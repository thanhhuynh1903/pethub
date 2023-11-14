package com.pethub.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "provinces")
public class Province extends IdBasedEntity {

	@Column(nullable = false, length = 45)
	private String name;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	public Province(){

	}

	public Province(String name, Country country) {
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Province [id=" + id + ", name=" + name + "]";
	}

}
