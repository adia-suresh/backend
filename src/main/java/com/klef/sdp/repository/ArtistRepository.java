package com.klef.sdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.sdp.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Integer>
{
    Optional<Artist> findByUsername(String username);

    Artist findByUsernameAndPassword(String username, String password);
}