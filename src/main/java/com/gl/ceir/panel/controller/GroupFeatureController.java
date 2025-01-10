package com.gl.ceir.panel.controller;

import java.util.List;

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

import com.gl.ceir.panel.dto.GroupFeatureDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.response.GroupFeatureViewDto;
import com.gl.ceir.panel.entity.app.GroupEntity;
import com.gl.ceir.panel.entity.app.GroupFeatureId;
import com.gl.ceir.panel.service.GroupFeatureService;

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
@RequestMapping("groupFeature")
@RequiredArgsConstructor
@Tag(name = "Api to manage group's features")
public class GroupFeatureController {
	private final GroupFeatureService groupFeatureService;
	
	@Operation(summary = "Save features for a group", description = "Return saved group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody GroupFeatureDto groupFeatureDto, HttpServletRequest request) {
		return new ResponseEntity<>(groupFeatureService.save(groupFeatureDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Return list of group's features", description = "Return active list of features based on groupId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupFeatureViewDto.class))) }) })
	@GetMapping("/{groupId}")
	public ResponseEntity<?> getById(@PathVariable Long groupId){
		log.info("Group id:{}", groupId);
		return new ResponseEntity<>(groupFeatureService.getById(groupId), HttpStatus.OK);
	}
	@Operation(summary = "Update features for a group", description = "Return updated group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody GroupFeatureDto groupFeatureDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Group:{},update request:{}", id, groupFeatureDto);
		return new ResponseEntity<>(groupFeatureService.update(groupFeatureDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Delete features from group by group id and feature id", description = "Return deleted api status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<GroupFeatureId> ids) {
		return new ResponseEntity<>(groupFeatureService.delete(ids), HttpStatus.OK);
	}
	@Operation(summary = "Activate features from group by group id and feature id", description = "Return deleted group's feature status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<GroupFeatureId> ids) {
		return new ResponseEntity<>(groupFeatureService.active(ids), HttpStatus.OK);
	}
	@Operation(summary = "Return group feature list", description = "Return group feature pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(groupFeatureService.pagination(ulrd), HttpStatus.OK);
	}
}
