package com.gl.ceir.panel.controller;

import java.util.List;
import java.util.TreeSet;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.FeatureModuleDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.entity.app.FeatureEntity;
import com.gl.ceir.panel.entity.app.FeatureModuleEntity;
import com.gl.ceir.panel.entity.app.FeatureModuleId;
import com.gl.ceir.panel.service.FeatureModuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("featureModule")
@RequiredArgsConstructor
@Tag(name = "Api to manage feature's module")
public class FeatureModuleController {
	private final FeatureModuleService featureModuleService;
	
	@Operation(summary = "Save modules for a feature", description = "Return saved feature information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody FeatureModuleDto featureModuleDto, HttpServletRequest request) {
		return new ResponseEntity<>(featureModuleService.save(featureModuleDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Update modules for a feature", description = "Return updated feature information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FeatureEntity.class)) }) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody FeatureModuleDto featureModuleDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Role:{},update request:{}", id, featureModuleDto);
		return new ResponseEntity<>(featureModuleService.update(featureModuleDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Return feature module list", description = "Return feature module pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(featureModuleService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Return list of active feature module", description = "Active list of feature module")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TreeSet.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findRoles() {
		return new ResponseEntity<>(featureModuleService.getFeatureModules(), HttpStatus.OK);
	}
	@Operation(summary = "Delete modules from feature by feature id and module id", description = "Return deleted feature's module status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<FeatureModuleId> ids) {
		return new ResponseEntity<>(featureModuleService.delete(ids), HttpStatus.OK);
	} 
	@Operation(summary = "Activate modules from feature by feature id and module id", description = "Return deleted feature's module status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<FeatureModuleId> ids) {
		return new ResponseEntity<>(featureModuleService.active(ids), HttpStatus.OK);
	}
	@Operation(summary = "Return list of feature's modules", description = "Return active list of modules based on featureId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FeatureModuleEntity.class))) }) })
	@GetMapping("/{featureId}")
	public ResponseEntity<?> getById(@PathVariable Long featureId){
		return new ResponseEntity<>(featureModuleService.getById(featureId), HttpStatus.OK);
	}
}
