package com.geog.Controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.MySqlDAO;
import com.geog.Model.Region;

@ManagedBean
@SessionScoped
public class RegionController {

	//declare variables
	private Region region;
	private MySqlDAO dao;
	private List<Region> regionList = null;

	public RegionController(){

		region = new Region();
		try {
			dao = MySqlDAO.getInstance();
		} catch (Exception e) {
		
		}

	}

	public String addRegion() {

		try {

			for (Region r : regionList) {

				if (region.getCountryCode().equalsIgnoreCase(r.getCountryCode())
						&& region.getRegionCode().equalsIgnoreCase(r.getRegionCode())) {

					FacesMessage message = new FacesMessage("Error: Country Code: " + region.getCountryCode()
							+ " and Region Code " + region.getRegionCode() + " already exist");

					FacesContext.getCurrentInstance().addMessage(null, message);

					return null;
				}

			}

			for (Region r : regionList) {

				if (region.getCountryCode().equalsIgnoreCase(r.getCountryCode())) {

					dao.addRegion(region.getCountryCode().toUpperCase(), region.getRegionCode().toUpperCase(),
							region.getRegionName(), region.getRegionDescr());

					return "list_regions.xhtml";
				}

			}

			FacesMessage message = new FacesMessage(
					"Error: Country Code: " + region.getCountryCode() + " does not exist");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}

	}

	public void loadRegions() {

		try {

		
			regionList = dao.getListOfRegion();

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

		}
	}

	public List<Region> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
