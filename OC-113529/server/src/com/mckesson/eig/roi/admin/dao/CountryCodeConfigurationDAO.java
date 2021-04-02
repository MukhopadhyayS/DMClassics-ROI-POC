package com.mckesson.eig.roi.admin.dao;

import java.util.List;

import com.mckesson.eig.roi.admin.model.Country;
import com.mckesson.eig.roi.base.dao.ROIDAO;



public interface CountryCodeConfigurationDAO extends ROIDAO {
    
    /**
     * This method updates an existing entry in CountryCodeConfig Table.
     * @param country
     * 
     */
    void updateCountryCode(Country country);

    
    /**
     * This method retrieveAllCountries fetches master list of countries.
     * @return List<Country>
     */
    List<Country> retrieveAllCountries();    

}
