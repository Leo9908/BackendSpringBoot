package com.store.entitys;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	@Column(name = "dish_type", nullable = false, length = 2)
	private int type;
	@Column(name = "precio", nullable = false, length = 5)
	private double precio;
	@Column(name = "description", nullable = false, length = 100)
	private String description;
	@Column(name = "imgUrl", nullable = false)
	private String imgUrl;
	@Column(name = "onSale", nullable = false)
	private Boolean onSale;

	// orphanRemoval elimina todos los objetos que dependen de otro para existir
	// cuando dicho objeto se eleimina
	@JsonBackReference // esta etiqueta corrige errores cuando hay referencias bidireccionales
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<ProductRating> ratings = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Boolean getOnSale() {
		return onSale;
	}

	public void setOnSale(Boolean onSale) {
		this.onSale = onSale;
	}

	public Collection<ProductRating> getRatings() {
		return ratings;
	}

	public void setRatings(Collection<ProductRating> ratings) {
		this.ratings = ratings;
	}

}
