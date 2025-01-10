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

import com.gl.ceir.panel.dto.GroupRoleDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.response.GroupRoleViewDto;
import com.gl.ceir.panel.entity.app.GroupEntity;
import com.gl.ceir.panel.entity.app.GroupRoleId;
import com.gl.ceir.panel.service.GroupRoleService;

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
@RequestMapping("groupRole")
@RequiredArgsConstructor
@Tag(name = "Api to manage group's role")
public class GroupRoleController {
	private final GroupRoleService groupRoleService;
	
	@Operation(summary = "Save roles for a group", description = "Return saved group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody GroupRoleDto groupRoleDto, HttpServletRequest request) {
		return new ResponseEntity<>(groupRoleService.save(groupRoleDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Return list of group's role", description = "Return active list of roles based on groupId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GroupRoleViewDto.class))) }) })
	@GetMapping("/{groupId}")
	public ResponseEntity<?> getById(@PathVariable Long groupId){
		return new ResponseEntity<>(groupRoleService.getById(groupId), HttpStatus.OK);
	}
	@Operation(summary = "Update roles for a group", description = "Return updated group information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = GroupEntity.class)) }) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody GroupRoleDto groupRoleDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Group:{},update request:{}", id, groupRoleDto);
		return new ResponseEntity<>(groupRoleService.update(groupRoleDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Delete roles from group by group id and role id", description = "Return deleted group apid status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<GroupRoleId> ids) {
		return new ResponseEntity<>(groupRoleService.delete(ids), HttpStatus.OK);
	}
	@Operation(summary = "Activate roles from group by group id and role id", description = "Return deleted group's roles status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<GroupRoleId> ids) {
		return new ResponseEntity<>(groupRoleService.active(ids), HttpStatus.OK);
	}
	@Operation(summary = "Return group role list", description = "Return group role pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(groupRoleService.pagination(ulrd), HttpStatus.OK);
	}
}
