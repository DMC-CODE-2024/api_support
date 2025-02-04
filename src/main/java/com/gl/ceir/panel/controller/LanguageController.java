package com.gl.ceir.panel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.dto.response.ApiStatusDto;
import com.gl.ceir.panel.service.LanguageService;

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
@RequiredArgsConstructor
@RequestMapping("/language")
@CrossOrigin
@Tag(name = "Return multilingual json based on language specific json")
public class LanguageController {
	private final LanguageService languageService;
	
	@Operation(summary = "Return language json based on language code us.json/km.json", description = "Return language us/km.json")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }) })
	@GetMapping("/{language}")
	public ResponseEntity<?> getById(@PathVariable String language) {
		log.info("language: {}", language);
		language = "en.json".equals(language) ? "us.json": language;
		log.info("language: {}", language);
		return new ResponseEntity<>(languageService.languagejson(language), HttpStatus.OK);
	}
	
	@Operation(hidden = true)
	@GetMapping("update/{language}")
	public ResponseEntity<?> sendotp(@PathVariable String language) {
		return new ResponseEntity<>(languageService.updateLanguage(language), HttpStatus.OK);
	}
}
