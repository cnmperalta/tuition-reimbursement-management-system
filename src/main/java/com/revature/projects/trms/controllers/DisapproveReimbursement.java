package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.ReimbursementStatus;
import com.revature.projects.trms.dao.DepartmentDataAccessObject;
import com.revature.projects.trms.dao.EmployeeDataAccessObject;
import com.revature.projects.trms.dao.ReimbursementDataAccessObject;
import com.revature.projects.trms.dao.ReimbursementStatusDataAccessObject;

@WebServlet("/disapprove-reimbursement")
public class DisapproveReimbursement extends HttpServlet {
  private static final long serialVersionUID = 11L;
  private ReimbursementDataAccessObject rDao;
  private HashMap<String, Integer> reimbursementStatuses;

  @Override
  public void init() throws ServletException {
    ReimbursementStatusDataAccessObject rsDao = new ReimbursementStatusDataAccessObject();
    List<ReimbursementStatus> reimbursementStatusList = rsDao.getAll();
    rDao = new ReimbursementDataAccessObject();
    reimbursementStatuses = new HashMap<String, Integer>();

    for(ReimbursementStatus rs : reimbursementStatusList)
      reimbursementStatuses.put(rs.getReimbursementStatus(), rs.getReimbursementStatusId());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();
    DisapprovalInformation di = mapper.readValue(json, DisapprovalInformation.class);
    int newStatus = 0;

    System.out.println(json);

    if(di.getDisapproverEmployeeType().equals("Supervisor"))
      newStatus = reimbursementStatuses.get("Disapproved by Supervisor");
    else if(di.getDisapproverEmployeeType().equals("Department Head"))
      newStatus = reimbursementStatuses.get("Disapproved by Department Head");
    else if(di.getDisapproverEmployeeType().equals("Benefits Coordinator"))
      newStatus = reimbursementStatuses.get("Disapproved by Benefits Coordinator");
    
    System.out.println("New Status: " + newStatus);
    rDao.checkConnection();
    System.out.println("New Status: " + newStatus);
    rDao.updateAttribute(di.getReimbursementId(), "AssignTo", 0);
    rDao.updateAttribute(di.getReimbursementId(), "ReimbursementStatusID", newStatus);
  }
}

class DisapprovalInformation {
  private int reimbursementId;
  private String disapproverEmployeeType;

  /**
   * @return the disapproverEmployeeType
   */
  public String getDisapproverEmployeeType() {
    return disapproverEmployeeType;
  }

  /**
   * @return the reimbursementId
   */
  public int getReimbursementId() {
    return reimbursementId;
  }

  /**
   * @param disapproverEmployeeType the disapproverEmployeeType to set
   */
  public void setDisapproverEmployeeType(String disapproverEmployeeType) {
    this.disapproverEmployeeType = disapproverEmployeeType;
  }

  /**
   * @param reimbursementId the reimbursementId to set
   */
  public void setReimbursementId(int reimbursementId) {
    this.reimbursementId = reimbursementId;
  }
}

