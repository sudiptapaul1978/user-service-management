package com.cts.user.application.to;

import java.util.Date;

public class UserPolicy {

	private String policyId;
	private float amountPaid;
	private Date policyEndDate;
	/**
	 * @return the policyId
	 */
	public String getPolicyId() {
		return policyId;
	}
	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	/**
	 * @return the amountPaid
	 */
	public float getAmountPaid() {
		return amountPaid;
	}
	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}
	/**
	 * @return the policyEndDate
	 */
	public Date getPolicyEndDate() {
		return policyEndDate;
	}
	/**
	 * @param policyEndDate the policyEndDate to set
	 */
	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPolicy [policyId=").append(policyId).append(", amountPaid=").append(amountPaid)
				.append(", policyEndDate=").append(policyEndDate).append("]");
		return builder.toString();
	}
	
}
