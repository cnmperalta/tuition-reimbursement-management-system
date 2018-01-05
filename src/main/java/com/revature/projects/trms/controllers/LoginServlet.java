package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.Department;
import com.revature.projects.trms.beans.Employee;
import com.revature.projects.trms.beans.EmployeeType;
import com.revature.projects.trms.dao.DepartmentDataAccessObject;
import com.revature.projects.trms.dao.EmployeeDataAccessObject;
import com.revature.projects.trms.dao.EmployeeTypeDataAccessObject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private EmployeeDataAccessObject eDao;
  private EmployeeTypeDataAccessObject etDao;
  private DepartmentDataAccessObject dDao;

  @Override
  public void init() throws ServletException {
    eDao = new EmployeeDataAccessObject();
    dDao = new DepartmentDataAccessObject();
    etDao =  new EmployeeTypeDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();
    Employee emp = null;
    Department dept = null;
    EmployeeType empType = null;
    LoginCredentials lc = null;
    SessionInformation si = null;
    String jsonResponse = null;

    eDao.checkConnection();
    dDao.checkConnection();
    etDao.checkConnection();

    System.out.println(json);
    lc = mapper.readValue(json, LoginCredentials.class);
    emp = eDao.getByEmailAddress(lc.getEmailAddress());
    dept = dDao.getById(emp.getDepartmentId());
    empType = etDao.getById(emp.getEmployeeTypeId());

    System.out.println(emp);

    if(emp.getLastLogin() != null)
      si = new SessionInformation(emp.getEmployeeId(), emp.getDirectSupervisorId(), dept.getDepartmentHeadId(), empType.getEmployeeType(), emp.getLastLogin(), true);
    else
      si = new SessionInformation(emp.getEmployeeId(), emp.getDirectSupervisorId(), dept.getDepartmentHeadId(), empType.getEmployeeType(), emp.getLastLogin(), false);
    
    session.setAttribute("EmployeeID", emp.getEmployeeId());

    jsonResponse = mapper.writeValueAsString(si);
    System.out.println(jsonResponse);

    resp.setContentType("application/json");
    resp.getWriter().write(jsonResponse);
  }
}

class SessionInformation {
  private int employeeId;
  private int directSupervisorId;
  private int departmentHeadId;
  private String employeeType;
  private ZonedDateTime lastLogin;
  private boolean loggedIn;

  public SessionInformation() {}

  public SessionInformation(int employeeId, int directSupervisorId, int departmentHeadId, String employeeType, ZonedDateTime lastLogin, boolean loggedIn) {
    this.departmentHeadId = departmentHeadId;
    this.directSupervisorId = directSupervisorId;
    this.employeeId = employeeId;
    this.employeeType = employeeType;
    this.lastLogin = lastLogin;
    this.loggedIn = loggedIn;
  }

  /**
   * @return the employeeType
   */
  public String getEmployeeType() {
    return employeeType;
  }

  /**
   * @return the departmentHeadId
   */
  public int getDepartmentHeadId() {
    return departmentHeadId;
  }

  /**
   * @return the directSupervisorId
   */
  public int getDirectSupervisorId() {
    return directSupervisorId;
  }

  /**
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }

  /**
   * @return the lastLogin
   */
  public ZonedDateTime getLastLogin() {
    return lastLogin;
  }

  public boolean getLoggedIn() {
    return loggedIn;
  }

  /**
   * @param employeeType the employeeType to set
   */
  public void setEmployeeType(String employeeType) {
    this.employeeType = employeeType;
  }

  /**
   * @param departmentHeadId the departmentHeadId to set
   */
  public void setDepartmentHeadId(int departmentHeadId) {
    this.departmentHeadId = departmentHeadId;
  }

  /**
   * @param directSupervisorId the directSupervisorId to set
   */
  public void setDirectSupervisorId(int directSupervisorId) {
    this.directSupervisorId = directSupervisorId;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @param lastLogin the lastLogin to set
   */
  public void setLastLogin(ZonedDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  /**
   * @param loggedIn the loggedIn to set
   */
  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }
}

class LoginCredentials {
  private String emailAddress;
  private String password;

  public LoginCredentials() {}
  public LoginCredentials(String emailAddress, String password) {
    this.emailAddress = emailAddress;
    this.password = password;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param emailAddress the emailAddress to set
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "Login [ " + emailAddress + ", " + password + " ]";
  }
}