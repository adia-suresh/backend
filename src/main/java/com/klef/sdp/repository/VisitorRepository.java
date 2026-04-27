package com.klef.sdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.sdp.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Integer>
{
    Optional<Visitor> findByUsername(String username);
    
    Optional<Visitor> findByEmail(String email);

    Visitor findByUsernameAndPassword(String username, String password);
}