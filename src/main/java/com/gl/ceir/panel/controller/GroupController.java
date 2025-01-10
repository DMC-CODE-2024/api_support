package com.gl.ceir.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.GroupDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.entity.app.GroupEntity;
import com.gl.ceir.panel.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("group")
@Tag(name = "Api to manage group")
public class GroupController {
	@Autowired
	private GroupService groupService;

	@Operation(summary = "Save group", description = "Return saved group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@PostMapping("save")
	public @ResponseBody GroupEntity save(@RequestBody GroupDto groupDto, HttpServletRequest request) {
		return groupService.save(groupDto, request);
	}

	@Operation(summary = "Return list of parent groups for current logged in user", description = "Active list of parent groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupEntity.class))) }) })
	@GetMapping("parents")
	public ResponseEntity<?> findParents() {
		return new ResponseEntity<>(groupService.getParents(), HttpStatus.OK);
	}

	@Operation(summary = "Return group information", description = "Return group information by groupId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupEntity.class))) }) })
	@GetMapping("/{id}")
	public @ResponseBody GroupEntity getById(Authentication authentication, @PathVariable Long id) {
		log.info("user name: {}", authentication.getName());
		return groupService.getById(id);
	}

	@Operation(summary = "Update group by groupId", description = "Return updated group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@PutMapping("/update/{id}")
	public @ResponseBody GroupEntity update(@RequestBody GroupDto groupDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Group:{},update request:{}", id, groupDto);
		return groupService.update(groupDto, id, request);
	}

	@Operation(summary = "Delete group by groupId", description = "Return deleted group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@DeleteMapping("/{id}")
	public @ResponseBody GroupEntity delete(@PathVariable Long id, HttpServletRequest request) {
		return groupService.deleteById(id,request);
	}

	@Operation(summary = "Return group list", description = "Return group pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(groupService.pagination(ulrd), HttpStatus.OK);
	}

	@Operation(summary = "Return list of active groups", description = "Active list of groups")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findUsers() {
		return new ResponseEntity<>(groupService.getGroups(), HttpStatus.OK);
	}
}
