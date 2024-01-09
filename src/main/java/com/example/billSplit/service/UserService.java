package com.example.billSplit.service;

import com.example.billSplit.dtos.AuthRequest;
import com.example.billSplit.dtos.AuthResponse;
import com.example.billSplit.entites.User;
import com.example.billSplit.exception.ApplicationException;
import com.example.billSplit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(()-> new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN));
        boolean matches = encoder.matches(request.getPassword(), user.getPassword());

        if(matches){
            var jwtToken = generateToken(user);
            return AuthResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        }
        throw new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ) {
        return buildToken(extraClaims, userDetails, 1000*60*1000);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("77397A244326462948404D635166546A576E5A7234753778214125442A472D4B");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User getUserById(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public User createUser(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User updateUser(Long userID, User user) {
        if (userRepository.existsById(userID)) {
            user.setUserID(userID);
            return userRepository.save(user);
        }
        return null; // Handle not found scenario
    }

    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(()->new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN));
    }
}
