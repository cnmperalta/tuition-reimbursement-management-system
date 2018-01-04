package com.revature.projects.trms.beans;

public class Department {
  private int departmentId;
  private String departmentName;
  private int departmentHeadId;

  public Department() {}

  public Department(String departmentName, int departmentHeadId) {
    this.departmentName = departmentName;
    this.departmentHeadId = departmentHeadId;
  }
  
  public Department(int departmentId, String departmentName, int departmentHeadId) {
    this.departmentId = departmentId;
    this.departmentName = departmentName;
    this.departmentHeadId = departmentHeadId;
  }

  /**
   * @return the departmentHeadId
   */
  public int getDepartmentHeadId() {
    return departmentHeadId;
  }

  /**
   * @return the departmentId
   */
  public int getDepartmentId() {
    return departmentId;
  }

  /**
   * @return the departmentName
   */
  public String getDepartmentName() {
    return departmentName;
  }

  /**
   * @param departmentHeadId the departmentHeadId to set
   */
  public void setDepartmentHeadId(int departmentHeadId) {
    this.departmentHeadId = departmentHeadId;
  }

  /**
   * @param departmentId the departmentId to set
   */
  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  /**
   * @param departmentName the departmentName to set
   */
  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  @Override
  public String toString() {
    return "Department [ " + departmentId + ", " + departmentName + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}