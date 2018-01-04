package com.revature.projects.trms.beans;

public class GradingFormat {
  private int gradingFormatId;
  private String gradingFormat;

  public GradingFormat() {}

  public GradingFormat(int gradingFormatId, String gradingFormat) {
    this.gradingFormatId = gradingFormatId;
    this.gradingFormat = gradingFormat;
  }

  /**
   * @return the gradingFormat
   */
  public String getGradingFormat() {
    return gradingFormat;
  }

  /**
   * @return the gradingFormatId
   */
  public int getGradingFormatId() {
    return gradingFormatId;
  }

  /**
   * @param gradingFormat the gradingFormat to set
   */
  public void setGradingFormat(String gradingFormat) {
    this.gradingFormat = gradingFormat;
  }
  
  /**
   * @param gradingFormatId the gradingFormatId to set
   */
  public void setGradingFormatId(int gradingFormatId) {
    this.gradingFormatId = gradingFormatId;
  }

  @Override
  public String toString() {
    return "GradingFormat [ " + gradingFormatId + ", " + gradingFormat + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}