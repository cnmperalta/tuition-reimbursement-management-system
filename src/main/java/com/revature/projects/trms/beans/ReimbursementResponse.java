package com.revature.projects.trms.beans;

import java.math.BigDecimal;

public class ReimbursementResponse {
  private int reimbursementResponseId;
  private int responderId;
  private BigDecimal reimbursementAmountAwarded;
  private BigDecimal reimbursementAmountExceeded;
  private String reimbursementExceedReason;
  private String disapprovalReason;

  public ReimbursementResponse() {}

  public ReimbursementResponse(int responderId, BigDecimal reimbursementAmountAwarded, BigDecimal reimbursementAmountExceeded, String reimbursementExceedReason, String disapprovalReason) {
    this.responderId = responderId;
    this.reimbursementAmountAwarded = reimbursementAmountAwarded;
    this.reimbursementAmountExceeded = reimbursementAmountExceeded;
    this.reimbursementExceedReason = reimbursementExceedReason;
    this.disapprovalReason = disapprovalReason;
  }

  public ReimbursementResponse(int reimbursementResponseId, int responderId, BigDecimal reimbursementAmountAwarded, BigDecimal reimbursementAmountExceeded, String reimbursementExceedReason, String disapprovalReason) {
    this.reimbursementResponseId = reimbursementResponseId;
    this.responderId = responderId;
    this.reimbursementAmountAwarded = reimbursementAmountAwarded;
    this.reimbursementAmountExceeded = reimbursementAmountExceeded;
    this.reimbursementExceedReason = reimbursementExceedReason;
    this.disapprovalReason = disapprovalReason;
  }

  /**
   * @return the disapprovalReason
   */
  public String getDisapprovalReason() {
    return disapprovalReason;
  }

  /**
   * @return the reimbursementAmountAwarded
   */
  public BigDecimal getReimbursementAmountAwarded() {
    return reimbursementAmountAwarded;
  }

  /**
   * @return the reimbursementAmountExceeded
   */
  public BigDecimal getReimbursementAmountExceeded() {
    return reimbursementAmountExceeded;
  }

  /**
   * @return the reimbursementExceedReason
   */
  public String getReimbursementExceedReason() {
    return reimbursementExceedReason;
  }

  /**
   * @return the reimbursementResponseId
   */
  public int getReimbursementResponseId() {
    return reimbursementResponseId;
  }

  /**
   * @return the responderId
   */
  public int getResponderId() {
    return responderId;
  }

  /**
   * @param disapprovalReason the disapprovalReason to set
   */
  public void setDisapprovalReason(String disapprovalReason) {
    this.disapprovalReason = disapprovalReason;
  }

  /**
   * @param reimbursementAmountAwarded the reimbursementAmountAwarded to set
   */
  public void setReimbursementAmountAwarded(BigDecimal reimbursementAmountAwarded) {
    this.reimbursementAmountAwarded = reimbursementAmountAwarded;
  }

  /**
   * @param reimbursementAmountExceeded the reimbursementAmountExceeded to set
   */
  public void setReimbursementAmountExceeded(BigDecimal reimbursementAmountExceeded) {
    this.reimbursementAmountExceeded = reimbursementAmountExceeded;
  }

  /**
   * @param reimbursementExceedReason the reimbursementExceedReason to set
   */
  public void setReimbursementExceedReason(String reimbursementExceedReason) {
    this.reimbursementExceedReason = reimbursementExceedReason;
  }

  /**
   * @param reimbursementResponseId the reimbursementResponseId to set
   */
  public void setReimbursementResponseId(int reimbursementResponseId) {
    this.reimbursementResponseId = reimbursementResponseId;
  }

  /**
   * @param responderId the responderId to set
   */
  public void setResponderId(int responderId) {
    this.responderId = responderId;
  }

  @Override
  public String toString() {
    return "Reimbursement Response [ " + reimbursementResponseId + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof ReimbursementResponse) {
      ReimbursementResponse other = (ReimbursementResponse) obj;

      return (this.reimbursementResponseId == other.getReimbursementResponseId());
    } else
      return false;
  }
}