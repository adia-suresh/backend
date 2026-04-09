package com.klef.sdp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_table")
public class Payment 
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
   private double amount;

   @Column(nullable = false)
   private String paymentMethod; // UPI / Card / NetBanking

   @Column(nullable = false)
   private String paymentStatus; // Success / Failed / Pending

   @CreationTimestamp
   @Column(updatable = false)
   private LocalDateTime paymentDate;

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

   public double getAmount() {
      return amount;
   }

   public void setAmount(double amount) {
      this.amount = amount;
   }

   public String getPaymentMethod() {
      return paymentMethod;
   }

   public void setPaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
   }

   public String getPaymentStatus() {
      return paymentStatus;
   }

   public void setPaymentStatus(String paymentStatus) {
      this.paymentStatus = paymentStatus;
   }

   public LocalDateTime getPaymentDate() {
      return paymentDate;
   }

   @Override
   public String toString() {
      return "Payment [id=" + id + ", amount=" + amount + ", status=" + paymentStatus + "]";
   }
}