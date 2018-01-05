package com.revature.projects.trms.beans;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeTest {
  private Employee e;
  @Before
  public void init() {
    e = new  Employee(
      1,
      "Kei",
      "Peralta",
      "keiperalta@sample.com",
      "wijakldsbfka",
      "asdfadnvkajnawefasdfas",
      2,
      5,
      ZonedDateTime.now(),
      3
    );
  }

  @Test
  public void setFirstNameTest() {
    e.setFirstName("Juno");
    assertEquals("Juno", e.getFirstName());
  }

  @Test
  public void setLastNameTest() {
    e.setLastName("McGuff");
    assertEquals("McGuff", e.getLastName());
  }

  @Test
  public void setEmailAddressTest() {
    e.setEmailAddress("cheesetomymacaroni@sample.com");
    assertEquals("cheesetomymacaroni@sample.com", e.getEmailAddress());
  }

  @Test
  public void setPasswordTest() {
    e.setPassword("aawaovnadlfanasg");
    assertEquals("aawaovnadlfanasg", e.getPassword());
  }

  @Test
  public void setSaltTest() {
    e.setSalt("akjhaifawefanskdf");
    assertEquals("akjhaifawefanskdf", e.getSalt());
  }

  @Test
  public void setDirectSupervisorIDTest() {
    e.setDirectSupervisorId(11);
    assertEquals(11, e.getDirectSupervisorId());
  }

  @Test
  public void setDepartmentIDTest() {
    e.setDepartmentId(55);
    assertEquals(55, e.getDepartmentId());
  }

  @Test
  public void setLastLoginTest() {
    ZonedDateTime now = ZonedDateTime.now();
    e.setLastLogin(now);
    assertEquals(now, e.getLastLogin());
  }

  @Test
  public void setEmployeeTypeIDTest() {
    e.setEmployeeTypeId(5);
    assertEquals(5, e.getEmployeeTypeId());
  }
}