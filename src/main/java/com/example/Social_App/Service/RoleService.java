package com.example.Social_App.Service;

import com.example.Social_App.Model.Role;
import com.example.Social_App.Repositiry.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    public Role findbyname(final String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role createRole(final Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(final String roleName, final Role role) {
        final Role oldrole = roleRepository.findByName(roleName);
        oldrole.setName(role.getName());
        return roleRepository.save(oldrole);
    }

    public void deleteRole(final String roleName) {
        final Role oldrole = roleRepository.findByName(roleName);
        roleRepository.delete(oldrole);
    }
}
