package com.klef.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.sdp.entity.*;
import com.klef.sdp.repository.BookingRepository;
import com.klef.sdp.service.AdminService;

@RestController
@RequestMapping("/adminapi")
@CrossOrigin("*")
public class AdminController 
{
   @Autowired
   private AdminService adminService;
   
   @Autowired
   private BookingRepository bookingRepository;

   @PostMapping("/login")
   public ResponseEntity<?> adminLogin(@RequestBody Admin admin)
   {
      Admin a = adminService.verifyAdminLogin(admin.getUsername(), admin.getPassword());

      if(a != null)
         return ResponseEntity.ok(a);
      else
         return ResponseEntity.status(401).body("Invalid Login");
   }

   // ---------------- ARTIST ----------------

   @PostMapping("/addartist")
   public ResponseEntity<?> addArtist(@RequestBody Artist artist)
   {
      try
      {
         String output = adminService.addArtist(artist);
         return ResponseEntity.ok(output);
      }
      catch(Exception e)
      {
         e.printStackTrace();
         return ResponseEntity.status(500).body("Error: " + e.getMessage());
      }
   }

   @GetMapping("/viewartists")
   public List<Artist> viewArtists()
   {
      return adminService.viewAllArtists();
   }

   @DeleteMapping("/deleteartist/{id}")
   public String deleteArtist(@PathVariable int id)
   {
      return adminService.deleteArtist(id);
   }

   // ---------------- VISITOR ----------------

   @GetMapping("/viewvisitors")
   public List<Visitor> viewVisitors()
   {
      return adminService.viewAllVisitors();
   }

   @DeleteMapping("/deletevisitor/{id}")
   public String deleteVisitor(@PathVariable int id)
   {
      return adminService.deleteVisitor(id);
   }

   // ---------------- ARTWORK ----------------

   @GetMapping("/viewartworks")
   public List<Artwork> viewArtworks()
   {
      return adminService.viewAllArtworks();
   }
   
   @GetMapping("/allbookings")
   public List<Booking> getAllBookings() {
       return adminService.getAllBookings();
   }
}