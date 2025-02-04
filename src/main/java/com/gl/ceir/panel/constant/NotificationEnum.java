package com.gl.ceir.panel.constant;

public enum NotificationEnum {
	ticketCreate("eirs.ticket.create.email", "eirs.ticket.create.sms"),
	userCreate("eirs.user.create.email","eirs.user.create.sms"),
	resetPassword("eirs.reset.password.email","eirs.reset.password.sms"),
	changeMobileOrEmail("eirs.otp.email.change.message","eirs.otp.mobile.change.message"),
	forgotTicket("eirs.ticket.otp.message","eirs.ticket.otp.message");

	public String email;
	public String sms;

	NotificationEnum(String email, String sms) {
		this.email = email;
		this.sms = sms;
	}

	public String email() {
		return this.email;
	}

	public String sms() {
		return this.sms;
	}
}
