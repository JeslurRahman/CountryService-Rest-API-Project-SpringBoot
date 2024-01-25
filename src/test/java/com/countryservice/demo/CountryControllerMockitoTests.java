package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {CountryControllerMockitoTests.class})
public class CountryControllerMockitoTests {
	
	//Note:-
	/*Mockito is generally used for mocking Java objects,
	 * primarily focusing on unit testing by isolating a class from its dependencies.
	*/

	@Mock
	CountryService countryServiceMock;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> mycountries;
	Country country;
	
	@Test
	@Order(1)
	public void test_getAllCountries()
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "China", "Beijing"));
		mycountries.add(new Country(3, "Japan", "Tokyo"));
		
		//mock
		when(countryServiceMock.getAllCountries()).thenReturn(mycountries);
		ResponseEntity<List<Country>> res = countryController.getCountries();
		
		//Assert
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(3, res.getBody().size());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById()
	{
		country = new Country(1, "India", "Delhi");
		int countryID = 1;
		
		//Mock
		when(countryServiceMock.getCountryById(countryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryById(countryID);
		
		//Assert
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryID, res.getBody().getId());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName()
	{
		country = new Country(1, "India", "Delhi");
		String countryName ="India";
		
		//Mock
		when(countryServiceMock.getCountryByName(countryName)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryByName(countryName);
		
		//Assert
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryName, res.getBody().getCountryName());
	}
	
	@Test
	@Order(4)
	public void test_addCountry()
	{
		country = new Country(4, "SriLanka", "Colombo");
		
		//Mock
		when(countryServiceMock.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.addCountry(country);
		
		//Assert
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
		assertEquals(country, res.getBody());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry()
	{
		country = new Country(4, "SriLanka", "Colombo");
		int countryId = 4;
		
		//Mock
		when(countryServiceMock.getCountryById(countryId)).thenReturn(country);
		when(countryServiceMock.updateCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.updateCountry(countryId,country);
				
		//Assert
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(countryId, res.getBody().getId());
		assertEquals("SriLanka", res.getBody().getCountryName());
		assertEquals("Colombo", res.getBody().getCountryCapital());
		
	}
	
	@Test
	@Order(5)
	public void test_deleteCountry()
	{
		country = new Country(4, "SriLanka", "Colombo");
		int countryId = 4;
		
		//Mock
		when(countryServiceMock.getCountryById(countryId)).thenReturn(country);
		ResponseEntity<Country> res = countryController.deleteCountry(countryId);
		
		//Assert
		assertEquals(HttpStatus.OK, res.getStatusCode());
		
	}
	
}
