package com.gl.ceir.panel.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.TicketDto;
import com.gl.ceir.panel.dto.TicketNoteDto;
import com.gl.ceir.panel.dto.TicketRateDto;
import com.gl.ceir.panel.dto.response.ApiStatusDto;
import com.gl.ceir.panel.dto.response.PaginationDto;
import com.gl.ceir.panel.dto.response.TicketResponseDto;
import com.gl.ceir.panel.repository.remote.DownloaderRepositoryRemote;
import com.gl.ceir.panel.service.TicketService;

import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/ticket")
@Tag(name = "Api to manage support tickets")
public class TicketController {
	private final TicketService ticketService;
	private final DownloaderRepositoryRemote downloaderRepositoryRemote;  

	@Operation(summary = "Create new ticket", description = "Return saved ticket information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@ModelAttribute TicketDto ticketDto) {
		return new ResponseEntity<>(ticketService.save(ticketDto), HttpStatus.OK);
	}
	
	@Operation(summary = "Add comment on a ticket", description = "Return saved ticket information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class)) }) })
	@PostMapping("save/note")
	public ResponseEntity<?> savenote(@ModelAttribute TicketNoteDto ticketDto) {
		log.info("ticket: {}", ticketDto);
		return new ResponseEntity<>(ticketService.saveNote(ticketDto), HttpStatus.OK);
	}
	@Operation(summary = "Add rate on a ticket", description = "Return saved ticket information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class)) }) })
	@PostMapping("save/rate")
	public ResponseEntity<?> saverate(@RequestBody TicketRateDto ticketDto) {
		log.info("ticket: {}", ticketDto);
		return new ResponseEntity<>(ticketService.saveRate(ticketDto),HttpStatus.OK);
	}

	@Operation(summary = "Return ticket list", description = "Return ticket pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationDto.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> permissions(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(ticketService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Return ticket list by msisdn", description = "Return ticket pagination list by msisdn", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PaginationDto.class)) }) })
	@PostMapping("{msisdn}/pagination")
	public ResponseEntity<?> permissions(@PathVariable String msisdn, @RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(ticketService.paginationByMsisdn(ulrd,msisdn), HttpStatus.OK);
	}

	@Operation(summary = "Return ticket information by ticket id", description = "Ticket information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class)) }) })
	@GetMapping("/{ticketId}")
	public ResponseEntity<?> getById(@PathVariable String ticketId) {
		log.info("ticket id: {}", ticketId);
		return new ResponseEntity<>(ticketService.getById(ticketId), HttpStatus.OK);
	}
	@Operation(summary = "Resolve ticket by ticket id", description = "Ticket information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class)) }) })
	@GetMapping("resolve/{ticketId}")
	public ResponseEntity<?> resolve(@PathVariable String ticketId) {
		log.info("ticket id: {}", ticketId);
		return new ResponseEntity<>(ticketService.resolve(ticketId), HttpStatus.OK);
	}
	
	@Operation(hidden = false)
	@GetMapping("forgot-ticket/{msisdn}")
	public ResponseEntity<?> forgot(@PathVariable String msisdn) {
		log.info("msisdn: {}", msisdn);
		return new ResponseEntity<>(ticketService.forgotTicket(msisdn), HttpStatus.OK);
	}
	
	@Operation(summary = "Verify otp by msisdn", description = "Verify otp by msisdn")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ApiStatusDto.class)) }) })
	@GetMapping("verify-otp/{msisdn}/{otp}")
	public ResponseEntity<?> verifyopt(@PathVariable String msisdn, @PathVariable String otp) {
		log.info("msisdn: {},otp:{}", msisdn, otp);
		return new ResponseEntity<>(ticketService.verifyOtp(msisdn, otp), HttpStatus.OK);
	}
	
	@Operation(summary = "Send otp on a msisdn", description = "Send Otp on a msisdn")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ApiStatusDto.class)) }) })
	@GetMapping("send-otp/{msisdn}")
	public ResponseEntity<?> sendotp(@PathVariable String msisdn) {
		log.info("msisdn: {}", msisdn);
		return new ResponseEntity<>(ticketService.sendotp(msisdn), HttpStatus.OK);
	}
	@Operation(summary = "Support dashboard", description = "Dashboard information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = JsonNode.class)) }) })
	@GetMapping("dashboard")
	public ResponseEntity<?> dashboard() {
		return new ResponseEntity<>(ticketService.viewDashboard(), HttpStatus.OK);
	}
	@Operation(summary = "Support dashboard", description = "Dashboard information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = JsonNode.class)) }) })
	@GetMapping("category")
	public ResponseEntity<?> categories() {
		return new ResponseEntity<>(ticketService.categories(), HttpStatus.OK);
	}
	@Operation(hidden = true)
	@GetMapping("document/list")
	public ResponseEntity<?> documentlist() {
		return new ResponseEntity<>(ticketService.documentlist(), HttpStatus.OK);
	}
	
	@Operation(hidden = true)
	@GetMapping("document/attachment/{id}")
	public Response attachment(@PathVariable String id) throws IOException {
		Response response = downloaderRepositoryRemote.attachment(id);
		log.info("response: ", response.body().asInputStream());
		return response;
	}
}
