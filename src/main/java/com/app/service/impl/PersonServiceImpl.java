package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.app.dao.impl.PersonDao;
import com.app.exception.handler.APP_NotFoundException;
import com.app.model.Person;

@Service(value = "personServiceImpl")
public class PersonServiceImpl implements PersonService {

	@Autowired
	public PersonDao personDao;

	@Override
	public Person addPerson(Person person) {
		return personDao.save(person);
	}

	@Override
	public List<Person> getAllPersons() {
		return (List<Person>) personDao.findAll();
	}

	@Override
	public Person getPersonById(int person_id) {
		return personDao.findOne(person_id);
	}

	@Override
	public Person getPersonByName(String name) {
		return personDao.findByFirstName(name);
	}

	@Override
	public boolean deletePersonById(int id) throws APP_NotFoundException {
		try {
			personDao.delete(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new APP_NotFoundException("Request Deleted PersonId Is Not Present");
		}
		return userExistOrNot(id);
	}

	private boolean userExistOrNot(int id) {
		Person person = getPersonById(id);
		if (!(person == null)) {
			return false;
		} else {
			return true;
		}
	}
}
