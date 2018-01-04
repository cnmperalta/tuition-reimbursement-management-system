package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.Employee;
import com.revature.projects.trms.dao.EmployeeDataAccessObject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();
    Employee emp = null;
    EmployeeDataAccessObject eDao = new EmployeeDataAccessObject();
    LoginCredentials lc = null;

    lc = mapper.readValue(json, LoginCredentials.class);
    emp = eDao.getByEmailAddress(lc.getEmailAddress());

    resp.setContentType("application/json");
    mapper.writeValue(resp.getOutputStream(), emp);
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