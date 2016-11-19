package seo.dale.practice.spring.data.redis.person;

import java.io.Serializable;

public class Address implements Serializable {

	private String street;

	private Integer number;

	public Address(String street, Integer number) {
		this.street = street;
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
