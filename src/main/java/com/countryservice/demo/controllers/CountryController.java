package com.countryservice.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
}
