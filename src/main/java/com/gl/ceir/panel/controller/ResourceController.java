package com.gl.ceir.panel.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.util.QrcodeUtil;
import com.google.zxing.WriterException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SuppressWarnings("unused")
@RestController
@Log4j2
@RequestMapping("resource")
@Tag(name = "Return image/attachment based on resource")
@RequiredArgsConstructor
public class ResourceController {
	@Value("${eirs.panel.source.path:}")
	private String basepath;
	@Value("${eirs.user.manual:}")
	private String usermanual;
	@Value("${ticket.content.disposition:inline}") // inline, attachment
	private String contentDisposition;
	private final QrcodeUtil qrcodeUtil;

	@Operation(summary = "Return image in response", description = "Return image by resource param")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "424", description = "Missing Attachment") })
	@GetMapping("/path")
	public ResponseEntity<?> image(@RequestParam String resource, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		Path path = Paths.get(basepath + File.separator + resource);
		boolean exist = Files.exists(path);
		String contentType = Files.probeContentType(path);
		if (exist) {
			File file = new File(basepath + File.separator + resource);
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "" + contentDisposition + "; filename=" + file.getName());
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			ByteArrayResource resourceObj = new ByteArrayResource(Files.readAllBytes(path));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType(contentType)).body(resourceObj);
		} else {
			return new ResponseEntity<>("Missing Attachment", HttpStatus.FAILED_DEPENDENCY);
		}

	}

	@Operation(summary = "Download user manual", description = "User manual as downloadable")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok"),
			@ApiResponse(responseCode = "424", description = "Missing Attachment") })
	@GetMapping("/usermanual")
	public ResponseEntity<?> usermanul(HttpServletResponse response) throws FileNotFoundException, IOException {
		File file = new File(basepath + File.separator + usermanual);

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usermanual.pdf");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	@GetMapping("qrcode/{ticketId}/{type}")
    @ResponseBody
    public ResponseEntity<?> downloadQRCode(@PathVariable String ticketId, @PathVariable String type, @RequestParam(name = "url") String url) throws WriterException, IOException {
		log.info("Request url:{}", url);
		Path path = qrcodeUtil.generateQRCodeImage(ticketId, url, 250, 250);
		boolean exist = Files.exists(path);
		String contentType = Files.probeContentType(path);
		if (exist) {
			File file = new File(path.toUri());
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "" + type + "; filename=" + file.getName());
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");
			ByteArrayResource resourceObj = new ByteArrayResource(Files.readAllBytes(path));
			return ResponseEntity.ok().headers(header).contentLength(file.length())
					.contentType(MediaType.parseMediaType(contentType)).body(resourceObj);
		} else {
			return new ResponseEntity<>("Missing Attachment", HttpStatus.FAILED_DEPENDENCY);
		}
    }
}
