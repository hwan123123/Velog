package org.example.blogproject.service;

import org.example.blogproject.domain.Role;
import org.example.blogproject.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}