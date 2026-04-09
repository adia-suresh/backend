package com.klef.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.sdp.dto.BookingRequest;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;
import com.klef.sdp.entity.Visitor;
import com.klef.sdp.service.VisitorService;

@RestController
@RequestMapping("/visitorapi")
@CrossOrigin("*")
public class VisitorController 
{
   @Autowired
   private VisitorService visitorService;

   @PostMapping("/register")
   public ResponseEntity<String> register(@RequestBody Visitor visitor)
   {
      return ResponseEntity.ok(visitorService.registerVisitor(visitor));
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody Visitor visitor)
   {
      Visitor v = visitorService.verifyVisitorLogin(
            visitor.getUsername(),
            visitor.getPassword()
      );

      if(v != null)
         return ResponseEntity.ok(v);
      else
         return ResponseEntity.status(401).body("Invalid Login");
   }

   @GetMapping("/viewartworks")
   public List<Artwork> viewArtworks()
   {
      return visitorService.viewAllArtworks();
   }

   @PostMapping("/book")
   public String bookArtwork(@RequestBody BookingRequest request)
   {
      return visitorService.bookArtwork(request);
   }

   @GetMapping("/mybookings/{id}")
   public List<Booking> myBookings(@PathVariable int id)
   {
      return visitorService.viewBookingsByVisitor(id);
   }
}