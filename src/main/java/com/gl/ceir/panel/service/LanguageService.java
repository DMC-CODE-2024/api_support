package com.gl.ceir.panel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.ceir.panel.repository.app.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@Service
@Log4j2
@RequiredArgsConstructor()
public class LanguageService {
	@Value("${eirs.panel.source.path:}")
	private String sourcePath;
	private final static String _LANGUAGE = "language";
	private final ObjectMapper objectMapper;
	private final ResourceLoader resourceLoader;
	private final UserService userService;
	private final UserRepository userRepository;
	private final PassiveExpiringMap<String, Object> languagemap;

	@PostConstruct
	private void init() {
		this.languagejson("us.json");
		this.languagejson("km.json");
	}
	public Object languagejson(String language) {
		Object json = null;
		try {
			this.updateLanguage(language);
			json = languagemap.get(language);
			if(ObjectUtils.isEmpty(json)) {
				log.info("Missing langage data from cache going to load from file path");
				json = read(language);
				languagemap.put(language, json);
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	public int updateLanguage(String language) {
		try {
			return userRepository.updateLanguage(userService.getLoggedInUser().getUserName(), language);
		}catch(Exception e) {
			return 0;
		}
	}

	private Object read(String language) {
		Object content = null;
		try {
			ClassLoader classLoader = ResourceReader.class.getClassLoader();
			try (InputStream in = new FileInputStream(Paths.get("").toAbsolutePath().toString() + File.separator + "language" + File.separator + language)) {
				content = IOUtils.toString(in, StandardCharsets.UTF_8);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
}
