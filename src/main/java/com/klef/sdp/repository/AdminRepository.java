package com.klef.sdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.sdp.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> 
{
    Optional<Admin> findByUsername(String username);
}