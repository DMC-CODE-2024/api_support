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
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.ModuleDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.entity.app.ModuleEntity;
import com.gl.ceir.panel.entity.app.ModuleTagEntity;
import com.gl.ceir.panel.entity.app.RoleEntity;
import com.gl.ceir.panel.service.ModuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@RestController
@Log4j2
@RequestMapping("module")
@AllArgsConstructor
@Tag(name = "Api to manage module")
public class ModuleController {
	private final ModuleService moduleService;
	
	@Operation(summary = "Save module", description = "Return saved module information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ModuleEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody ModuleDto moduleDto, HttpServletRequest request) {
		return new ResponseEntity<>(moduleService.save(moduleDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Return module information by moduleId", description = "Return module information", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ModuleEntity.class)) }) })
	@RequestMapping("{id}")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		return new ResponseEntity<>(moduleService.getById(id), HttpStatus.OK);
	}
	@Operation(summary = "Update module by moduleId", description = "Return updated module information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ModuleEntity.class)) }) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody ModuleDto moduleDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Feature:{},update request:{}", id, moduleDto);
		return new ResponseEntity<>(moduleService.update(moduleDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Delete module by module id", description = "Return deleted module information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ModuleTagEntity.class)) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
		return new ResponseEntity<>(moduleService.deleteById(id,request), HttpStatus.OK);
	}
	@Operation(summary = "Return module list", description = "Return module pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(moduleService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Return list of active tags", description = "Active list of tags")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ModuleEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findModules() {
		return new ResponseEntity<>(moduleService.getModules(), HttpStatus.OK);
	}
	@Operation(summary = "Delete module by module id list", description = "Return deleted module status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(moduleService.delete(ids), HttpStatus.OK);
	}
	@Operation(summary = "Activate module by module id list", description = "Return deleted module status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(moduleService.active(ids), HttpStatus.OK);
	}
}
