package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.Reimbursement;
import com.revature.projects.trms.dao.ReimbursementDataAccessObject;

@WebServlet("/get-reimbursement")
public class GetReimbursementServlet extends HttpServlet {
  private static final long serialVersionUID = 9L;
  private ReimbursementDataAccessObject rDao;

  @Override
  public void init() throws ServletException {
    rDao = new ReimbursementDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String json = req.getReader().lines().collect(Collectors.joining());
    Reimbursement r = null;
    ObjectMapper mapper = new ObjectMapper();
    ReimbursementID rid = mapper.readValue(json, ReimbursementID.class);
    String jsonResponse = null;
    
    rDao.checkConnection();
    r = rDao.getById(rid.getReimbursementID());
    jsonResponse = mapper.writeValueAsString(r);
    resp.getWriter().write(jsonResponse);
    resp.setContentType("application/json");
  }
}

class ReimbursementID {
  private int reimbursementID;

  /**
   * @return the reimbursementID
   */
  public int getReimbursementID() {
    return reimbursementID;
  }

  /**
   * @param reimbursementID the reimbursementID to set
   */
  public void setReimbursementID(int reimbursementID) {
    this.reimbursementID = reimbursementID;
  }

}