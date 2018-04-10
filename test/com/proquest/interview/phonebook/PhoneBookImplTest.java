package com.proquest.interview.phonebook;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImplTest {
	
	private PhoneBook phoneBook;
	
	@Before
	public void setup() {
		DatabaseUtil.initDB();
		phoneBook = new PhoneBookImpl();
	}
	
	@Test
	public void shouldAddFindPerson() {
		phoneBook.addPerson(new Person("Cynthia Smith","(824) 128-8758","875 Main St, Ann Arbor, MI"));
		Person person = phoneBook.findPerson("Cynthia", "Smith");
		assertEquals("Cynthia Smith", person.getName());
		assertEquals("(824) 128-8758", person.getPhoneNumber());
		assertEquals("875 Main St, Ann Arbor, MI", person.getAddress());
	}
}
