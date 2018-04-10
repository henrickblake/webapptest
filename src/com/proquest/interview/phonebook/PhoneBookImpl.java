package com.proquest.interview.phonebook;

import java.util.Map;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	
	private Map<String, Person> people = new java.util.HashMap<String, Person>();
	
	public Map<String, Person> getPeople() {
		return people;
	}

	public void setPeople(Map<String, Person> people) {
		this.people = people;
	}

	@Override
	public void addPerson(Person newPerson) {
		String name = newPerson.getName();
		if (people.get(name) == null) {
			people.put(name, newPerson);
			DatabaseUtil.addPerson(newPerson);
		}
		else {
			System.out.println(newPerson.getName() + " already exists");
		}
	}
	
	@Override
	public Person findPerson(String firstName, String lastName) {
		Person personFound = null;
		String name = firstName + " " + lastName;

		// Check local phonebook
		personFound = people.get(name);
		
		return personFound;		
	}
	
	private void printPhoneBook() {
		System.out.println("Phone book:");
		for (Person person : people.values()) {
			System.out.println(person);
		}
	}
	
	private void printEntry(String firstName, String lastName){
		System.out.println("Phone book entry:");
		Person person = findPerson(firstName, lastName);
		if (person != null) {
			System.out.println(person);
		}
		else {
			System.out.println(firstName + " " + lastName + " not found");
		}
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		PhoneBookImpl phoneBook = new PhoneBookImpl();
		
		// Load local phone book from database
		phoneBook.setPeople(DatabaseUtil.getAllEntries());
		
		// Create person objects and put them in the PhoneBook and database
		Person person = new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI");
		phoneBook.addPerson(person);
		person = new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI");
		phoneBook.addPerson(person);
		
		// Print the phone book out to System.out
		phoneBook.printPhoneBook();
		
		// Find Cynthia Smith and print out just her entry
		phoneBook.printEntry("Cynthia", "Smith");
		
		// TODO: insert the new person objects into the database - already done above
	}
	
}
