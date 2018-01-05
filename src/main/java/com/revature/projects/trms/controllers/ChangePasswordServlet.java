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
import com.revature.projects.trms.beans.Employee;
import com.revature.projects.trms.dao.EmployeeDataAccessObject;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
  private static final long serialVersionUID = 5L;
  private EmployeeDataAccessObject eDao;

  @Override
  public void init() throws ServletException {
    eDao = new EmployeeDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();
    Employee emp = null;
    Password p = mapper.readValue(json, Password.class);

    eDao.updateAttribute(p.getEmployeeId(), "Password", p.getPassword());
    eDao.updateAttribute(p.getEmployeeId(), "LastLogin", ZonedDateTime.now());
    System.out.println("Employee updated.");
    session.setAttribute("LoggedIn", true);
    mapper.writeValue(resp.getOutputStream(), new LoggedIn(true));
  }
}

class Password {
  private int employeeId;
  private String password;

  public Password() {}

  /**
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }
  
  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
}

class LoggedIn {
  private boolean loggedIn;

  public LoggedIn() {}

  public LoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public boolean getLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }
}