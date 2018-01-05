package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.Event;
import com.revature.projects.trms.beans.Location;
import com.revature.projects.trms.beans.Reimbursement;
import com.revature.projects.trms.dao.EventDataAccessObject;
import com.revature.projects.trms.dao.LocationDataAccessObject;
import com.revature.projects.trms.dao.ReimbursementDataAccessObject;

@WebServlet("/new-reimbursement")
public class NewReimbursementServlet extends HttpServlet {
  private static final long serialVersionUID = 7L;
  private ReimbursementDataAccessObject rDao;
  private EventDataAccessObject evDao;
  private LocationDataAccessObject lDao;

  @Override
  public void init() throws ServletException {
    rDao = new ReimbursementDataAccessObject();
    evDao = new EventDataAccessObject();
    lDao = new LocationDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();
    ReimbursementInformation ri = mapper.readValue(json, ReimbursementInformation.class);
    ZonedDateTime zdt = LocalDate.parse(ri.getStartDate()).atStartOfDay(ZoneId.systemDefault());

    rDao.checkConnection();
    evDao.checkConnection();
    lDao.checkConnection();

    Location l = new Location(
      ri.getStreetAddress1(),
      ri.getStreetAddress2(),
      ri.getCity(),
      ri.getState(),
      ri.getZipCode(),
      ri.getCountry()
    );    

    Event e = new Event(
      ri.getEventName(),
      zdt,
      ri.getCost(),
      l,
      ri.getEventType(),
      ri.getGradingFormat()
    );

    
    Reimbursement r = new Reimbursement(
      ri.getEmployeeId(),
      ri.getSupervisorId(),
      ri.getDescription(),
      ri.getJustification(),
      ri.getAmountRequest(),
      ri.getAddInfo(),
      "Pending",
      ZonedDateTime.now(),
      e
    );
    
    lDao.createNew(l);
    l.setLocationId(lDao.getCurrentID());
    evDao.createNew(e);
    e.setEventId(evDao.getCurrentID());
    rDao.createNew(r);
    System.out.println("Reimbursement inserted.");
  }
}

class ReimbursementInformation {
  private int employeeId;
  private int supervisorId;
  private int departmentHeadId;
  private String eventName;
  private String eventType;
  private String gradingFormat;
  private String startDate;
  private BigDecimal cost;
  private String description;
  private String justification;
  private String streetAddress1;
  private String streetAddress2;
  private String city;
  private String state;
  private String zipCode;
  private String country;
  private BigDecimal amountRequest;
  private String addInfo;

  /**
   * @return the departmentHeadId
   */
  public int getDepartmentHeadId() {
    return departmentHeadId;
  }

  /**
   * @return the supervisorId
   */
  public int getSupervisorId() {
    return supervisorId;
  }

  /**
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }

  /**
   * @return the addInfo
   */
  public String getAddInfo() {
    return addInfo;
  }

  /**
   * @return the amountRequest
   */
  public BigDecimal getAmountRequest() {
    return amountRequest;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the cost
   */
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
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
   * @return the justification
   */
  public String getJustification() {
    return justification;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the streetAddress1
   */
  public String getStreetAddress1() {
    return streetAddress1;
  }

  /**
   * @return the streetAddress2
   */
  public String getStreetAddress2() {
    return streetAddress2;
  }

  /**
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * @param departmentHeadId the departmentHeadId to set
   */
  public void setDepartmentHeadId(int departmentHeadId) {
    this.departmentHeadId = departmentHeadId;
  }

  /**
   * @param supervisorId the supervisorId to set
   */
  public void setSupervisorId(int supervisorId) {
    this.supervisorId = supervisorId;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @param addInfo the addInfo to set
   */
  public void setAddInfo(String addInfo) {
    this.addInfo = addInfo;
  }

  /**
   * @param amountRequest the amountRequest to set
   */
  public void setAmountRequest(BigDecimal amountRequest) {
    this.amountRequest = amountRequest;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @param cost the cost to set
   */
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
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
   * @param justification the justification to set
   */
  public void setJustification(String justification) {
    this.justification = justification;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @param streetAddress1 the streetAddress1 to set
   */
  public void setStreetAddress1(String streetAddress1) {
    this.streetAddress1 = streetAddress1;
  }

  /**
   * @param streetAddress2 the streetAddress2 to set
   */
  public void setStreetAddress2(String streetAddress2) {
    this.streetAddress2 = streetAddress2;
  }

  /**
   * @param zipCode the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

}