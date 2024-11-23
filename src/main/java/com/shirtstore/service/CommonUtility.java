package com.shirtstore.service;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class CommonUtility {
	public static void generateCountriesList(HttpServletRequest request) {
		String[] countryCodes = Locale.getISOCountries();
		
		Map<String, String> mapCountries = new TreeMap<String, String>();
		
		for(String countryCode : countryCodes) {
			Locale locale = new Locale(" ", countryCode);
			String code = locale.getCountry();
			String name = locale.getDisplayCountry();
			
			mapCountries.put(name, code);
		}
		request.setAttribute("mapCountries", mapCountries);
	}
	
}
