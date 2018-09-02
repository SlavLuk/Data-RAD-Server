package com.geog.Controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.MySqlDAO;
import com.geog.Model.CityDetails;

@ManagedBean
@SessionScoped
public class SearchCityController {
	
	// declare variables
	private MySqlDAO dao;
	private List<CityDetails> cityList = null;
	private String sign;
	private String population;
	private String countryCode;
	private String byTheSea;

	public SearchCityController(){

		try {
			dao = MySqlDAO.getInstance();
		} catch (Exception e) {
			
		
		}
	}

	public String showCity(String sign, String population, String conCode, String bySea) {

		//convert sign letters into sign
		if (sign.equals("lt")) {

			sign = "<";
		} else if (sign.equals("gt")) {

			sign = ">";
		} else if (sign.equals("eq")) {
			sign = "=";
		}

		int populat = 0;
		
		try {//convert string into int

			if (population == "") {

				populat = 0;
				
			}else{
				
				populat = Integer.parseInt(population);
			}

			

		} catch (Exception e) {

			
		}

		try {//if database not available
			
			cityList = dao.getSearchDetails(sign, populat, conCode, bySea);

		} catch (Exception e) {

			FacesMessage message = 
					new FacesMessage("Error: Cannot connect to Database");

					FacesContext.getCurrentInstance().addMessage(null, message);
					
					return null;

		}

		return "searchResult.xhtml";
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getByTheSea() {
		return byTheSea;
	}

	public void setByTheSea(String byTheSea) {
		this.byTheSea = byTheSea;
	}

	public List<CityDetails> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityDetails> cityList) {
		this.cityList = cityList;
	}

}
