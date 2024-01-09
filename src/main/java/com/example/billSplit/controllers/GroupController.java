package com.example.billSplit.controllers;

import com.example.billSplit.dtos.AddGroupUsers;
import com.example.billSplit.entites.Group;
import com.example.billSplit.entites.User;
import com.example.billSplit.repositories.GroupRepository;
import com.example.billSplit.repositories.UserRepository;
import com.example.billSplit.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getAllGroupsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return groupService.getAllGroups(user);
    }

    @GetMapping("/{groupID}")
    public Group getGroupById(@PathVariable Long groupID) {
        return groupService.getGroupById(groupID);
    }

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Set<User> set = new HashSet<>();
        set.add(user);
        group.setUsers(set);
        return groupService.createGroup(group);
    }

    @PostMapping("/{groupID}/addUsers")
    public Group addUsersToGroup(@PathVariable Long groupID, @RequestBody AddGroupUsers addGroupUsers) {
        return groupService.addUsersToGroup(groupID, addGroupUsers.getUserEmail());
    }

    @DeleteMapping("/{groupID}")
    public void deleteGroup(@PathVariable Long groupID) {
        groupService.deleteGroup(groupID);
    }
}
