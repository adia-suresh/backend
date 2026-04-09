package com.klef.sdp.service;

import java.util.List;

import com.klef.sdp.dto.BookingRequest;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;
import com.klef.sdp.entity.Visitor;

public interface VisitorService 
{
   public String registerVisitor(Visitor visitor);
   public Visitor verifyVisitorLogin(String username, String password);
   public List<Artwork> viewAllArtworks();
   public String bookArtwork(BookingRequest request);
   public List<Booking> viewBookingsByVisitor(int visitorId);
}