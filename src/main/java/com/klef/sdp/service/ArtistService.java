package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.entity.Artist;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;

public interface ArtistService
{
   public Artist verifyArtistLogin(String username, String password);

   public String addArtwork(Artwork artwork);

   public List<Artwork> viewArtworksByArtist(int artistId);

   public String deleteArtwork(int artworkId);

   public List<Booking> viewBookingsByArtist(int artistId);
}