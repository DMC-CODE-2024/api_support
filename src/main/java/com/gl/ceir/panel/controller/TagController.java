package com.gl.ceir.panel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.gl.ceir.panel.dto.ModuleTagDto;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.response.MessageResponse;
import com.gl.ceir.panel.entity.app.ModuleTagEntity;
import com.gl.ceir.panel.entity.app.UserEntity;
import com.gl.ceir.panel.service.TagService;

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
@RequestMapping("tag")
@AllArgsConstructor
@Tag(name = "Api to manage tag")
public class TagController {
	@Autowired
	private final TagService tagService;
	
	@Operation(summary = "Save tag", description = "Return saved tag status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody ModuleTagDto tagDto, HttpServletRequest request) {
		return new ResponseEntity<>(tagService.save(tagDto,request), HttpStatus.OK);
	}
	@Operation(summary = "Return tag information by tag id", description = "Tag information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ModuleTagEntity.class)) }) })
	@GetMapping("/{id}")
	public @ResponseBody ModuleTagEntity getById(@PathVariable Long id){
		return tagService.getById(id);
	}
	@Operation(summary = "Update tag by tag id", description = "Return updated tag status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }) })
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody ModuleTagDto tagDto, @PathVariable Long id, HttpServletRequest request) {
		log.info("Tag:{},update request:{}", id, tagDto);
		return new ResponseEntity<>(tagService.update(tagDto, id,request), HttpStatus.OK);
	}
	@Operation(summary = "Delete tag by tag id", description = "Return deleted tag information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ModuleTagEntity.class)) }) })
	@DeleteMapping("/{id}")
	public @ResponseBody ModuleTagEntity delete(@PathVariable Long id, HttpServletRequest request) {
		return tagService.deleteById(id,request);
	}
	@Operation(summary = "Return tag list", description = "Return tag pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(tagService.pagination(ulrd), HttpStatus.OK);
	}
	@Operation(summary = "Return list of active tags", description = "Active list of tags")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ModuleTagEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findUsers() {
		return new ResponseEntity<>(tagService.getTags(), HttpStatus.OK);
	}
	@Operation(summary = "Delete tag by tag id list", description = "Return deleted tag status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(tagService.delete(ids), HttpStatus.OK);
	}
	@Operation(summary = "Activate tag by tag id list", description = "Return deleted tag status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<Long> ids) {
		return new ResponseEntity<>(tagService.active(ids), HttpStatus.OK);
	}
}
