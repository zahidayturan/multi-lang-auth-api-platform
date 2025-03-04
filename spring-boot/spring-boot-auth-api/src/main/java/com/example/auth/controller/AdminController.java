package com.example.auth.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.model.User;
import com.example.auth.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Admin Dashboard - Sadece Admin görebilir!";
    }

    @PutMapping("/update-role/{username}")
    public ResponseEntity<?> updateUserRole(@PathVariable String username, @RequestBody Set<String> roles) {
        User updatedUser = userService.updateUserRoles(username, roles);
        return ResponseEntity.ok("User role updated successfully: " + updatedUser.getRoles());
    }
}
