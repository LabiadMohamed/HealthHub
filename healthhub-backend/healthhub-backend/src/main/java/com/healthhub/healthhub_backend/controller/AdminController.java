package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.AdminStatsResponse;
import com.healthhub.healthhub_backend.dto.UserResponse;
import com.healthhub.healthhub_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsResponse> getStats() {
        return ResponseEntity.ok(adminService.getStats());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PatchMapping("/users/{id}/promote/volunteer")
    public ResponseEntity<Void> promoteToVolunteer(@PathVariable Long id) {
        adminService.promoteToVolunteer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{id}/promote/admin")
    public ResponseEntity<Void> promoteToAdmin(@PathVariable Long id) {
        adminService.promoteToAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}