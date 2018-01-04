package com.revature.projects.trms.dao;

import com.revature.projects.trms.beans.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LocationDataAccessObject extends GenericDataAccessObject<Location> {

  public LocationDataAccessObject() {
    super();
  }
	@Override
	public List<Location> getAll() {
    List<Location> locations = new LinkedList<Location>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Location";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      while(rs.next()) {
        int locationId = rs.getInt("LocationID");
        String streetAddress1 = rs.getString("StreetAddress1");
        String streetAddress2 = rs.getString("StreetAddress2");
        String city = rs.getString("City");
        String state = rs.getString("State");
        String zipCode = rs.getString("ZipCode");
        String country = rs.getString("Country");

        locations.add(new Location(locationId, streetAddress1, streetAddress2, city, state, zipCode, country));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return locations;
	}

	@Override
	public Location getById(int id) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Location l = null;

    try {
      String sql = "select * from Location where LocationID=?";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      if(rs.next()) {
        int locationId = rs.getInt("LocationID");
        String streetAddress1 = rs.getString("StreetAddress1");
        String streetAddress2 = rs.getString("StreetAddress2");
        String city = rs.getString("City");
        String state = rs.getString("State");
        String zipCode = rs.getString("ZipCode");
        String country = rs.getString("Country");

        l = new Location(locationId, streetAddress1, streetAddress2, city, state, zipCode, country);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
		return l;
	}

	@Override
	public List<Location> getByAttribute(String attributeName, Object attributeValue) {
		return null;
	}

	@Override
	public void createNew(Location e) {
    PreparedStatement ps = null;
    
    try {
      String sql = "insert into Location (StreetAddress1, StreetAddress2, City, State, ZipCode, Country) values (?, ?, ?, ?, ?, ?)";
      ps = connection.prepareStatement(sql);
      ps.setString(1, e.getStreetAddress1());;
      ps.setString(2, e.getStreetAddress2());;
      ps.setString(3, e.getCity());;
      ps.setString(4, e.getState());;
      ps.setString(5, e.getZipCode());;
      ps.setString(6, e.getCountry());
      ps.execute();
    } catch (SQLException ex) {
      ex.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
	}

	@Override
	public void deleteById(int id) {
		
	}

	@Override
	public void deleteByAttribute(String attributeName, Object attributeValue) {
		
	}

	@Override
	public void updateAttribute(int id, String attributeName, Object attributeValue) {
		
	}

	@Override
	public int getCount() {
		return 0;
	}

}