package com.hackathon.adminservice.exception;

public class LimitIsGreaterThanQuestionAvailabilityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LimitIsGreaterThanQuestionAvailabilityException(int availability) {
		super(Integer.toString(availability));
	}
}
