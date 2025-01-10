package com.gl.ceir.panel.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.constant.CategoryEnum;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@RestController
@Log4j2
@RequestMapping("category")
@RequiredArgsConstructor
@Tag(name = "Return interface categories")
public class CategoryController {
	
	
	@Operation(summary = "Return interfaces category for feature creation", description = "Return list of categories")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEnum.class)) }) })
	@GetMapping("list")
	public @ResponseBody ResponseEntity<List<CategoryEnum>> accessList() {
		return new ResponseEntity<>(Arrays.asList(CategoryEnum.values()), HttpStatus.OK);
	}
}
