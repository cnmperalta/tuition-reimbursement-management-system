package com.revature.projects.trms.dao;

import java.util.List;

public interface DataAccessObject<T> {
  public List<T> getAll();
  public T getById(int id);
  public List<T> getByAttribute(String attributeName, Object attributeValue);
  public void createNew(T e);
  public void deleteById(int id);
  public void deleteByAttribute(String attributeName, Object attributeValue);
  public void updateAttribute(int id, String attributeName, Object attributeValue);
  public int getCount();
  public int getCurrentID();
}