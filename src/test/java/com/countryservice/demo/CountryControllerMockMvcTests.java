package com.countryservice.demo;

import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.countryservice.demo")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {CountryControllerMockMvcTests.class})
public class CountryControllerMockMvcTests {
	
	//Note:-
	/*MockMVC is tailored for testing the web layer, providing a way to send HTTP requests and assert responses.
	 * It performs full Spring MVC request handling but via mock request and response objects instead of a running server.
	 * */
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	CountryService countryServiceMock;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> mycountries;
	Country country;
	
	// this will run before executing each test method
	@BeforeEach 
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}
	
	@Test
	@Order(1)
	public void test_getCountries() throws Exception
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1, "India", "Delhi"));
		mycountries.add(new Country(2, "China", "Beijing"));
		mycountries.add(new Country(3, "Japan", "Tokyo"));
		
		//Mock
		when(countryServiceMock.getAllCountries()).thenReturn(mycountries);
		
		//here we are passing the request type (get("/getCountries")) not the method of controller class
		this.mockMvc.perform(get("/getCountries"))
			.andExpect(status().isFound())
			.andDo(print());	
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() throws Exception
	{
		country = new Country(1, "India", "Delhi");
		int countryID = 1;
		
		//Mock
		when(countryServiceMock.getCountryById(countryID)).thenReturn(country);
		
		this.mockMvc.perform(get("/getCountryById/{id}",countryID)) ////here passing Path parameter
			.andExpect(status().isFound())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Delhi"))
			.andDo(print());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() throws Exception
	{
		country = new Country(1, "India", "Delhi");
		String countryName = "India";
		
		//Mock
		when(countryServiceMock.getCountryByName(countryName)).thenReturn(country);
		
		this.mockMvc.perform(get("/getCountries/countryname").param("name", "India"))//here passing Request parameter or Query parameter
			.andExpect(status().isFound())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Delhi"))
			.andDo(print());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() throws Exception
	{
		country = new Country(4, "SriLanka", "Colombo"); // this in the java format need to convert into json format
		
		//Mock
		when(countryServiceMock.addCountry(country)).thenReturn(country);
		
		//converting into JSON format
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(country);
		
		this.mockMvc.perform(post("/addCountry")
							.content(jsonBody)
							.contentType(MediaType.APPLICATION_JSON)
							)
			.andExpect(status().isCreated())
			.andDo(print());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() throws Exception
	{
		country = new Country(4, "SriLanka", "Colombo");
		int countryId = 4;
		
		//Mock
		when(countryServiceMock.getCountryById(countryId)).thenReturn(country);
		when(countryServiceMock.updateCountry(country)).thenReturn(country);
		
		//converting into JSON format
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(country);
		
		this.mockMvc.perform(put("/updateCountry/{id}",countryId )
							.content(jsonBody)
							.contentType(MediaType.APPLICATION_JSON)
							)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("SriLanka"))
			.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Colombo"))
			.andDo(print());
	}
	
	@Test
	@Order(5)
	public void test_deleteCountry() throws Exception
	{
		country = new Country(4, "SriLanka", "Colombo");
		int countryId = 4;
		
		//Mock
		when(countryServiceMock.getCountryById(countryId)).thenReturn(country);
		
		this.mockMvc.perform(delete("/deleteCountry/{id}",countryId ))
			.andExpect(status().isOk());	
		
	}
	
}
