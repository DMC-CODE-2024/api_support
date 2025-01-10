package com.gl.ceir.panel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.entity.app.CountryEntity;
import com.gl.ceir.panel.repository.app.CountryRepository;
import com.gl.ceir.panel.repository.app.ProvinceRepository;

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
@RequestMapping("country")
@RequiredArgsConstructor
@Tag(name = "Return countries and dependend")
public class CountryController {
	private final CountryRepository countryRepository;
	private final ProvinceRepository provinceRepository;

	@Operation(summary = "Return Country List", description = "Return list of countryies")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CountryEntity.class)) }) })
	@GetMapping("list")
	public @ResponseBody ResponseEntity<?> accessList() {
		return new ResponseEntity<>(countryRepository.findByName("Cambodia"), HttpStatus.OK);
	}
	
	@Operation(hidden = true)
	@GetMapping("provinces")
	public @ResponseBody ResponseEntity<?> province() {
		return new ResponseEntity<>(provinceRepository.findAll(), HttpStatus.OK);
	}
}
