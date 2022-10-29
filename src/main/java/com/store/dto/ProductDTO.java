package com.store.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {
	private Long id;
	@NotEmpty // Este es una de las etiquetas para las validaciones
	@Size(min = 5, message = "The product name must have at least 5 characters")
	private String name;
	@NotNull
	private int type;
	@NotNull
	private double precio;

	private String description;
	@NotEmpty
	@Size(min = 15, message = "The product URL image name must have at least 15 characters")
	private String imgUrl;
	@NotNull
	private Boolean onSale;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public ProductDTO() {
		super();
	}

}
