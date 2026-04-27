package com.klef.sdp.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.sdp.entity.Admin;
import com.klef.sdp.entity.Artist;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;
import com.klef.sdp.entity.Visitor;
import com.klef.sdp.repository.AdminRepository;
import com.klef.sdp.repository.ArtistRepository;
import com.klef.sdp.repository.ArtworkRepository;
import com.klef.sdp.repository.BookingRepository;
import com.klef.sdp.repository.VisitorRepository;

@Service
public class AdminServiceImpl implements AdminService
{
   @Autowired
   private AdminRepository adminRepository;
   
   @Autowired
   private BookingRepository bookingRepository;

   @Autowired
   private ArtistRepository artistRepository;

   @Autowired
   private VisitorRepository visitorRepository;

   @Autowired
   private ArtworkRepository artworkRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public Admin verifyAdminLogin(String username, String password)
   {
       Optional<Admin> adminOpt = adminRepository.findById(java.util.Objects.requireNonNull(username));

       if (adminOpt.isPresent())
       {
           Admin admin = adminOpt.get();

           if (passwordEncoder.matches(password, admin.getPassword()))
           {
               return admin;
           }
       }

       return null;
   }
   @Override
   public String addArtist(Artist artist) 
   {
       System.out.println("========= ARTIST DATA RECEIVED =========");
       System.out.println("Username: " + artist.getUsername());
       System.out.println("Password: " + artist.getPassword());
       System.out.println("Name: " + artist.getName());
       System.out.println("Email: " + artist.getEmail());
       System.out.println("Contact: " + artist.getContact());
       System.out.println("Gender: " + artist.getGender());
       System.out.println("Specialization: " + artist.getSpecialization());
       System.out.println("Bio: " + artist.getBio());
       System.out.println("=======================================");

       artist.setPassword(passwordEncoder.encode(artist.getPassword()));
       artistRepository.save(artist);
       return "Artist Added Successfully";
   }
   
   @Override
   public List<Artist> viewAllArtists() 
   {
      return artistRepository.findAll();
   }
   
   @Override
   public String deleteArtist(int id) 
   {
      if (artistRepository.existsById(id)) 
      {
         artistRepository.deleteById(id);
         return "Artist Deleted Successfully";
      } 
      else 
      {
         return "Artist ID Not Found";
      }
   }

   @Override
   public List<Visitor> viewAllVisitors() 
   {
      return visitorRepository.findAll();
   }

   @Override
   public String deleteVisitor(int id) 
   {
      visitorRepository.deleteById(id);
      return "Visitor Deleted Successfully";
   }

   @Override
   public String addArtwork(Artwork artwork) 
   {
      artworkRepository.save(java.util.Objects.requireNonNull(artwork));
      return "Artwork Added Successfully";
   }

   @Override
   public List<Artwork> viewAllArtworks() 
   {
      return artworkRepository.findAll();
   }

   @Override
   public Artwork viewArtworkById(int id) 
   {
      return artworkRepository.findById(id).orElse(null);
   }
   
   @Override
   public List<Booking> getAllBookings() {
       return bookingRepository.findAll();
   }
}