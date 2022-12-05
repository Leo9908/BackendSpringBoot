package com.store.dto;

public class AddressDTO {

	private Long id;
	private String formatted;
	private Float latitude;
	private Float longitude;
	private String alias;
	private String apto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormatted() {
		return formatted;
	}

	public void setFormatted(String formatted) {
		this.formatted = formatted;
	}

	public String getApto() {
		return apto;
	}

	public void setApto(String apto) {
		this.apto = apto;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public AddressDTO(String formatted, String apto, Float latitude, Float longitude, String alias) {
		super();
		this.formatted = formatted;
		this.latitude = latitude;
		this.longitude = longitude;
		this.alias = alias;
		this.apto = apto;
	}

	public AddressDTO() {
		super();
	}

}
