package com.revature.projects.trms.beans;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Event {
  private int eventId;
  private String eventName;
  private ZonedDateTime startTime;
  private BigDecimal cost;
  private Location location;
  private String eventType;
  private String gradingFormat;

  public Event() {}

  public Event(String eventName, ZonedDateTime startTime, BigDecimal cost, Location location, String eventType, String gradingFormat) {
    this.eventName = eventName;
    this.startTime = startTime;
    this.cost = cost;
    this.location = location;
    this.eventType = eventType;
    this.gradingFormat = gradingFormat;
  }

  public Event(int eventId, String eventName, ZonedDateTime startTime, BigDecimal cost, Location location, String eventType, String gradingFormat) {
    this.eventId = eventId;
    this.eventName = eventName;
    this.startTime = startTime;
    this.cost = cost;
    this.location = location;
    this.eventType = eventType;
    this.gradingFormat = gradingFormat;
  }

  /**
   * @return the cost
   */
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * @return the eventId
   */
  public int getEventId() {
    return eventId;
  }

  /**
   * @return the eventName
   */
  public String getEventName() {
    return eventName;
  }

  /**
   * @return the eventType
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * @return the gradingFormat
   */
  public String getGradingFormat() {
    return gradingFormat;
  }

  /**
   * @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * @return the startTime
   */
  public ZonedDateTime getStartTime() {
    return startTime;
  }

  /**
   * @param cost the cost to set
   */
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  /**
   * @param eventId the eventId to set
   */
  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  /**
   * @param eventName the eventName to set
   */
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  /**
   * @param eventType the eventType to set
   */
  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  /**
   * @param gradingFormat the gradingFormat to set
   */
  public void setGradingFormat(String gradingFormat) {
    this.gradingFormat = gradingFormat;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * @param startTime the startTime to set
   */
  public void setStartTime(ZonedDateTime startTime) {
    this.startTime = startTime;
  }

  @Override
  public String toString() {
    return "Event [ " + eventId + ", " + eventName + " at " + location + " on " + startTime.toString() + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}