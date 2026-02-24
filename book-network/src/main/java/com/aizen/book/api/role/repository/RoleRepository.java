package com.aizen.book.api.role.repository;

import com.aizen.book.api.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Integer> {


    Optional<Role> findByName(String role);
}
