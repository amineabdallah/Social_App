package com.example.Social_App.Controller;

import com.example.Social_App.Model.Role;
import com.example.Social_App.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;


    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/Roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();

    }

    @GetMapping("/Role/{roleName}")
    public ResponseEntity<Role> findById(@PathVariable(value = "roleName") final String roleName) {
        return new ResponseEntity<>(roleService.findbyname(roleName), HttpStatus.OK);
    }

    @PostMapping("/Role")
    public ResponseEntity<Role> addRole(@RequestBody final Role role) {
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
    }

    @PutMapping("/Role/{roleName}")
    public ResponseEntity<Role> updateRole(@RequestBody final Role role, @PathVariable(value = "roleName") final String roleName) {
        return new ResponseEntity<>(roleService.updateRole(roleName, role), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/Role/{roleName}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "roleName") final String roleName) {
        roleService.deleteRole(roleName);
        return ResponseEntity.noContent().build();
    }

}
