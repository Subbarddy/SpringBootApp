package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS_DETAILS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ADD_ID")
	private int add_id;

	@Column(name = "HOUS_NO")
	private String hous_no;

	@Column(name = "LAND_MARK")
	private String land_mark;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "PIN_CODE")
	private String pin_code;

	public int getAdd_id() {
		return add_id;
	}

	public void setAdd_id(int add_id) {
		this.add_id = add_id;
	}

	public String getHous_no() {
		return hous_no;
	}

	public void setHous_no(String hous_no) {
		this.hous_no = hous_no;
	}

	public String getLand_mark() {
		return land_mark;
	}

	public void setLand_mark(String land_mark) {
		this.land_mark = land_mark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPin_code() {
		return pin_code;
	}

	public void setPin_code(String pin_code) {
		this.pin_code = pin_code;
	}

	public Address(int add_id, String hous_no, String land_mark, String city, String state, String pin_code) {
		super();
		this.add_id = add_id;
		this.hous_no = hous_no;
		this.land_mark = land_mark;
		this.city = city;
		this.state = state;
		this.pin_code = pin_code;
	}

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

}
