package com.gl.ceir.panel.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.UserGroupDto;
import com.gl.ceir.panel.dto.response.UserGroupViewDto;
import com.gl.ceir.panel.entity.app.UserEntity;
import com.gl.ceir.panel.entity.app.UserGroupEntity;
import com.gl.ceir.panel.entity.app.UserGroupId;
import com.gl.ceir.panel.service.UserGroupService;

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
@RequestMapping("userGroup")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Api to manage user's group") 
public class UserGroupController {
	private final UserGroupService userGroupService;
	
	@Operation(summary = "Save groups for a user", description = "Return saved user information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody UserGroupDto userGroupDto, HttpServletRequest request) {
		log.info("Going to save user group");
		return new ResponseEntity<>(userGroupService.save(userGroupDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Updated groups for a user", description = "Return update user information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)) }) })
	@PostMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody UserGroupDto userRoleDto, @PathVariable Long id, HttpServletRequest request) {
		return new ResponseEntity<>(userGroupService.update(userRoleDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Return user group list", description = "Return user group pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		log.info("pagination:{}", ulrd);
		return new ResponseEntity<>(userGroupService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Return list of active user group", description = "Active list of user group")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserGroupEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> list() {
		return new ResponseEntity<>(userGroupService.list(), HttpStatus.OK);
	}
	@Operation(summary = "Delete user's groups by user group id", description = "Return deleted api status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<UserGroupId> ids, HttpServletRequest request) {
		return new ResponseEntity<>(userGroupService.delete(ids,request), HttpStatus.OK);
	} 
	@Operation(summary = "Active user's groups by user group id", description = "Return active api status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<UserGroupId> ids) {
		return new ResponseEntity<>(userGroupService.active(ids), HttpStatus.OK);
	}
	@Operation(summary = "Return list of user's group", description = "Return active list of user's group")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserGroupViewDto.class))) }) })
	@GetMapping("/{userId}")
	public ResponseEntity<?> getById(@PathVariable Long userId){
		return new ResponseEntity<>(userGroupService.getById(userId), HttpStatus.OK);
	}
	@Operation(summary = "Return list of user's group by list of userIds", description = "Return active list of user's group")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserGroupViewDto.class))) }) })
	@PostMapping("findByUserIds")
	public ResponseEntity<?> getByUserIds(@RequestBody List<Long> userIds){
		return new ResponseEntity<>(userGroupService.getByUserIdsId(userIds), HttpStatus.OK);
	}
}
