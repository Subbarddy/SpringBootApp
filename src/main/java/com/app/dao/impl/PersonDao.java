package com.app.dao.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.model.Person;

@Repository
public interface PersonDao extends CrudRepository<Person, Integer> {

	@Query(nativeQuery = true, value = "select * from Person_details where first_name like %:name%")
	Person findByFirstName(@Param(value = "name") String first_name);

	@Query(nativeQuery = true, value = "select * from Person_details where person_id=:personId")
	Person findByPersonId(@Param(value = "personId") int id);
}
