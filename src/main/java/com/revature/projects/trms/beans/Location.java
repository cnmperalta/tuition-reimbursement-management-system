package com.revature.projects.trms.beans;

public class Location {
  private int locationId;
  private String streetAddress1;
  private String streetAddress2;
  private String city;
  private String state;
  private String zipCode;
  private String country;

  public Location() {}

  public Location(String streetAddress1, String streetAddress2, String city, String state, String zipCode, String country) {
    this.streetAddress1 = streetAddress1;
    this.streetAddress2 = streetAddress2;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.country = country;  
  }

  public Location(int locationId, String streetAddress1, String streetAddress2, String city, String state, String zipCode, String country) {
    this.locationId = locationId;
    this.streetAddress1 = streetAddress1;
    this.streetAddress2 = streetAddress2;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.country = country;  
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @return the locationId
   */
  public int getLocationId() {
    return locationId;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the streetAddress1
   */
  public String getStreetAddress1() {
    return streetAddress1;
  }

  /**
   * @return the streetAddress2
   */
  public String getStreetAddress2() {
    return streetAddress2;
  }

  /**
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @param locationId the locationId to set
   */
  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @param streetAddress1 the streetAddress1 to set
   */
  public void setStreetAddress1(String streetAddress1) {
    this.streetAddress1 = streetAddress1;
  }

  /**
   * @param streetAddress2 the streetAddress2 to set
   */
  public void setStreetAddress2(String streetAddress2) {
    this.streetAddress2 = streetAddress2;
  }

  /**
   * @param zipCode the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public String toString() {
    return "Location [ " + locationId + ", " + streetAddress1 + ", " + streetAddress2 + ", " + city + ", " + state + " " + zipCode + " " + country + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    if(this.toString().equals(obj.toString()))
      return true;
    else
      return false;
  }
}