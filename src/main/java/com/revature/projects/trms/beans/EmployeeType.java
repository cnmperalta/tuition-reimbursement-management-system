package com.revature.projects.trms.beans;

public class EmployeeType {
  private int employeeTypeId;
  private String employeeType;

  public EmployeeType() {}

  public EmployeeType(int employeeTypeId, String employeeType) {
    this.employeeTypeId = employeeTypeId;
    this.employeeType = employeeType;
  }

  /**
   * @return the employeeType
   */
  public String getEmployeeType() {
    return employeeType;
  }

  /**
   * @return the employeeTypeId
   */
  public int getEmployeeTypeId() {
    return employeeTypeId;
  }

  /**
   * @param employeeType the employeeType to set
   */
  public void setEmployeeType(String employeeType) {
    this.employeeType = employeeType;
  }

  /**
   * @param employeeTypeId the employeeTypeId to set
   */
  public void setEmployeeTypeId(int employeeTypeId) {
    this.employeeTypeId = employeeTypeId;
  }

  @Override
  public String toString() {
    return "EmployeeType [ " + employeeTypeId + ", " + employeeType + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}