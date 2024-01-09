package com.example.billSplit.service;

import com.example.billSplit.entites.Group;
import com.example.billSplit.entites.User;
import com.example.billSplit.exception.ApplicationException;
import com.example.billSplit.repositories.GroupRepository;
import com.example.billSplit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Group> getAllGroups(User user) {
        return groupRepository.findGroupsByUserId(user.getUserID());
    }

    public Group getGroupById(Long groupID) {
        return groupRepository.findById(groupID).orElseThrow(()->new ApplicationException(HttpStatus.NOT_FOUND.value(), "Group Not Found",HttpStatus.NOT_FOUND));
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group addUsersToGroup(Long groupID, Set<String> userIds) {
        Group group = groupRepository.findById(groupID).orElseThrow(()->new ApplicationException(HttpStatus.NOT_FOUND.value(), "Group Not Found",HttpStatus.NOT_FOUND));
        List<User> users = userRepository.findAllByEmailIn(userIds);
        group.getUsers().addAll(users);
        groupRepository.save(group);
        return group;
    }

    public void deleteGroup(Long groupID) {
        groupRepository.deleteById(groupID);
    }
}
