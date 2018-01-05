package com.revature.projects.trms.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.revature.projects.trms.beans.GradingFormat;
import com.revature.projects.trms.beans.Event;
import com.revature.projects.trms.beans.EventType;

public class EventDataAccessObject extends GenericDataAccessObject<Event> {
  private HashMap<Integer, String> gradingFormats;
  private HashMap<String, Integer> gradingFormatsReversed;
  private HashMap<String, Integer> eventTypesReversed;
  private LocationDataAccessObject locationDAO;
  private EventTypeDataAccessObject eventTypeDAO;

  public EventDataAccessObject() {
    gradingFormats = new HashMap<Integer, String>();
    gradingFormatsReversed = new HashMap<String, Integer>();
    eventTypesReversed = new HashMap<String,Integer>();
    locationDAO = new LocationDataAccessObject();
    eventTypeDAO = new EventTypeDataAccessObject();
    init();
  }

  private void init() {
    GradingFormatDataAccessObject gfDao = new  GradingFormatDataAccessObject();
    List<GradingFormat> gradingFormatList = gfDao.getAll();
    List<EventType> eventTypeList = eventTypeDAO.getAll();

    for(GradingFormat gradingFormat : gradingFormatList) {
      gradingFormats.put(gradingFormat.getGradingFormatId(), gradingFormat.getGradingFormat());
      gradingFormatsReversed.put(gradingFormat.getGradingFormat(), gradingFormat.getGradingFormatId());
    }

    for(EventType eventType : eventTypeList)
      eventTypesReversed.put(eventType.getEventType(), eventType.getEventTypeId());
  }

  private void populateList(List<Event> events, ResultSet rs) throws SQLException {
    while(rs.next()) {
      int eventId = rs.getInt("EventId");
      String eventName = rs.getString("EventName");
      ZonedDateTime startTime = rs.getObject("EventStartTime", ZonedDateTime.class);
      BigDecimal cost = rs.getBigDecimal("Cost");
      int locationId = rs.getInt("LocationID");
      int eventTypeId = rs.getInt("EventTypeID");
      int gradingFormatId = rs.getInt("GradingFormatID");

      events.add(new Event(
        eventId,
        eventName,
        startTime,
        cost,
        locationDAO.getById(locationId),
        eventTypeDAO.getById(eventTypeId).getEventType(),
        gradingFormats.get(gradingFormatId)));
    }
  }
    
  @Override
  public List<Event> getAll() {
    List<Event> events = new  LinkedList<Event>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Event";
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();

      populateList(events, rs);
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
    return events;
  }
  @Override
  public Event getById(int id) {
    Event event = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      String sql = "select * from Event where EventID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      rs = ps.executeQuery();

      if(rs.next()) {
        int eventId = rs.getInt("EventId");
        String eventName = rs.getString("EventName");
        ZonedDateTime startTime = rs.getObject("EventStartTime", ZonedDateTime.class);
        BigDecimal cost = rs.getBigDecimal("Cost");
        int locationId = rs.getInt("LocationID");
        int eventTypeId = rs.getInt("EventTypeID");
        int gradingFormatId = rs.getInt("GradingFormatID");

        event = new Event(
          eventId,
          eventName,
          startTime,
          cost,
          locationDAO.getById(locationId),
          eventTypeDAO.getById(eventTypeId).getEventType(),
          gradingFormats.get(gradingFormatId));
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

    return event;
  }
  @Override
  public List<Event> getByAttribute(String attributeName, Object attributeValue) {
    return null;
  }
  @Override
  public void createNew(Event e) {
    PreparedStatement ps = null;
    
    try {
      String sql = "insert into Event (EventName, EventStartTime, Cost, LocationID, EventTypeID, GradingFormatID) values (?, ?, ?, ?, ?, ?)";
      ps = connection.prepareStatement(sql);
      ps.setString(1, e.getEventName());
      ps.setTimestamp(2, Timestamp.from(e.getStartTime().toInstant()));
      ps.setBigDecimal(3, e.getCost());
      ps.setInt(4, e.getLocation().getLocationId());
      ps.setInt(5, eventTypesReversed.get(e.getEventType()));
      ps.setInt(6, gradingFormatsReversed.get(e.getGradingFormat()));
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
    PreparedStatement ps = null;

    try {
      String sql = "delete from Event where EventID=?";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(ps != null && !ps.isClosed()) ps.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
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

  @Override
  public int getCurrentID() {
    CallableStatement cs = null;
    int currentID = 0;

    try {
      String sql = "{ CALL SP_Get_Curr_EventID(?) }";
      cs = connection.prepareCall(sql);
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      currentID = cs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(cs != null && !cs.isClosed()) cs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return currentID;
  }
}