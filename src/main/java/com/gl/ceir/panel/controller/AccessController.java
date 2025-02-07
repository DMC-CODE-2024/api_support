package com.gl.ceir.panel.controller;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.constant.AccessEnum;
import com.gl.ceir.panel.dto.AclTreeDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.entity.app.RoleFeatureModuleAccessEntity;
import com.gl.ceir.panel.entity.app.RoleFeatureModuleAccessId;
import com.gl.ceir.panel.service.AclService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("acl")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Acl permissions handling")
public class AccessController {
	private final AclService aclService;
	
	@Operation(summary = "Return all access list", description = "Access list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AccessEnum.class)) }) })
	@GetMapping("list")
	public @ResponseBody ResponseEntity<?> accessList() {
		return new ResponseEntity<>(Arrays.asList(AccessEnum.values()), HttpStatus.OK);
	}
	@Operation(summary = "Save access permission for a role", description = "Save acl successfully", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleFeatureModuleAccessEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody AclTreeDto aclDto , HttpServletRequest request) {
		return new ResponseEntity<>(aclService.save(aclDto, request), HttpStatus.OK);
	}
	@Operation(summary = "Find acl permissions by roleId", description = "Return acl permissions by roleId", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RoleFeatureModuleAccessEntity.class)) }) })
	@GetMapping("/findByRoleId/{roleId}")
	public ResponseEntity<List<RoleFeatureModuleAccessEntity>> getByRoleId(@PathVariable Long roleId){
		return new ResponseEntity<>(aclService.findByRoleId(roleId), HttpStatus.OK);
	}
	@Operation(summary = "Find acl permissions tree by roleId", description = "Return acl permissions by roledId as tree", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AclTreeDto.class)) }) })
	@GetMapping("/findTreeByRoleId/{roleId}")
	public ResponseEntity<AclTreeDto> getTreeByRoleId(@PathVariable Long roleId){
		return new ResponseEntity<>(aclService.getTreeByRoleId(roleId), HttpStatus.OK);
	}
	@Operation(summary = "Access control list", description = "Return pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<Page<?>> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(aclService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Delete access control based on roleId, featureId, and moduleId", description = "Update acl status as Deleted", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<RoleFeatureModuleAccessId> ids) {
		return new ResponseEntity<>(aclService.delete(ids), HttpStatus.OK);
	}
	@Operation(summary = "Activate access control based on roleId, featureId, and moduleId", description = "Update acl status as Active", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<RoleFeatureModuleAccessId> ids) {
		return new ResponseEntity<>(aclService.active(ids), HttpStatus.OK);
	}
	@Operation(hidden = true)
	@SuppressWarnings("rawtypes")
	@GetMapping("isAllowInYourRegion")
	public ResponseEntity<?> isAllowInYourRegion(HttpServletRequest request) {
		Enumeration headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
	        String headerName = (String)headerNames.nextElement();
	        log.info("header: " + headerName + ":" + request.getHeader(headerName));
	    }
		log.info("Request ip:{}", request.getRemoteAddr());
		return new ResponseEntity<>(aclService.checkRegion(request.getRemoteAddr()), HttpStatus.OK);
	}
}
