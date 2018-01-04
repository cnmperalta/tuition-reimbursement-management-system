package com.revature.projects.trms.beans;

import java.time.ZonedDateTime;

public class InformationRequest {
  private int informationRequestId;
  private int requesterId;
  private int requesteeId;
  private int reimbursementId;
  private String requesterMessage;
  private String requesteeResponse;
  private ZonedDateTime requestDate;
  private ZonedDateTime responseDate;

  public InformationRequest() {}

  public InformationRequest(int requesterId, int requesteeId, int reimbursementId, String requesterMessage, String requesteeResponse, ZonedDateTime requestDate, ZonedDateTime responseTime) {
    this.requesterId = requesterId;
    this.requesteeId = requesteeId;
    this.reimbursementId = reimbursementId;
    this.requesterMessage = requesterMessage;
    this.requesteeResponse = requesteeResponse;
    this.requestDate = requestDate;
    this.responseDate = responseDate;
  }

  public InformationRequest(int informationRequestId, int requesterId, int requesteeId, int reimbursementId, String requesterMessage, String requesteeResponse, ZonedDateTime requestDate, ZonedDateTime responseTime) {
    this.informationRequestId = informationRequestId;
    this.requesterId = requesterId;
    this.requesteeId = requesteeId;
    this.reimbursementId = reimbursementId;
    this.requesterMessage = requesterMessage;
    this.requesteeResponse = requesteeResponse;
    this.requestDate = requestDate;
    this.responseDate = responseDate;
  }

  /**
   * @return the informationRequestId
   */
  public int getInformationRequestId() {
    return informationRequestId;
  }

  /**
   * @return the reimbursementId
   */
  public int getReimbursementId() {
    return reimbursementId;
  }

  /**
   * @return the requestDate
   */
  public ZonedDateTime getRequestDate() {
    return requestDate;
  }

  /**
   * @return the requesteeId
   */
  public int getRequesteeId() {
    return requesteeId;
  }

  /**
   * @return the requesteeResponse
   */
  public String getRequesteeResponse() {
    return requesteeResponse;
  }

  /**
   * @return the requesterId
   */
  public int getRequesterId() {
    return requesterId;
  }

  /**
   * @return the requesterMessage
   */
  public String getRequesterMessage() {
    return requesterMessage;
  }

  /**
   * @return the responseDate
   */
  public ZonedDateTime getResponseDate() {
    return responseDate;
  }

  /**
   * @param informationRequestId the informationRequestId to set
   */
  public void setInformationRequestId(int informationRequestId) {
    this.informationRequestId = informationRequestId;
  }

  /**
   * @param reimbursementId the reimbursementId to set
   */
  public void setReimbursementId(int reimbursementId) {
    this.reimbursementId = reimbursementId;
  }

  /**
   * @param requestDate the requestDate to set
   */
  public void setRequestDate(ZonedDateTime requestDate) {
    this.requestDate = requestDate;
  }

  /**
   * @param requesteeId the requesteeId to set
   */
  public void setRequesteeId(int requesteeId) {
    this.requesteeId = requesteeId;
  }

  /**
   * @param requesteeResponse the requesteeResponse to set
   */
  public void setRequesteeResponse(String requesteeResponse) {
    this.requesteeResponse = requesteeResponse;
  }

  /**
   * @param requesterId the requesterId to set
   */
  public void setRequesterId(int requesterId) {
    this.requesterId = requesterId;
  }

  /**
   * @param requesterMessage the requesterMessage to set
   */
  public void setRequesterMessage(String requesterMessage) {
    this.requesterMessage = requesterMessage;
  }

  /**
   * @param responseDate the responseDate to set
   */
  public void setResponseDate(ZonedDateTime responseDate) {
    this.responseDate = responseDate;
  }

  @Override
  public String toString() {
    return "InformationRequest [ " + informationRequestId + " ]"; 
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }
}