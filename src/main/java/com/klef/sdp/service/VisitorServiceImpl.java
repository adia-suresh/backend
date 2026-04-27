package com.klef.sdp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.sdp.dto.BookingRequest;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;
import com.klef.sdp.entity.Visitor;
import com.klef.sdp.repository.ArtworkRepository;
import com.klef.sdp.repository.BookingRepository;
import com.klef.sdp.repository.VisitorRepository;

@Service
public class VisitorServiceImpl implements VisitorService
{
   @Autowired
   private VisitorRepository visitorRepository;

   @Autowired
   private ArtworkRepository artworkRepository;

   @Autowired
   private BookingRepository bookingRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public String registerVisitor(Visitor visitor) 
   {
      visitor.setPassword(passwordEncoder.encode(visitor.getPassword()));
      visitorRepository.save(visitor);
      return "Visitor Registered Successfully";
   }

   @Override
   public Visitor verifyVisitorLogin(String username, String password) 
   {
      boolean isEmail = username != null && username.contains("@");
      Visitor visitor = isEmail ? visitorRepository.findByEmail(username).orElse(null) : visitorRepository.findByUsername(username).orElse(null);

      if (visitor != null && passwordEncoder.matches(password, visitor.getPassword()))
      {
         return visitor;
      }

      return null;
   }

   @Override
   public List<Artwork> viewAllArtworks() 
   {
      return artworkRepository.findAll();
   }

   @Override
   public String bookArtwork(BookingRequest request) 
   {
      Visitor visitor = visitorRepository.findById(request.getVisitorId()).orElse(null);
      Artwork artwork = artworkRepository.findById(request.getArtworkId()).orElse(null);

      if (visitor == null) {
         return "Visitor not found";
      }

      if (artwork == null) {
         return "Artwork not found";
      }

      if (artwork.getStock() <= 0) {
         return "Artwork is out of stock";
      }

      Booking booking = new Booking();
      booking.setVisitor(visitor);
      booking.setArtwork(artwork);
      booking.setBookingDate(request.getBookingDate());
      booking.setVisitSlot(request.getVisitSlot());
      booking.setRemarks(request.getRemarks());
      booking.setFinalPrice(request.getFinalPrice());
      booking.setStatus(request.getStatus());

      bookingRepository.save(booking);

      // reduce stock
      artwork.setStock(artwork.getStock() - 1);
      artworkRepository.save(artwork);

      return "Artwork Booked Successfully";
   }

   @Override
   public List<Booking> viewBookingsByVisitor(int visitorId) 
   {
      return bookingRepository.findAll()
              .stream()
              .filter(b -> b.getVisitor() != null && b.getVisitor().getId() == visitorId)
              .collect(Collectors.toList());
   }
}