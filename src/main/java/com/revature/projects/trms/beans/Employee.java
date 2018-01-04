package com.revature.projects.trms.beans;

import java.time.ZonedDateTime;

public class Employee {
  private int employeeId;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String password;
  private String salt;
  private int directSupervisorId;
  private int departmentId;
  private ZonedDateTime lastLogin;
  private int employeeTypeId;

  public Employee() {}

  public Employee(String firstName, String lastName, String emailAddress, String password, String salt, int directSupervisorId, int departmentId, ZonedDateTime lastLogin, int employeeTypeId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.password = password;
    this.salt = salt;
    this.directSupervisorId = directSupervisorId;
    this.lastLogin = lastLogin;
    this.departmentId = departmentId;
    this.employeeTypeId = employeeTypeId;
  }

  public Employee(int employeeId, String firstName, String lastName, String emailAddress, String password, String salt, int directSupervisorId, int departmentId, ZonedDateTime lastLogin, int employeeTypeId) {
    this.employeeId = employeeId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.password = password;
    this.salt = salt;
    this.directSupervisorId = directSupervisorId;
    this.departmentId = departmentId;
    this.lastLogin = lastLogin;
    this.employeeTypeId = employeeTypeId;
  }

  /**
   * @return the departmentId
   */
  public int getDepartmentId() {
    return departmentId;
  }

  /**
   * @return the directSupervisorId
   */
  public int getDirectSupervisorId() {
    return directSupervisorId;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return the employeeId
   */
  public int getEmployeeId() {
    return employeeId;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @return the salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * @return the employeeTypeId
   */
  public int getEmployeeTypeId() {
    return employeeTypeId;
  }

  /**
   * @return the lastLogin
   */
  public ZonedDateTime getLastLogin() {
    return lastLogin;
  }

  /**
   * @param departmentId the departmentId to set
   */
  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  /**
   * @param directSupervisorId the directSupervisorId to set
   */
  public void setDirectSupervisorId(int directSupervisorId) {
    this.directSupervisorId = directSupervisorId;
  }

  /**
   * @param emailAddress the emailAddress to set
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @param employeeId the employeeId to set
   */
  public void setEmployeeId(int employeeId) {
    this.employeeId = employeeId;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @param salt the salt to set
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  /**
   * @param employeeTypeId the employeeTypeId to set
   */
  public void setEmployeeTypeId(int employeeTypeId) {
    this.employeeTypeId = employeeTypeId;
  }

  /**
   * @param lastLogin the lastLogin to set
   */
  public void setLastLogin(ZonedDateTime lastLogin) {
    this.lastLogin = lastLogin;
  }

  @Override
  public String toString() {
    return "Employee [" + employeeId + ", " + firstName + " " + lastName + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Employee) {
      Employee other = (Employee) obj;

      return (this.emailAddress.equals(other.getEmailAddress()));
    } else
      return false;
  }
}