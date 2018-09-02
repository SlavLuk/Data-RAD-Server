package com.geog.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.naming.*;
import javax.sql.*;

import com.geog.Model.*;

public class MySqlDAO {

	//declare variables
	private DataSource mysqlDS;
	private Context context;
	private String jndiName;
	private Connection conn;
	private Statement myStmt;
	private PreparedStatement preMyStmt;
	private ResultSet rs;
	private static MySqlDAO singleMySqlDAO = null;

	private MySqlDAO() throws Exception {

			//initialise connection pool
		context = new InitialContext();
		jndiName = "java:comp/env/jdbc/geography";
		mysqlDS = (DataSource) context.lookup(jndiName);
		conn = mysqlDS.getConnection();

	}

	//create singleton object
	public static MySqlDAO getInstance() throws Exception {
		if (singleMySqlDAO == null) {

			singleMySqlDAO = new MySqlDAO();
		}

		return singleMySqlDAO;
	}

	public List<CityDetails> getSearchDetails(String sign, int population, String conCode, String bySea)
			throws Exception {

		if (population == 0 && conCode == "") {

			preMyStmt = conn.prepareStatement(
					"select c.cty_code,c.cty_name,cnt.co_name," + "r.reg_name,c.population,c.isCoastal,c.areaKM"
							+ " from city c" + " join country cnt" + " on c.co_code =cnt.co_code" + " join region r"
							+ " on c.reg_code=r.reg_code" + " where c.isCoastal=?"

			);

			preMyStmt.setString(1, bySea);

			rs = preMyStmt.executeQuery();

		} else if (conCode == "") {

			preMyStmt = conn.prepareStatement(
					"select c.cty_code,c.cty_name,cnt.co_name," + "r.reg_name,c.population,c.isCoastal,c.areaKM"
							+ " from city c" + " join country cnt" + " on c.co_code =cnt.co_code" + " join region r"
							+ " on c.reg_code=r.reg_code" + " where c.population" + sign + "? and c.isCoastal=?"

			);

			preMyStmt.setInt(1, population);
			preMyStmt.setString(2, bySea);

			rs = preMyStmt.executeQuery();
		} else {

			preMyStmt = conn.prepareStatement("select c.cty_code,c.cty_name,cnt.co_name,"
					+ "r.reg_name,c.population,c.isCoastal,c.areaKM" + " from city c" + " join country cnt"
					+ " on c.co_code =cnt.co_code" + " join region r" + " on c.reg_code=r.reg_code"
					+ " where c.population" + sign + "? and cnt.co_code=? and c.isCoastal=?"

			);

			preMyStmt.setInt(1, population);
			preMyStmt.setString(2, conCode);
			preMyStmt.setString(3, bySea);

			rs = preMyStmt.executeQuery();

		}

		CityDetails city;

		List<CityDetails> c = new ArrayList<>();

		while (rs.next()) {

			city = new CityDetails();

			city.setCityCode(rs.getString("cty_code"));
			city.setCityName(rs.getString("cty_name"));
			city.setCountry(rs.getString("co_name"));
			city.setRegion(rs.getString("reg_name"));
			city.setPopulation(rs.getInt("population"));
			city.setCoastal(rs.getBoolean("isCoastal"));
			city.setArea(rs.getDouble("areaKM"));

			c.add(city);
		}

		preMyStmt.close();
		rs.close();

		return c;

	}
	
	public void addCountry(String cntCode,String cntName,String cntDetl) throws SQLException{
		
		myStmt = conn.createStatement();

		String query = "insert into country values("+"'"+cntCode.toUpperCase()+"',"+"'"+cntName+"',"+"'"+cntDetl+"')";
		myStmt.executeUpdate(query);
		
		myStmt.close();
		
		
	}
	
	
	public void addCity(String cCode,String cntCode,String rCode,String cName,int pop,boolean coast,double area) throws SQLException{
		
		myStmt = conn.createStatement();
		String query = "insert into city values("+"'"+cCode+"',"+"'"+cntCode+"',"+"'"+rCode+"',"+"'"+cName+"',"+pop+",'"+coast+"',"+area+")";
		myStmt.executeUpdate(query);
		
		myStmt.close();
	}
	
	public void deleteCountry(String cntCode) throws SQLException{
		
		myStmt = conn.createStatement();
		String query  = "delete from country where co_code = '"+cntCode+"'";
		myStmt.executeUpdate(query);
		
		myStmt.close();
		
	}
	
	public void updateCont(String cntCode,String cntName,String cntDetail) throws SQLException{
		
		myStmt = conn.createStatement();
		String query = "update country set co_name = '"+cntName+"',co_details = '"+cntDetail+"' where co_code = '"+cntCode+"'";
			
		myStmt.executeUpdate(query);
		
		myStmt.close();
	}
	
	public void addRegion(String cntCode,String regCode,String regName,String regDesc) throws SQLException{
		
		myStmt = conn.createStatement();
		
		String query = "insert into region values("+"'"+cntCode+"',"+"'"+regCode+"',"+"'"+regName+"',"+"'"+regDesc+"')";
		myStmt.executeUpdate(query);
		
		myStmt.close();
		
	}

	public CityDetails getFullDetails(City c) throws Exception {

		preMyStmt = conn.prepareStatement(
				"select c.cty_code,c.cty_name,cnt.co_name," + "r.reg_name,c.population,c.isCoastal,c.areaKM"
						+ " from city c" + " join country cnt" + " on c.co_code =cnt.co_code" + " join region r"
						+ " on c.reg_code=r.reg_code" + " where c.cty_code = ?");
		preMyStmt.setString(1, c.getCityCode());

		rs = preMyStmt.executeQuery();

		CityDetails city = new CityDetails();

		while (rs.next()) {

			city.setCityCode(rs.getString("cty_code"));
			city.setCityName(rs.getString("cty_name"));
			city.setCountry(rs.getString("co_name"));
			city.setRegion(rs.getString("reg_name"));
			city.setPopulation(rs.getInt("population"));
			city.setCoastal(rs.getBoolean("isCoastal"));
			city.setArea(rs.getDouble("areaKM"));
		}

		preMyStmt.close();
		rs.close();

		return city;

	}

	public List<City> getListOfCity() throws Exception {

		myStmt = conn.createStatement();

		String query = "select * from city";
		rs = myStmt.executeQuery(query);
		List<City> c = new ArrayList<>();
		City city;

		while (rs.next()) {

			city = new City();

			city.setCityCode(rs.getString("cty_code"));
			city.setCountryCode(rs.getString("co_code"));
			city.setRegionCode(rs.getString("reg_code"));
			city.setCityName(rs.getString("cty_name"));
			city.setPopulation(rs.getInt("population"));
			city.setByTheSea(rs.getBoolean("isCoastal"));
			city.setArea(rs.getDouble("areaKM"));
			c.add(city);

		}

		myStmt.close();
		rs.close();

		return c;

	}

	public List<Country> getListOfCountry() throws Exception {

		myStmt = conn.createStatement();

		String query = "select * from country";
		rs = myStmt.executeQuery(query);
		List<Country> c = new ArrayList<>();
		Country cnt;

		while (rs.next()) {

			cnt = new Country();

			cnt.setCountryCode(rs.getString("co_code"));
			cnt.setCountryName(rs.getString("co_name"));
			cnt.setCountryDetail(rs.getString("co_details"));

			c.add(cnt);

		}

		myStmt.close();
		rs.close();

		return c;
	}

	public List<Region> getListOfRegion() throws Exception {

		myStmt = conn.createStatement();

		String query = "select * from region";
		rs = myStmt.executeQuery(query);
		List<Region> r = new ArrayList<>();
		Region reg;

		while (rs.next()) {

			reg = new Region();

			reg.setCountryCode(rs.getString("co_code"));
			reg.setRegionCode(rs.getString("reg_code"));
			reg.setRegionName(rs.getString("reg_name"));
			reg.setRegionDescr(rs.getString("reg_desc"));

			r.add(reg);

		}

		myStmt.close();
		rs.close();

		return r;
	}

	
	@PreDestroy
	private void cleanUp(){
		
		try {
			conn.close();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}		
		
	}

}
