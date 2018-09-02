package com.geog.Controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.geog.DAO.MongoDAO;
import com.geog.DAO.MySqlDAO;
import com.geog.Model.Country;
import com.geog.Model.HeadState;

@ManagedBean
@SessionScoped
public class HeadStateController {

	// declare variables
	private MongoDAO mongo;
	private Country country;
	private HeadState head;
	private MySqlDAO dao;
	private List<Country> countryList = null;
	private List<HeadState> listHeads = null;

	// default constructor
	public HeadStateController() {
		try {
			dao = MySqlDAO.getInstance();
			mongo = new MongoDAO();

		} catch (Exception e) {

		}

		head = new HeadState();
	}

	public String deleteHead(HeadState h) {

		try {

			mongo.deleteHeadOfState(h.get_id().toUpperCase());

			return "list_head_of_state.xhtml";

		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}

	}

	// before adding head state check if country exists in database
	public String addHead() {

		boolean found = false;

		try {

			countryList = dao.getListOfCountry();

			for (Country c : countryList) {

				if (head.get_id().equalsIgnoreCase(c.getCountryCode())) {

					found = true;

				}

			}
			// add head of state into database
			if (found) {

				for (HeadState h : listHeads) {

					// check if head of state already there
					if (h.get_id().equalsIgnoreCase(head.get_id())) {

						FacesMessage message = new FacesMessage(
								"Error: Head Of State for this Country : " + head.get_id() + " already exists");

						FacesContext.getCurrentInstance().addMessage(null, message);

						return null;

					}

				}
				mongo.addHeadOfState(head.get_id().toUpperCase(), head.getHeadOfState());

				return "list_head_of_state.xhtml";

			} else {

				FacesMessage message = new FacesMessage("Error: Country Code: " + head.get_id() + " not found");

				FacesContext.getCurrentInstance().addMessage(null, message);

				return null;
			}

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}

	}

	// load page before rendering
	public void loadHeadsOfState() {

		try {

			listHeads = mongo.getHeadOfSate();

		} catch (Exception e) {

			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");

			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	}

	// setters ,getters
	public List<HeadState> getListHeads() {
		return listHeads;
	}

	public void setListHeads(List<HeadState> listHeads) {
		this.listHeads = listHeads;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public HeadState getHead() {
		return head;
	}

	public void setHead(HeadState head) {
		this.head = head;
	}

}
