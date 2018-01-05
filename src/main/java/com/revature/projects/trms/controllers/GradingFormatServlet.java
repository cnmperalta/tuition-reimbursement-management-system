package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.GradingFormat;
import com.revature.projects.trms.dao.GradingFormatDataAccessObject;

@WebServlet("/get-grading-formats")
public class GradingFormatServlet extends HttpServlet {
  private static final long serialVersionUID = 3L;
  private GradingFormatDataAccessObject gfDao;
  
  @Override
  public void init() throws ServletException {
    gfDao = new GradingFormatDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<GradingFormat> gradingFormats = gfDao.getAll();
    ObjectMapper mapper = new ObjectMapper();
    String jsonResponse = mapper.writeValueAsString(gradingFormats);
    
    resp.setContentType("application/json");
    resp.getWriter().write(jsonResponse);
  }
}