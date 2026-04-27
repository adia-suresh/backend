package com.klef.sdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.klef.sdp.entity.Artist;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;
import com.klef.sdp.security.JwtUtil;
import com.klef.sdp.service.ArtistService;
import com.klef.sdp.service.UserService;

@RestController
@RequestMapping("/artistapi")
@CrossOrigin("*")
public class ArtistController 
{
   @Autowired
   private ArtistService artistService;

   @Autowired
   private JwtUtil jwtUtil;

   @Autowired
   private UserService userService;

   @PostMapping("/login")
   public Object login(@RequestBody Artist artist)
   {
      String loginIdentifier = (artist.getUsername() != null && !artist.getUsername().isEmpty()) ? artist.getUsername() : artist.getEmail();
      Artist a = artistService.verifyArtistLogin(
            loginIdentifier,
            artist.getPassword()
      );

      if(a != null)
      {
         UserDetails userDetails = userService.loadUserByUsername(a.getUsername());
         String token = jwtUtil.generateToken(userDetails);

         Map<String, Object> response = new HashMap<>();
         response.put("token", token);
         response.put("user", a);
         response.put("role", "ARTIST");

         return response;
      }
      else
      {
         return "Invalid Login";
      }
   }

   @PostMapping("/addartwork")
   public String addArtwork(@RequestBody Artwork artwork)
   {
      return artistService.addArtwork(artwork);
   }

   @GetMapping("/myartworks/{id}")
   public List<Artwork> myArtworks(@PathVariable int id)
   {
      return artistService.viewArtworksByArtist(id);
   }

   @DeleteMapping("/deleteartwork/{id}")
   public String deleteArtwork(@PathVariable int id)
   {
      return artistService.deleteArtwork(id);
   }

   @GetMapping("/mybookings/{id}")
   public List<Booking> myBookings(@PathVariable int id)
   {
      return artistService.viewBookingsByArtist(id);
   }
}