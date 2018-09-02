package com.geog.Model;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Country {
	
	private String countryCode;
	private String countryName;
	private String countryDetail;

	public Country() {

	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryDetail() {
		return countryDetail;
	}

	public void setCountryDetail(String countryDetail) {
		this.countryDetail = countryDetail;
	}

}
