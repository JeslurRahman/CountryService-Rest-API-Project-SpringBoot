package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {
	
	/*
	 * Rest Template is used to create applications that consume RESTful Web Services
	 * To test the Integration test, make sure that the application should started
	*/
	
	@Test
	@Order(1)
	void integrationTest_getAllCountries() throws JSONException
	{
		/*Keeping this expected json data inside the test cases is not the best practices
		 * Instead of this we can specify this jason data in the third party file (eg. .json or .txt) and 
		 * we can call it through java code and convert into string is the best practice
		 */
		String expected = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"China\",\r\n"
				+ "        \"countryCapital\": \"Beijing\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"countryName\": \"Japan\",\r\n"
				+ "        \"countryCapital\": \"Tokyo\"\r\n"
				+ "    }\r\n"
				+ "]";
		
		//Rest Template
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountries", String.class);
		
		//Verification
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		//Assertion
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(2)
	void integrationTest_getCountryById() throws JSONException
	{
		String expected = "{\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "}";
		
		//Rest Template
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountryById/1", String.class);
		
		//Verification
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		//Assertion
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(3)
	void integrationTest_getCountryByName() throws JSONException
	{
		String expected = "{\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"India\",\r\n"
				+ "        \"countryCapital\": \"Delhi\"\r\n"
				+ "}";
		
		//Rest Template
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountries/countryname?name=India", String.class);
		
		//Verification
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		
		//Assertion
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(4)
	void integrationTest_addCountry() throws JSONException
	{
		Country country = new Country(4,"Germany","Berlin");
		
		String expected = "{\r\n"
				+ "        \"id\": 4,\r\n"
				+ "        \"countryName\": \"Germany\",\r\n"
				+ "        \"countryCapital\": \"Berlin\"\r\n"
				+ "}";
		
		//Rest Template
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addCountry",request, String.class);
		
		//Verification
		System.out.println(response.getBody());
		
		//Assertion
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(5)
	void integrationTest_updateCountry() throws JSONException
	{
		Country country = new Country(4,"Sri Lanka","Colombo");
		
		String expected = "{\r\n"
				+ "        \"id\": 4,\r\n"
				+ "        \"countryName\": \"Sri Lanka\",\r\n"
				+ "        \"countryCapital\": \"Colombo\"\r\n"
				+ "}";
		
		//Rest Template
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/updateCountry/4", HttpMethod.PUT, request, String.class);
		
		//Verification
		System.out.println(response.getBody());
		
		//Assertion
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(6)
	void integrationTest_deleteCountry() throws JSONException
	{
		Country country = new Country(4,"Sri Lanka","Colombo");
		
		String expected = "{\r\n"
				+ "        \"id\": 4,\r\n"
				+ "        \"countryName\": \"Sri Lanka\",\r\n"
				+ "        \"countryCapital\": \"Colombo\"\r\n"
				+ "}";
		
		//Rest Template
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/deleteCountry/4", HttpMethod.DELETE, request, String.class);
		
		//Verification
		System.out.println(response.getBody());
		
		//Assertion
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
		//we can call this instead of writing above lines of code but we can not do assertion
		//restTemplate.delete("http://localhost:8080/deleteCountry/4"); 
	}
}
