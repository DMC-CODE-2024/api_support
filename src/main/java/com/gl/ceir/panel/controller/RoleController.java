package com.gl.ceir.panel.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.RoleDto;
import com.gl.ceir.panel.entity.app.RoleEntity;
import com.gl.ceir.panel.entity.audit.AuditTrailEntity;
import com.gl.ceir.panel.repository.audit.AuditTrailRepository;
import com.gl.ceir.panel.service.RoleService;

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
@RequestMapping("role")
@RequiredArgsConstructor
@Tag(name = "Api to manage role")
public class RoleController {
	private final RoleService roleService;
	private final AuditTrailRepository auditTrailRepository;

	@Operation(summary = "Save role", description = "Return saved role status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleEntity.class)) }) })
	@PostMapping("save")
	public @ResponseBody RoleEntity save(@RequestBody RoleDto roleDto, HttpServletRequest request) {
		return roleService.save(roleDto, request);
	}

	@Operation(summary = "Return list of active roles", description = "Active list of roles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RoleEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findRoles() {
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}

	@Operation(summary = "Return role information by roleId", description = "Return role information", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleEntity.class)) }) })
	@RequestMapping("{id}")
	@GetMapping("/{id}")
	public @ResponseBody RoleEntity getById(@PathVariable Long id) {
		return roleService.getById(id);
	}

	@Operation(summary = "Update role by roleId", description = "Return updated role status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleEntity.class)) }) })
	@PutMapping("/update/{id}")
	public @ResponseBody RoleEntity update(@RequestBody RoleDto roleDto, @PathVariable Long id,
			HttpServletRequest request) {
		log.info("Role:{},update request:{}", id, roleDto);
		return roleService.update(roleDto, id, request);
	}

	@Operation(summary = "Delete role based on roleId", description = "Return deleted role", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleEntity.class)) }) })
	@DeleteMapping("/{id}")
	public @ResponseBody RoleEntity delete(@PathVariable Long id, HttpServletRequest request) {
		return roleService.deleteById(id, request);
	}

	@Operation(summary = "Return role list", description = "Return role pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(roleService.pagination(ulrd), HttpStatus.OK);
	}

	@Operation(summary = "Delete roles based list of role'ds", description = "Return api status", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(roleService.delete(ids), HttpStatus.OK);
	}

	@Operation(summary = "Activate roles based list of role'ds", description = "Return api status", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(roleService.active(ids), HttpStatus.OK);
	}

	@Operation(hidden = true)
	@GetMapping("saveaudit")
	public ResponseEntity<?> saveaudit() {
		AuditTrailEntity audit = AuditTrailEntity.builder().featureId(1l).featureName("demo").publicIp("demo")
				.roleType("demo").subFeature("semo").txnId(String.valueOf(System.currentTimeMillis())).userId(1l)
				.userName("demo").details("details").build();
		auditTrailRepository.save(audit);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
