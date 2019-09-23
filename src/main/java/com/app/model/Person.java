package com.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "PERSON_DETAILS")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERSON_ID")
	@ApiModelProperty(notes = "The database generated person ID")
	private int personId;

	@ApiModelProperty(notes = "Person First_Name")
	@Column(name = "FIRST_NAME")
	private String firstName;

	@ApiModelProperty(notes = "Person Last_NameD")
	@Column(name = "LAST_NAME")
	private String lastName;

	@ApiModelProperty(notes = "Person EmailID")
	@Column(name = "EMAIL_ID")
	private String emailIid;

	@ApiModelProperty(notes = "Person Address Details")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSON_ID")
	private List<Address> address;

	@ApiModelProperty(notes = "Person Added Date")
	@Column(name = "DATE_TIME")
	private String dateTime;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailIid() {
		return emailIid;
	}

	public void setEmailIid(String emailIid) {
		this.emailIid = emailIid;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Person(int personId, String firstName, String lastName, String emailIid, List<Address> address,
			String dateTime) {
		super();
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailIid = emailIid;
		this.address = address;
		this.dateTime = dateTime;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

}
