package com.cts.user.application.to;

public class UserBirthDate {
	DateRequest date;
	
	/**
	 * @return the date
	 */
	public DateRequest getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(DateRequest date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserBirthDate [date=").append(date).append("]");
		return builder.toString();
	}
}

