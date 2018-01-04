package com.revature.projects.trms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.revature.projects.trms.beans.EventType;

public class EventTypeDataAccessObject extends GenericDataAccessObject<EventType> {

	@Override
	public List<EventType> getAll() {
		List<EventType> eventTypes = new LinkedList<EventType>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from EventType";

      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      
      while(rs.next()) {
        int eventTypeId = rs.getInt("EventTypeID");
        String eventType = rs.getString("EventType");
        BigDecimal coverage = rs.getBigDecimal("ReimbursementCoveragePercent");

        eventTypes.add(new EventType(eventTypeId, eventType, coverage));
      }
    } catch (SQLException e) {
      //TODO: handle exception
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        System.err.println("Error closing PreparedStatement for getting data from reference table EventType.");
      }

      try {
        if(rs != null && !rs.isClosed()) rs.close();
      } catch (SQLException e) {
        System.err.println("Error closing ResultSet for getting data from reference table EventType.");
      }
    }

    return eventTypes;
	}

	@Override
	public EventType getById(int id) {
    EventType eventType = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from EventType where EventTypeID=?";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      if(rs.next()) {
        int eventTypeId = rs.getInt("EventTypeID");
        String eventTypeStr = rs.getString("EventType");
        BigDecimal coverage = rs.getBigDecimal("ReimbursementCoveragePercent");

        eventType = new EventType(eventTypeId, eventTypeStr, coverage);
      }
    } catch (Exception e) {
      //TODO: handle exception
    }
		return eventType;
	}

	@Override
	public List<EventType> getByAttribute(String attributeName, Object attributeValue) {
		return null;
	}

	@Override
	public void createNew(EventType e) {
		
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