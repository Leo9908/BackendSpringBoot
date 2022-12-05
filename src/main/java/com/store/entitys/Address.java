package com.store.entitys;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_address")
public class Address {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "formatted", nullable = false)
	private String formatted;
	@Column(name = "latitude", nullable = true, length = 40)
	private Float latitude;
	@Column(name = "longitude", nullable = true, length = 40)
	private Float longitude;
	@Column(name = "alias", nullable = false, length = 20)
	private String alias;
	@Column(name = "apto", nullable = false, length = 10)
	private String apto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@JsonBackReference
	@OneToMany(mappedBy = "address")
	private Collection<Order> orders = new HashSet<>();

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Address(String address, String alias, Float latitude, Float longitude, User user) {
		super();
		this.formatted = address;
		this.alias = alias;
		this.latitude = latitude;
		this.longitude = longitude;
		this.user = user;
	}

	public Address() {
		super();
	}

}
