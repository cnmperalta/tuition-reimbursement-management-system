package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
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
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(rDao);

  }
}

