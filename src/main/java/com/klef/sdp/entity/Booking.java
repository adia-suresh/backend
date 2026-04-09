package com.klef.sdp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "booking_table")
public class Booking 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @ManyToOne
   @JoinColumn(name = "visitor_id", nullable = false)
   private Visitor visitor;

   @ManyToOne
   @JoinColumn(name = "artwork_id", nullable = false)
   private Artwork artwork;

   @Column(nullable = false)
   private String bookingDate;

   @Column(nullable = false)
   private String visitSlot;

   @Column(length = 500)
   private String remarks;

   @Column(nullable = false)
   private double finalPrice;

   @Column(nullable = false)
   private String status; // Pending / Confirmed / Cancelled

   @CreationTimestamp
   @Column(updatable = false)
   private LocalDateTime bookedAt;

   @UpdateTimestamp
   private LocalDateTime updatedAt;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Visitor getVisitor() {
      return visitor;
   }

   public void setVisitor(Visitor visitor) {
      this.visitor = visitor;
   }

   public Artwork getArtwork() {
      return artwork;
   }

   public void setArtwork(Artwork artwork) {
      this.artwork = artwork;
   }

   public String getBookingDate() {
      return bookingDate;
   }

   public void setBookingDate(String bookingDate) {
      this.bookingDate = bookingDate;
   }

   public String getVisitSlot() {
      return visitSlot;
   }

   public void setVisitSlot(String visitSlot) {
      this.visitSlot = visitSlot;
   }

   public String getRemarks() {
      return remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }

   public double getFinalPrice() {
      return finalPrice;
   }

   public void setFinalPrice(double finalPrice) {
      this.finalPrice = finalPrice;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public LocalDateTime getBookedAt() {
      return bookedAt;
   }

   public LocalDateTime getUpdatedAt() {
      return updatedAt;
   }

   @Override
   public String toString() {
      return "Booking [id=" + id + ", status=" + status + ", price=" + finalPrice + "]";
   }
}