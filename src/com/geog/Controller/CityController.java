package com.geog.Controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.MySqlDAO;
import com.geog.Model.City;
import com.geog.Model.CityDetails;

@ManagedBean
@SessionScoped
public class CityController {

	// declare variables
	private City city;
	private MySqlDAO dao;
	private List<City> cityList = null;
	private CityDetails cityDetail;

	// default constructor
	public CityController() {

		try {
			dao = MySqlDAO.getInstance();
		} catch (Exception e) {

		}

		city = new City();

	}

	// add city method
	public String addCity() {

		try {
			// iterate city list
			for (City c : cityList) {
				// check if city code exists if true return with error message
				if (city.getCityCode().equalsIgnoreCase(c.getCityCode())) {

					FacesMessage message = new FacesMessage(
							"Error: City Code: " + city.getCityCode() + " already exist");

					FacesContext.getCurrentInstance().addMessage(null, message);

					return null;
				}

			}

			for (City ct : cityList) {
				// check if country code and region code exist if true add city
				// into database and return to list of cities page
				if (city.getCountryCode().equalsIgnoreCase(ct.getCountryCode())
						&& city.getRegionCode().equalsIgnoreCase(ct.getRegionCode())) {

					dao.addCity(city.getCityCode().toUpperCase(), city.getCountryCode().toUpperCase(),
							city.getRegionCode().toUpperCase(), city.getCityName(), city.getPopulation(),
							city.isByTheSea(), city.getArea());

					return "list_cities.xhtml";

				}

			}
			// if false no region code or country code exist return
			FacesMessage message = new FacesMessage("Error: Region Code: " + city.getRegionCode() + " Country Code: "
					+ city.getCountryCode() + " do not exist");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;

			// any exception occurred display error message
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}
	}

	// load cities with render view listener before page is rendered
	public void loadCities() {

		try {

			cityList = dao.getListOfCity();

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

		}

	}

	// show city's full details and pass city object to database
	public String showCity(City c) {

		try {

			cityDetail = dao.getFullDetails(c);

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;

		}

		return "city_details.xhtml";
	}

	// setters,getters methods
	public List<City> getCityList() {

		return cityList;
	}

	public void setCityList(List<City> cityList) {

		this.cityList = cityList;
	}

	public CityDetails getCityDetail() {
		return cityDetail;
	}

	public void setCityDetail(CityDetails cityDetail) {
		this.cityDetail = cityDetail;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
