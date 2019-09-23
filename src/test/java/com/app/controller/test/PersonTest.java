package com.app.controller.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.controller.PersonController;
import com.app.model.Address;
import com.app.model.Person;
import com.app.service.impl.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonTest {

	@InjectMocks
	PersonController personController;

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private PersonService persionservice;

	public static Person person;
	public static List<Address> addresses;
	public static List<Person> persons;
	public static Address address;

	@BeforeClass
	public static void initialSetUp() {
		address = new Address(1, "28/9", "main road", "banglore", "karnataka", "560063");
		addresses = new ArrayList<Address>();
		addresses.add(address);

		person = new Person(1, "gangala", "subbareddy", "gangala@gmail.com", addresses, "2019/09/18");
		persons = new ArrayList<Person>();
		persons.add(person);
		persons.add(new Person(2, "venkat", "subbareddy", "venkat@gmail.com", addresses, "2018/09/18"));
	}

	@Test
	public void addPersonTest() throws Exception {

		when(persionservice.addPerson(any())).thenReturn(person);

		mockmvc.perform(MockMvcRequestBuilders.post("/addPerson")
				.content(asJsonString(new Person(1, "gangala", "subbareddy", "abc@gmail.com", addresses, "2019/09/18")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("gangala"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("subbareddy"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailIid").value("gangala@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.address[*].city").value("banglore"));
	}

	@Test
	public void getAllPersons() throws Exception {

		when(persionservice.getAllPersons()).thenReturn(persons);

		mockmvc.perform(MockMvcRequestBuilders.get("/getAllPersons").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("gangala"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName").value("venkat"));
	}

	@Test
	public void getPersonById() throws Exception {

		when(persionservice.getPersonById(anyInt())).thenReturn(person);

		mockmvc.perform(MockMvcRequestBuilders.get("/getPersonByID/{Id}", 1).accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("gangala"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailIid").value("gangala@gmail.com"));
	}

	@Test
	public void getPersonByName() throws Exception {

		when(persionservice.getPersonByName(anyString())).thenReturn(person);

		mockmvc.perform(
				MockMvcRequestBuilders.get("/getPersonByName/{Name}", "gangala").accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("gangala"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailIid").value("gangala@gmail.com"));
	}
	
	/*@Test
	public void deletePersonDetails() throws Exception {
		
		//when(persionservice.deletePersonById(anyInt())).thenReturn(true);
		
		doNothing().when(persionservice.deletePersonById(anyInt()));

		mockmvc.perform(
				MockMvcRequestBuilders.get("/deletePersonDetails/{Id}", 1).accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
	}*/

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
