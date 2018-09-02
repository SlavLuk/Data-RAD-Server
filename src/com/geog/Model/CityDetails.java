package com.geog.Model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CityDetails {
	private String cityCode;
	private String cityName;
	private String country;
	private String region;
	private int population;
	private boolean coastal;
	private double area;
	
	public CityDetails(){
		
	}


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}



	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}
	
	public boolean isCoastal() {
		return coastal;
	}


	public void setCoastal(boolean coastal) {
		this.coastal = coastal;
	}

}
