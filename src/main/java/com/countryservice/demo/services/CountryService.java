package com.countryservice.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.AddResponse;
import com.countryservice.demo.repositories.CountryRepository;

//If we need to inject this class into other classes we need to add this annotation
@Component
@Service //we have to mention this because this is micro service class
public class CountryService 
{
	@Autowired
	CountryRepository countryRepository;
	
	public List<Country> getAllCountries()
	{
		return countryRepository.findAll();
	}
	
	public Country getCountryById(int id)
	{
		return countryRepository.findById(id).get();
	}
	
	public Country getCountryByName(String countryName)
	{
		Country country = null;
		List<Country> countries = countryRepository.findAll();
	
		//for each loop
		for(Country coun: countries)
		{
			if(coun.getCountryName().equalsIgnoreCase(countryName))
			{
				country = coun;
				break;
			}
		}

		return  country;
	}
	
	public Country addCountry(Country country)
	{
		country.setId(getMaxId());
		countryRepository.save(country);
		return country;
	}
	
	//Utility method to get max id (generate ID automatically for each new record)
	public int getMaxId()
	{
		return countryRepository.findAll().size()+ 1;
	}
	
	public Country updateCountry(Country country)
	{
		countryRepository.save(country);
		return country;
	}
	
	public AddResponse deleteCountry(int id)
	{
		countryRepository.deleteById(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country Deleted succesfully!");
		res.setId(id);
		return res;
	}
	
	
	
	//HashMap 
	//HashMap in Java stores the data in (Key, Value) pairs, and you can access them by an index of another type (e.g. an Integer).
	//One object is used as a key (index) to another object (value).
	/*
	static HashMap<Integer, Country> countryIdMap;
	
	public CountryService()
	{
		countryIdMap = new HashMap<Integer, Country>();
		
		Country indiaCountry = new Country(1, "India", "Delhi");
		Country usaCountry = new Country(2, "USA", "Washington");
		Country ukCountry = new Country(3, "UK", "London");
		
		countryIdMap.put(1,indiaCountry);
		countryIdMap.put(2,usaCountry);
		countryIdMap.put(3,ukCountry);
	}
	
	public List getAllCountries()
	{
		List countries = new ArrayList(countryIdMap.values());
		return countries;
	}
	
	public Country getCountryById(int id)
	{
		Country country = countryIdMap.get(id);
		return country;
	}
	
	public Country getCountryByName(String countryName)
	{
		Country country = null;
		for(int i : countryIdMap.keySet())
		{
			if(countryIdMap.get(i).getCountryName().equals(countryName))
			{
				country = countryIdMap.get(i);
			}
		}	
		return country;
	}
	
	public Country addCountry(Country country)
	{
		country.setId(getMaxId());
		countryIdMap.put(country.getId(), country);
		return country;
		
	}
	
	//Utility method to get max id (generate ID automatically for each new record)
	public static int getMaxId()
	{
		int max = 0;
		for(int id:countryIdMap.keySet())
		{
			if(max <= id )
				max = id;
		}
		return max + 1;
	}
	
	public Country updateCountry(Country country)
	{
		if(country.getId() > 0)
		{
			countryIdMap.put(country.getId(), country);
		}
		return country;
	}
	
	public AddResponse deleteCountry(int id)
	{
		countryIdMap.remove(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country deleted...");
		res.setId(id);
		return res;
	}
	*/

}
