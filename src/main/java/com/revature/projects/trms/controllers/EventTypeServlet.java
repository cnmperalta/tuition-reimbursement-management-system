package com.revature.projects.trms.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.trms.beans.EventType;
import com.revature.projects.trms.dao.EventTypeDataAccessObject;

@WebServlet("/get-event-types")
public class EventTypeServlet extends HttpServlet {
  private static final long serialVersionUID = 4L;
  private EventTypeDataAccessObject etDao;

  @Override
  public void init() throws ServletException {
    etDao = new EventTypeDataAccessObject();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<EventType> eventTypes = etDao.getAll();
    ObjectMapper mapper = new ObjectMapper();
    String jsonResponse = mapper.writeValueAsString(eventTypes);

    etDao.checkConnection();

    resp.setContentType("application/json");
    resp.getWriter().write(jsonResponse);
  }
}