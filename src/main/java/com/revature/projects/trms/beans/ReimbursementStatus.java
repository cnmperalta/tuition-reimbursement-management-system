package com.revature.projects.trms.beans;

public class ReimbursementStatus {
  private int reimbursementStatusId;
  private String reimbursementStatus;

  public ReimbursementStatus() {}

  public ReimbursementStatus(int reimbursementStatusId, String reimbursementStatus) {
    this.reimbursementStatusId = reimbursementStatusId;
    this.reimbursementStatus = reimbursementStatus;
  }

  /**
   * @return the reimbursementStatus
   */
  public String getReimbursementStatus() {
    return reimbursementStatus;
  }

  /**
   * @return the reimbursementStatusId
   */
  public int getReimbursementStatusId() {
    return reimbursementStatusId;
  }

  /**
   * @param reimbursementStatus the reimbursementStatus to set
   */
  public void setReimbursementStatus(String reimbursementStatus) {
    this.reimbursementStatus = reimbursementStatus;
  }

  /**
   * @param reimbursementStatusId the reimbursementStatusId to set
   */
  public void setReimbursementStatusId(int reimbursementStatusId) {
    this.reimbursementStatusId = reimbursementStatusId;
  }

  @Override
  public String toString() {
    return "ReimbursementStatus [ " + reimbursementStatusId + ", " + reimbursementStatus + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}