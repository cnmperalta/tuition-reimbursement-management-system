package com.revature.projects.trms.beans;

import java.math.BigDecimal;

public class EventType {
  private int eventTypeId;
  private String eventType;
  private BigDecimal coverage;

  public EventType() {}

  public EventType(String eventType, BigDecimal coverage) {
    this.eventType = eventType;
    this.coverage = coverage;
  }

  public EventType(int eventTypeId, String eventType, BigDecimal coverage) {
    this.eventTypeId = eventTypeId;
    this.eventType = eventType;
    this.coverage = coverage;
  }

  /**
   * @return the coverage
   */
  public BigDecimal getCoverage() {
    return coverage;
  }

  /**
   * @return the eventType
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * @return the eventTypeId
   */
  public int getEventTypeId() {
    return eventTypeId;
  }

  /**
   * @param coverage the coverage to set
   */
  public void setCoverage(BigDecimal coverage) {
    this.coverage = coverage;
  }

  /**
   * @param eventType the eventType to set
   */
  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  /**
   * @param eventTypeId the eventTypeId to set
   */
  public void setEventTypeId(int eventTypeId) {
    this.eventTypeId = eventTypeId;
  }

  @Override
  public String toString() {
    return "EventType [ " + eventTypeId + ", " + eventType + " covered at " + coverage + "% ]";
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}