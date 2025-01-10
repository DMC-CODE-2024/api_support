package com.gl.ceir.panel.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.FeatureDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.entity.app.FeatureEntity;
import com.gl.ceir.panel.entity.app.ModuleTagEntity;
import com.gl.ceir.panel.service.FeatureService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@RestController
@Log4j2
@RequestMapping("feature")
@AllArgsConstructor
@Tag(name = "Api to manage feature")
public class FeatureController {
	private final FeatureService featureService;
	
	@Operation(summary = "Save feature", description = "Return saved feature information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@ModelAttribute FeatureDto featureDto, HttpServletRequest request) {
		log.info("feature: {}", featureDto);
		return new ResponseEntity<>(featureService.save(featureDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Return feature information by feature id", description = "Feature information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureEntity.class)) }) })
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		return new ResponseEntity<>(featureService.getById(id), HttpStatus.OK);
	}
	@Operation(summary = "Update feature by featureId", description = "Return updated feature information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureEntity.class)) }) })
	@RequestMapping(path = "/update/{id}", method = RequestMethod.PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> update(@Valid @ModelAttribute FeatureDto featureDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Feature:{}", featureDto);
		return new ResponseEntity<>(featureService.update(featureDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Delete feature by feature id", description = "Return deleted feature information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureEntity.class)) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return new ResponseEntity<>(featureService.deleteById(id), HttpStatus.OK);
	}
	@Operation(summary = "Return feature list", description = "Return feature pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(featureService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Return list of active feature by roleId", description = "Active list of features")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FeatureEntity.class))) }) })
	@GetMapping("list/{roleId}")
	public ResponseEntity<?> findFeaturesByRoleId(@PathVariable Long roleId) {
		return new ResponseEntity<>(featureService.getAllFeaturesByRoleId(roleId), HttpStatus.OK);
	}
	@Operation(summary = "Return list of active features", description = "Active list of features")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FeatureEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findFeatures() {
		return new ResponseEntity<>(featureService.getFeatures(), HttpStatus.OK);
	}
	@Operation(hidden = true)
	@GetMapping("all/list")
	public ResponseEntity<?> findAllFeatures() {
		return new ResponseEntity<>(featureService.getAllFeatures(), HttpStatus.OK);
	}
	@Operation(summary = "Return menu for current logged in user", description = "Menu for current logged in user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FeatureEntity.class))) }) })
	@GetMapping("menu")
	public ResponseEntity<?> menu() {
		return new ResponseEntity<>(featureService.menu(), HttpStatus.OK);
	}
	@Operation(summary = "Delete features by list of featureId", description = "Return api status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(featureService.delete(ids), HttpStatus.OK);
	}
	@Operation(summary = "Active features by list of featureId", description = "Return api status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(featureService.active(ids), HttpStatus.OK);
	}
}
