package com.gl.ceir.panel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.constant.UrlTypeEnum;
import com.gl.ceir.panel.entity.app.LinkEntity;
import com.gl.ceir.panel.service.LinkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/link")
@Tag(name = "Api to manage links for frontend will use during feature creation")
public class LinkController {
	private final LinkService linkService;

	@Operation(summary = "Save link with information", description = "Return saved link information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = LinkEntity.class)) }) })
	@PostMapping("/save")
	public ResponseEntity<?> registerUser(@Valid @RequestBody LinkEntity linkEntity) {
		return ResponseEntity.ok(linkService.save(linkEntity));
	}

	@Operation(summary = "Return link list", description = "Return active link list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LinkEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<List<LinkEntity>> list() {
		return new ResponseEntity<>(linkService.links(), HttpStatus.OK);
	}
	@Operation(summary = "Return list of link by url type", description = "Return list list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LinkEntity.class))) }) })
	@GetMapping("list/{urlTypeEnum}")
	public ResponseEntity<?> list(@PathVariable UrlTypeEnum urlTypeEnum) {
		return new ResponseEntity<>(linkService.linksByUrlType(urlTypeEnum), HttpStatus.OK);
	}
	
	@Operation(summary = "Return link information by url", description = "Return link information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = LinkEntity.class)) }) })
	@GetMapping("findByUrl/{url}")
	public ResponseEntity<?> list(@PathVariable String url) {
		return new ResponseEntity<>(linkService.findByUrl(url), HttpStatus.OK);
	}
}
