package com.klef.sdp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "artwork_table")
public class Artwork 
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "artwork_id")
   private int id;

   @Column(name = "artwork_category", nullable = false, length = 100)
   private String category;

   @Column(name = "artwork_title", nullable = false, length = 100)
   private String title;

   @Column(name = "artwork_description", nullable = false, length = 500)
   private String description;

   @Column(name = "artwork_price", nullable = false)
   private double price;

   @Column(name = "artwork_image_url", nullable = false, length = 1000)
   private String imageUrl;

   @Column(name = "artwork_status", nullable = false, length = 50)
   private String status; // Available / Sold
   
   @Column(nullable = false)
   private int stock;

   @ManyToOne
   @JoinColumn(name = "artist_id", nullable = false)
   private Artist artist;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public Artist getArtist() {
      return artist;
   }

   public void setArtist(Artist artist) {
      this.artist = artist;
   }
   
   public int getStock() {
	    return stock;
	}

	public void setStock(int stock) {
	    this.stock = stock;
	}

	@Override
	public String toString() {
	   return "Artwork [id=" + id + ", title=" + title + ", category=" + category + 
	          ", price=" + price + ", status=" + status + ", stock=" + stock + "]";
	}
}