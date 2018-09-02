package com.geog.Controller;

import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
import com.mysql.jdbc.exceptions.jdbc4.*;

import com.geog.DAO.MySqlDAO;
import com.geog.Model.Country;

@ManagedBean
@SessionScoped
public class CountryController {

	// declare variables
	private Country country;
	private MySqlDAO dao;
	private List<Country> countryList = null;

	// default constructor
	public CountryController() {
		try {
			dao = MySqlDAO.getInstance();
		} catch (Exception e) {

		}
		country = new Country();

	}

	// set country code and get update page
	public String getUpdatePage(Country c) {

		country.setCountryCode(c.getCountryCode());

		return "update_country.xhtml";
	}

	// delete passed country code
	public String delete(Country c) {

		try {
			dao.deleteCountry(c.getCountryCode());

		} catch (MySQLIntegrityConstraintViolationException e) {

			FacesMessage message = new FacesMessage(
					"Error: Can Not Delete Country: " + c.getCountryCode() + " as there are associated Region");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;

		} catch (SQLException e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;

		}

		return "list_countries.xhtml";
	}

	public String updateCountry() {

		try {

			dao.updateCont(country.getCountryCode(), country.getCountryName(), country.getCountryDetail());

			return "list_countries.xhtml";

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}

	}

	//// load countries with render view listener before page is rendered
	public void loadCountryies() {

		try {

			countryList = dao.getListOfCountry();

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

		}
	}

	// add country
	public String add() {

		try {

			// check if country exists in database if true display error
			for (Country c : countryList) {

				if (country.getCountryCode().equalsIgnoreCase(c.getCountryCode())) {

					FacesMessage message = new FacesMessage(
							"Error: Country Code: " + country.getCountryCode() + " alerady exists");

					FacesContext.getCurrentInstance().addMessage(null, message);

					return null;

				}

			}

			// if false add country
			dao.addCountry(country.getCountryCode(), country.getCountryName(), country.getCountryDetail());

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}

		return "list_countries.xhtml";
	}

	// setter,getters
	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
