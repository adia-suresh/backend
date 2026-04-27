package com.klef.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.sdp.entity.Artist;
import com.klef.sdp.entity.Artwork;
import com.klef.sdp.entity.Booking;
import com.klef.sdp.repository.ArtistRepository;
import com.klef.sdp.repository.ArtworkRepository;
import com.klef.sdp.repository.BookingRepository;

@Service
public class ArtistServiceImpl implements ArtistService
{
   @Autowired
   private ArtistRepository artistRepository;

   @Autowired
   private ArtworkRepository artworkRepository;

   @Autowired
   private BookingRepository bookingRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   public Artist verifyArtistLogin(String username, String password) 
   {
      boolean isEmail = username != null && username.contains("@");
      Artist artist = isEmail ? artistRepository.findByEmail(username).orElse(null) : artistRepository.findByUsername(username).orElse(null);

      if (artist != null && passwordEncoder.matches(password, artist.getPassword()))
      {
         return artist;
      }

      return null;
   }

   @Override
   public String addArtwork(Artwork artwork) 
   {
      int artistId = artwork.getArtist().getId();

      Artist artist = artistRepository.findById(artistId).orElse(null);

      if (artist == null)
      {
         return "Artist Not Found";
      }

      artwork.setArtist(artist);
      artworkRepository.save(artwork);

      return "Artwork Added Successfully";
   }

   @Override
   public List<Artwork> viewArtworksByArtist(int artistId) 
   {
      return artworkRepository.findAll()
              .stream()
              .filter(a -> a.getArtist() != null && a.getArtist().getId() == artistId)
              .toList();
   }

   @Override
   public String deleteArtwork(int artworkId) 
   {
      artworkRepository.deleteById(artworkId);
      return "Artwork Deleted Successfully";
   }

   @Override
   public List<Booking> viewBookingsByArtist(int artistId)
   {
      return bookingRepository.findAll()
              .stream()
              .filter(b -> b.getArtwork() != null &&
                           b.getArtwork().getArtist() != null &&
                           b.getArtwork().getArtist().getId() == artistId)
              .toList();
   }
}