package com.gl.ceir.panel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.request.OtpDto;
import com.gl.ceir.panel.entity.app.LinkEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("otp")
@Tag(name = "Otp api to send/verify otp")
public class OtpController {
	@Operation(summary = "Send otp to a mobile number", description = "Return otp status", hidden = true)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }) })
	@GetMapping("send/{mobileNumber}")
	public ResponseEntity<?> save(@PathVariable String mobileNumber) {
		log.info("Send otp to mobile:{}", mobileNumber);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Verify otp to a mobile number", description = "Return otp verify status", hidden = true)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }) })
	@PostMapping("verify")
	public ResponseEntity<?> verify(@RequestBody OtpDto otpDto) {
		log.info("Verify otp:{}", otpDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
