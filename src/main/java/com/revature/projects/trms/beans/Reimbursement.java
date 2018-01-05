package com.revature.projects.trms.beans;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class Reimbursement {
  private int reimbursementId;
  private int requesterId;
  private int assignTo;
  private String description;
  private String workRelatedJustification;
  private BigDecimal amountRequested;
  private String additionalInformation;
  private String reimbursementStatus;
  private ZonedDateTime dateSubmitted;
  private ZonedDateTime dateCompleted;
  private Event event;
  private ReimbursementResponse response;
  private List<InformationRequest> informationRequests;

  public Reimbursement() {}

  public Reimbursement(int requesterId, int assignTo, String description, String workRelatedJustification, BigDecimal amountRequested, String additionalInformation, String reimbursementStatus, ZonedDateTime dateSubmitted, Event event) {
    this.requesterId = requesterId;
    this.assignTo = assignTo;
    this.description = description;
    this.workRelatedJustification = workRelatedJustification;
    this.amountRequested = amountRequested;
    this.additionalInformation = additionalInformation;
    this.reimbursementStatus = reimbursementStatus;
    this.dateSubmitted = dateSubmitted;
    this.dateCompleted = null;
    this.event = event;
    this.response = null;
    this.informationRequests = null;
  }

  public Reimbursement(int reimbursementId, int requesterId, int assignTo, String description, String workRelatedJustification, BigDecimal amountRequested, String additionalInformation, String reimbursementStatus, ZonedDateTime dateSubmitted, ZonedDateTime dateCompleted, Event event, ReimbursementResponse response, List<InformationRequest> informationRequests) {
    this.reimbursementId = reimbursementId;
    this.requesterId = requesterId;
    this.assignTo = assignTo;
    this.description = description;
    this.workRelatedJustification = workRelatedJustification;
    this.amountRequested = amountRequested;
    this.additionalInformation = additionalInformation;
    this.reimbursementStatus = reimbursementStatus;
    this.dateSubmitted = dateSubmitted;
    this.dateCompleted = dateCompleted;
    this.event = event;
    this.response = response;
    this.informationRequests = informationRequests;
  }

  /**
   * @return the assignTo
   */
  public int getAssignTo() {
    return assignTo;
  }

  /**
   * @return the additionalInformation
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }

  /**
   * @return the informationRequests
   */
  public List<InformationRequest> getInformationRequests() {
    return informationRequests;
  }

  /**
   * @return the amountRequested
   */
  public BigDecimal getAmountRequested() {
    return amountRequested;
  }
  
  /**
   * @return the dateCompleted
   */
  public ZonedDateTime getDateCompleted() {
    return dateCompleted;
  }

  /**
   * @return the dateSubmitted
   */
  public ZonedDateTime getDateSubmitted() {
    return dateSubmitted;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the event
   */
  public Event getEvent() {
    return event;
  }

  /**
   * @return the reimbursementId
   */
  public int getReimbursementId() {
    return reimbursementId;
  }

  /**
   * @return the reimbursementStatus
   */
  public String getReimbursementStatus() {
    return reimbursementStatus;
  }

  /**
   * @return the requesterId
   */
  public int getRequesterId() {
    return requesterId;
  }

  /**
   * @return the response
   */
  public ReimbursementResponse getResponse() {
    return response;
  }

  /**
   * @return the workRelatedJustification
   */
  public String getWorkRelatedJustification() {
    return workRelatedJustification;
  }

  /**
   * @param assignTo the assignTo to set
   */
  public void setAssignTo(int assignTo) {
    this.assignTo = assignTo;
  }

  /**
   * @param additionalInformation the additionalInformation to set
   */
  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  /**
   * @param informationRequests the informationRequests to set
   */
  public void setInformationRequests(List<InformationRequest> informationRequests) {
    this.informationRequests = informationRequests;
  }

  /**
   * @param amountRequested the amountRequested to set
   */
  public void setAmountRequested(BigDecimal amountRequested) {
    this.amountRequested = amountRequested;
  }

  /**
   * @param dateCompleted the dateCompleted to set
   */
  public void setDateCompleted(ZonedDateTime dateCompleted) {
    this.dateCompleted = dateCompleted;
  }

  /**
   * @param dateSubmitted the dateSubmitted to set
   */
  public void setDateSubmitted(ZonedDateTime dateSubmitted) {
    this.dateSubmitted = dateSubmitted;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param event the event to set
   */
  public void setEvent(Event event) {
    this.event = event;
  }

  /**
   * @param reimbursementId the reimbursementId to set
   */
  public void setReimbursementId(int reimbursementId) {
    this.reimbursementId = reimbursementId;
  }

  /**
   * @param reimbursementStatus the reimbursementStatus to set
   */
  public void setReimbursementStatus(String reimbursementStatus) {
    this.reimbursementStatus = reimbursementStatus;
  }

  /**
   * @param requesterId the requesterId to set
   */
  public void setRequesterId(int requesterId) {
    this.requesterId = requesterId;
  }

  /**
   * @param response the response to set
   */
  public void setResponse(ReimbursementResponse response) {
    this.response = response;
  }

  /**
   * @param workRelatedJustification the workRelatedJustification to set
   */
  public void setWorkRelatedJustification(String workRelatedJustification) {
    this.workRelatedJustification = workRelatedJustification;
  }

  @Override
  public String toString() {
    return "Reimbursement [ " + reimbursementId + " by Employeer # " + requesterId + " on " + dateSubmitted + " for " + event.getEventName() + " ]";
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Reimbursement) {
      Reimbursement other = (Reimbursement) obj;
      return (reimbursementId == other.getRequesterId());
    } else
      return false;
  }
}