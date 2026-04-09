package com.klef.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.sdp.entity.Artwork;

public interface ArtworkRepository extends JpaRepository<Artwork, Integer>
{
}