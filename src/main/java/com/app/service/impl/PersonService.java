package com.app.service.impl;

import java.util.List;

import com.app.exception.handler.APP_NotFoundException;
import com.app.model.Person;

public interface PersonService {
	public Person addPerson(Person person);
	public List<Person> getAllPersons();
	public Person getPersonById(int id);
	public Person getPersonByName(String name);
	public boolean deletePersonById(int id) throws APP_NotFoundException;
}
