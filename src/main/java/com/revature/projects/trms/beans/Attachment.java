package com.revature.projects.trms.beans;

public class Attachment {
  private int attachmentId;
  private int reimbursementId;
  private String attachmentLocation;
  private String attachmentDescription;

  public Attachment() {}
  
  public Attachment(int reimbursementId, String attachmentLocation, String attachmentDescription) {
    this.reimbursementId = reimbursementId;
    this.attachmentLocation = attachmentLocation;
    this.attachmentDescription = attachmentDescription;
  }

  public Attachment(int attachmentId, int reimbursementId, String attachmentLocation, String attachmentDescription) {
    this.attachmentId = attachmentId;
    this.reimbursementId = reimbursementId;
    this.attachmentLocation = attachmentLocation;
    this.attachmentDescription = attachmentDescription;
  }

  /**
   * @return the attachmentDescription
   */
  public String getAttachmentDescription() {
    return attachmentDescription;
  }
  
  /**
   * @return the attachmentId
   */
  public int getAttachmentId() {
    return attachmentId;
  }

  /**
   * @return the attachmentLocation
   */
  public String getAttachmentLocation() {
    return attachmentLocation;
  }

  /**
   * @return the reimbursementId
   */
  public int getReimbursementId() {
    return reimbursementId;
  }

  /**
   * @param attachmentDescription the attachmentDescription to set
   */
  public void setAttachmentDescription(String attachmentDescription) {
    this.attachmentDescription = attachmentDescription;
  }

  /**
   * @param attachmentId the attachmentId to set
   */
  public void setAttachmentId(int attachmentId) {
    this.attachmentId = attachmentId;
  }

  /**
   * @param attachmentLocation the attachmentLocation to set
   */
  public void setAttachmentLocation(String attachmentLocation) {
    this.attachmentLocation = attachmentLocation;
  }

  /**
   * @param reimbursementId the reimbursementId to set
   */
  public void setReimbursementId(int reimbursementId) {
    this.reimbursementId = reimbursementId;
  }

  @Override
  public String toString() {
    return "Attachment [ " + attachmentId + " for reimbursement # " + reimbursementId + " at " + attachmentLocation + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Attachment) {
      Attachment other = (Attachment) obj;

      return (attachmentId == other.getAttachmentId());
    } else
      return false;
  }
}