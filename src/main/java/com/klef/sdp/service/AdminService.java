package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.entity.Admin;
import com.klef.sdp.entity.Artist;
import com.klef.sdp.entity.Visitor;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;

public interface AdminService 
{
   public Admin verifyAdminLogin(String username, String password);

   // Artist Management
   public String addArtist(Artist artist);
   public List<Artist> viewAllArtists();
   public String deleteArtist(int id);

   // Visitor Management
   public List<Visitor> viewAllVisitors();
   public String deleteVisitor(int id);

   // Artwork Management
   public String addArtwork(Artwork artwork);
   public List<Artwork> viewAllArtworks();
   public Artwork viewArtworkById(int id);
   public List<Booking> getAllBookings();
}