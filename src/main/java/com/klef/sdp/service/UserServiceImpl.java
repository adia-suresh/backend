package com.klef.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.klef.sdp.entity.Admin;
import com.klef.sdp.entity.Artist;
import com.klef.sdp.entity.Visitor;
import com.klef.sdp.repository.AdminRepository;
import com.klef.sdp.repository.ArtistRepository;
import com.klef.sdp.repository.VisitorRepository;

@Service
public class UserServiceImpl implements UserService 
{
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private ArtistRepository artistRepo;

    @Autowired
    private VisitorRepository visitorRepo;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException 
    {
        boolean isEmail = input.contains("@");

        // Admin
        Optional<Admin> adminOpt = adminRepo.findByUsername(input);
        if (adminOpt.isPresent()) 
        {
            Admin admin = adminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // Artist
        Optional<Artist> artistOpt = isEmail ? artistRepo.findByEmail(input) : artistRepo.findByUsername(input);
        if (artistOpt.isPresent()) 
        {
            Artist artist = artistOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    artist.getUsername(),
                    artist.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ARTIST"))
            );
        }

        // Visitor
        Optional<Visitor> visitorOpt = isEmail ? visitorRepo.findByEmail(input) : visitorRepo.findByUsername(input);
        if (visitorOpt.isPresent()) 
        {
            Visitor visitor = visitorOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    visitor.getUsername(),
                    visitor.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_VISITOR"))
            );
        }

        throw new UsernameNotFoundException("User not found with input: " + input);
    }

    @Override
    public Object getUserByLogin(String input) 
    {
        boolean isEmail = input.contains("@");

        Optional<Admin> adminOpt = adminRepo.findByUsername(input);
        if (adminOpt.isPresent()) 
        {
            return adminOpt.get();
        }

        Optional<Artist> artistOpt = isEmail ? artistRepo.findByEmail(input) : artistRepo.findByUsername(input);
        if (artistOpt.isPresent()) 
        {
            return artistOpt.get();
        }

        Optional<Visitor> visitorOpt = isEmail ? visitorRepo.findByEmail(input) : visitorRepo.findByUsername(input);
        if (visitorOpt.isPresent()) 
        {
            return visitorOpt.get();
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsernameAndRole(String input, String role) throws UsernameNotFoundException 
    {
        boolean isEmail = input.contains("@");

        if ("ADMIN".equalsIgnoreCase(role)) {
            Optional<Admin> adminOpt = adminRepo.findByUsername(input);
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        admin.getUsername(), admin.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        } else if ("ARTIST".equalsIgnoreCase(role)) {
            Optional<Artist> artistOpt = isEmail ? artistRepo.findByEmail(input) : artistRepo.findByUsername(input);
            if (artistOpt.isPresent()) {
                Artist artist = artistOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        artist.getUsername(), artist.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ARTIST")));
            }
        } else if ("VISITOR".equalsIgnoreCase(role)) {
            Optional<Visitor> visitorOpt = isEmail ? visitorRepo.findByEmail(input) : visitorRepo.findByUsername(input);
            if (visitorOpt.isPresent()) {
                Visitor visitor = visitorOpt.get();
                return new org.springframework.security.core.userdetails.User(
                        visitor.getUsername(), visitor.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_VISITOR")));
            }
        }

        throw new UsernameNotFoundException("User not found with input: " + input + " for role: " + role);
    }

    @Override
    public Object getUserByLoginAndRole(String input, String role) 
    {
        boolean isEmail = input.contains("@");

        if ("ADMIN".equalsIgnoreCase(role)) {
            Optional<Admin> adminOpt = adminRepo.findByUsername(input);
            if (adminOpt.isPresent()) return adminOpt.get();
        } else if ("ARTIST".equalsIgnoreCase(role)) {
            Optional<Artist> artistOpt = isEmail ? artistRepo.findByEmail(input) : artistRepo.findByUsername(input);
            if (artistOpt.isPresent()) return artistOpt.get();
        } else if ("VISITOR".equalsIgnoreCase(role)) {
            Optional<Visitor> visitorOpt = isEmail ? visitorRepo.findByEmail(input) : visitorRepo.findByUsername(input);
            if (visitorOpt.isPresent()) return visitorOpt.get();
        }
        return null;
    }
}