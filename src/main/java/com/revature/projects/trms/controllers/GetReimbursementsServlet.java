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
import com.revature.projects.trms.beans.Reimbursement;
import com.revature.projects.trms.dao.ReimbursementDataAccessObject;

@WebServlet("/get-reimbursements")
public class GetReimbursementsServlet extends HttpServlet {
  private static final long serialVersionUID = 6L;
  private ReimbursementDataAccessObject rDao;

  @Override
  public void init() throws ServletException {
    rDao = new ReimbursementDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Reimbursement> reimbursements = null;
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();;
    EmployeeID eid = mapper.readValue(json, EmployeeID.class);
    String jsonResponse = null;

    rDao.checkConnection();
    
    if(eid.getEmployeeType().equals("Employee")) {
      reimbursements = rDao.getByAttribute("RequesterID", eid.getRequesterID());
    } else {
      reimbursements = rDao.getByAttribute("AssignTo", eid.getRequesterID());
    }
    
    jsonResponse = mapper.writeValueAsString(reimbursements);
    System.out.println(jsonResponse);
    resp.getWriter().write(jsonResponse);
    resp.setContentType("application/json");
  }
}

class EmployeeID {
  private int requesterID;
  private String employeeType;

  /**
   * @return the employeeType
   */
  public String getEmployeeType() {
    return employeeType;
  }

  /**
   * @return the employeeID
   */
  public int getRequesterID() {
    return requesterID;
  }

  /**
   * @param employeeID the employeeID to set
   */
  public void setRequesterID(int requesterID) {
    this.requesterID = requesterID;
  }

  /**
   * @param employeeType the employeeType to set
   */
  public void setEmployeeType(String employeeType) {
    this.employeeType = employeeType;
  }
}
