package com.countryservice.demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.services.CountryService;

@RestController
public class CountryController {
	
	//CountryService countryService = new CountryService(); 
	//No need to create any objects instead of that we can use Autowired which is available feature in spring boot
	
	/*when we do Autowired or Dependency Injection which class dependency we inject here we need to add something in that class
	 -like go to the CountryService class and add the annotation call (@Component) 
	 */
	
	@Autowired
	CountryService countryService;
	
	@GetMapping("/getCountries")
	public ResponseEntity<List<Country>> getCountries()
	{
		try 
		{
			List<Country> countries = countryService.getAllCountries();
			return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getCountryById/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable(value="id") int id)
	{
		try 
		{
			Country country = countryService.getCountryById(id);
			
			/*when return a object not only returning the object along with that if we need to return http response, 
			 * status code and there are multiple things for that we can use special class call "ResponseEntity<>"  */
			return new ResponseEntity<Country>(country,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getCountries/countryname")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value="name") String countryName)
	{
		try 
		{
			Country country = countryService.getCountryByName(countryName);
			return new ResponseEntity<Country>(country,HttpStatus.FOUND);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addCountry")
	public ResponseEntity<Country> addCountry(@RequestBody Country country)
	{
		try 
		{
			country = countryService.addCountry(country);
			return new ResponseEntity<Country>(country,HttpStatus.CREATED);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/updateCountry/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value="id") int id, @RequestBody Country country)
	{
		try 
		{
			Country existCountry = countryService.getCountryById(id);
			
			existCountry.setCountryName(country.getCountryName());
			existCountry.setCountryCapital(country.getCountryCapital());
			
			Country updatedCountry =countryService.updateCountry(existCountry);
			return new ResponseEntity<Country>(updatedCountry,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
	}
	
	@DeleteMapping("/deleteCountry/{id}")
	public ResponseEntity<Country> deleteCountry(@PathVariable(value="id") int id)
	{
		Country country = null;
		try
		{
			country = countryService.getCountryById(id);
			countryService.deleteCountry(country);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Country>(country, HttpStatus.OK);
	}
	
	/*
	@DeleteMapping("/deleteCountry/{id}")
	public AddResponse deleteCountry(@PathVariable(value="id") int id)
	{
		return countryService.deleteCountry(id);
	}
	*/
	
	//This implementation was added before adding repository class 
	/*
	@Autowired
	CountryService countryService;
	
	@GetMapping("/getCountries")
	public List getCountries()
	{
		return countryService.getAllCountries();
	}
	
	@GetMapping("/getCountryById/{id}")
	public Country getCountryById(@PathVariable(value="id") int id)
	{
		return countryService.getCountryById(id);
	}
	
	@GetMapping("/getCountryByName/countryName")
	public Country getCountryByName(@RequestParam(value="name") String countryName)
	{
		return countryService.getCountryByName(countryName);
	}
	
	@PostMapping("/addCountry")
	public Country addCountry(@RequestBody Country country)
	{
		return countryService.addCountry(country);
	}
	
	@PutMapping("/updateCountry")
	public Country updateCountry(@RequestBody Country country)
	{
		return countryService.updateCountry(country);
	}
	
	@DeleteMapping("/deleteCountry/{id}")
	public AddResponse deleteCountry(@PathVariable(value="id") int id)
	{
		return countryService.deleteCountry(id);
	}
	*/
	
	
}
