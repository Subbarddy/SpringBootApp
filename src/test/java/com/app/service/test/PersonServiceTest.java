package com.app.service.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.controller.PersonController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonServiceTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	
}
