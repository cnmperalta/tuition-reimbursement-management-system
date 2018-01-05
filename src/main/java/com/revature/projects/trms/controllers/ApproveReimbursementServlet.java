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

@WebServlet("/approve-reimbursement")
public class ApproveReimbursementServlet extends HttpServlet {
  private static final long serialVersionUID = 10L;
  private ReimbursementDataAccessObject rDao;
  private EmployeeDataAccessObject eDao;
  private DepartmentDataAccessObject dDao;
  private HashMap<String, Integer> reimbursementStatuses;

  @Override
  public void init() throws ServletException {
    ReimbursementStatusDataAccessObject rsDao = new ReimbursementStatusDataAccessObject();
    List<ReimbursementStatus> reimbursementStatusList = rsDao.getAll();
    rDao = new ReimbursementDataAccessObject();
    eDao = new EmployeeDataAccessObject();
    dDao = new DepartmentDataAccessObject();
    reimbursementStatuses = new HashMap<String, Integer>();

    for(ReimbursementStatus rs : reimbursementStatusList)
      reimbursementStatuses.put(rs.getReimbursementStatus(), rs.getReimbursementStatusId());

  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String json = req.getReader().lines().collect(Collectors.joining());
    ObjectMapper mapper = new ObjectMapper();
    ApprovalInformation ai = mapper.readValue(json, ApprovalInformation.class);
    int newAssignTo = 0;
    int newStatus = 0;

    rDao.checkConnection();
    if(ai.getApproverEmployeeType().equalsIgnoreCase("Supervisor")) {
      newAssignTo = dDao.getById(eDao.getById(ai.getRequesterId()).getDepartmentId()).getDepartmentHeadId();
      newStatus = reimbursementStatuses.get("Approved by Supervisor");
    }
    else if (ai.getApproverEmployeeType().equalsIgnoreCase("Department Head")) {
      newAssignTo = eDao.getBenefitCoordinator().getEmployeeId();
      newStatus = reimbursementStatuses.get("Approved by Department Head");
    }
    else if (ai.getApproverEmployeeType().equalsIgnoreCase("Benefits Coordinator")) {
      newAssignTo = 0;
      newStatus = reimbursementStatuses.get("Approved by Benefits Coordinator");
    }

    rDao.updateAttribute(ai.getReimbursementId(), "AssignTo", newAssignTo);
    rDao.updateAttribute(ai.getReimbursementId(), "ReimbursementStatusID", newStatus);
  }

}

class ApprovalInformation {
  private int reimbursementId;
  private int requesterId;
  private int approverId;
  private String approverEmployeeType;

  /**
   * @return the approverEmployeeType
   */
  public String getApproverEmployeeType() {
    return approverEmployeeType;
  }

  /**
   * @return the approverId
   */
  public int getApproverId() {
    return approverId;
  }

  /**
   * @return the reimbursementId
   */
  public int getReimbursementId() {
    return reimbursementId;
  }

  /**
   * @return the requesterId
   */
  public int getRequesterId() {
    return requesterId;
  }

  /**
   * @param approverEmployeeType the approverEmployeeType to set
   */
  public void setApproverEmployeeType(String approverEmployeeType) {
    this.approverEmployeeType = approverEmployeeType;
  }

  /**
   * @param approverId the approverId to set
   */
  public void setApproverId(int approverId) {
    this.approverId = approverId;
  }

  /**
   * @param reimbursementId the reimbursementId to set
   */
  public void setReimbursementId(int reimbursementId) {
    this.reimbursementId = reimbursementId;
  }

  /**
   * @param requesterId the requesterId to set
   */
  public void setRequesterId(int requesterId) {
    this.requesterId = requesterId;
  }
}