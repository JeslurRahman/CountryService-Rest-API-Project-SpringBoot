package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class) // need to add this to run the test methods in order
@SpringBootTest(classes = CountryServiceMockitoTests.class)
public class CountryServiceMockitoTests {
	// used mockito framework to mock in unit testing
	@Mock
	CountryRepository countryRepoMock;

	@InjectMocks
	CountryService countryService;

	public List<Country> mycountries;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "China", "Beijing"));
		mycountries.add(new Country(3, "Japan", "Tokyo"));

		// Mocked the external dependency "countryRepository.findAll()"
		when(countryRepoMock.findAll()).thenReturn(mycountries); // mocking

		// Assertion
		assertEquals(3, countryService.getAllCountries().size());
	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "China", "Beijing"));
		mycountries.add(new Country(3, "Japan", "Tokyo"));

		int countryID = 1;

		// MOCKED
		when(countryRepoMock.findAll()).thenReturn(mycountries);
		// Assertion
		assertEquals(countryID, countryService.getCountryById(countryID).getId());
	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		List<Country> mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "China", "Beijing"));
		mycountries.add(new Country(3, "Japan", "Tokyo"));

		String countryName = "India";

		// MOCKED
		when(countryRepoMock.findAll()).thenReturn(mycountries);
		// Assertion
		assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
	}

	@Test
	@Order(4)
	public void test_addCountry() {
		Country country = new Country(4, "Germany", "Berlin");

		// MOCKED
		when(countryRepoMock.save(country)).thenReturn(country);
		// Assertion
		assertEquals(country, countryService.addCountry(country));
	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		Country country = new Country(4, "Germany", "Berlin");

		// MOCKED
		when(countryRepoMock.save(country)).thenReturn(country);
		// Assertion
		assertEquals(country, countryService.updateCountry(country));
	}

	@Test
	@Order(6)
	public void test_DeleteCountry() {
		Country country = new Country(4, "Germany", "Berlin");
		
		/*
		// MOCKED - here we can not use "when" and "thenReturn" because the deleteCountry method id void not returning anything
		when(countryRepoMock.delete(country)).thenReturn(country);
		
		// Assertion
		assertEquals(country, countryService.deleteCountry(country));
		*/
		countryService.deleteCountry(country);
		verify(countryRepoMock,times(1)).delete(country);
	}
}
