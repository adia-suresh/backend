package com.klef.sdp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="artist_table")
public class Artist 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(nullable = false, length = 100)
   private String name;

   @Column(nullable = false, length = 10)
   private String gender;

   @Column(nullable = false, unique = true, length = 100)
   private String email;

   @Column(nullable = false, unique = true, length = 20)
   private String contact;

   @Column(nullable = false, length = 100)
   private String specialization;

   @Column(nullable = false, length = 500)
   private String bio;

   @Column(nullable = false, unique = true, length = 50)
   private String username;

   @Column(nullable = false)
   private String password;

   @CreationTimestamp
   @Column(updatable = false)
   private LocalDateTime registeredAt;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getContact() {
      return contact;
   }

   public void setContact(String contact) {
      this.contact = contact;
   }

   public String getSpecialization() {
      return specialization;
   }

   public void setSpecialization(String specialization) {
      this.specialization = specialization;
   }

   public String getBio() {
      return bio;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public LocalDateTime getRegisteredAt() {
      return registeredAt;
   }

   public void setRegisteredAt(LocalDateTime registeredAt) {
      this.registeredAt = registeredAt;
   }

   @Override
   public String toString() {
      return "Artist [id=" + id + ", name=" + name + ", email=" + email + "]";
   }
}