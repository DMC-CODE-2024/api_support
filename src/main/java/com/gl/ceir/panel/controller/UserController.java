package com.gl.ceir.panel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.panel.constant.OtpChannelTypeEnum;
import com.gl.ceir.panel.constant.UserTypeEnum;
import com.gl.ceir.panel.dto.PaginationRequestDto;
import com.gl.ceir.panel.dto.UserCreateDto;
import com.gl.ceir.panel.dto.UserGroupPermissionDto;
import com.gl.ceir.panel.dto.UserUpdateDto;
import com.gl.ceir.panel.dto.response.ApiStatusDto;
import com.gl.ceir.panel.dto.response.BooleanDto;
import com.gl.ceir.panel.dto.response.MessageResponse;
import com.gl.ceir.panel.entity.app.LinkEntity;
import com.gl.ceir.panel.entity.app.UserEntity;
import com.gl.ceir.panel.service.RedmineBackendService;
import com.gl.ceir.panel.service.UserPermissionService;
import com.gl.ceir.panel.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name = "Api to manage user")
public class UserController {
	private final UserService userService;
	private final UserPermissionService userPermissionService;
	private final RedmineBackendService redmineBackendService;

	@Value("${user.for.testing:}")
	private String userForTesting;

	@Operation(summary = "Save user", description = "Return saved user status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = LinkEntity.class)) }) })
	@PostMapping("save")
	public ResponseEntity<?> save(@Valid @ModelAttribute UserCreateDto ucd, HttpServletRequest request) {
		log.info("User create request:{}", ucd);
		return userService.save(ucd, request);
	}

	@Operation(summary = "Update user by userId", description = "Return saved user status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }) })
	@RequestMapping(path = "/update/{id}", method = { RequestMethod.PUT, RequestMethod.POST }, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> update(@Valid @ModelAttribute UserUpdateDto userDto, @PathVariable Long id,
			HttpServletRequest request) {
		return new ResponseEntity<>(userService.update(userDto, id, request), HttpStatus.OK);
	}

	@Operation(summary = "Return user list", description = "Return user pagination list", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }) })
	@PostMapping("pagination")
	public ResponseEntity<?> pagination(@RequestBody PaginationRequestDto ulrd) {
		return new ResponseEntity<>(userService.pagination(ulrd), HttpStatus.OK);
	}

	@Operation(summary = "Return user information by userId", description = "Return user information", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class)) }) })
	@RequestMapping("{id}")
	public ResponseEntity<?> view(@PathVariable Long id) {
		return new ResponseEntity<>(userService.viewById(id), HttpStatus.OK);
	}

	@Operation(summary = "User user email and msisdn by userId", description = "Return user record update status", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }) })
	@RequestMapping("updateEmailAndMsisdn/{id}/{email}/{msisdn}")
	public ResponseEntity<?> updateEmailandMsisdn(@PathVariable Long id, @PathVariable String email,
			@PathVariable String msisdn) {
		return new ResponseEntity<>(userService.updateEmailAndMsisdn(id, email, msisdn), HttpStatus.OK);
	}

	@Operation(summary = "Return list of active users", description = "Active list of users")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))) }) })
	@GetMapping("list")
	public ResponseEntity<?> findUsers() {
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}

	@Operation(summary = "Return list of current logged in user's permissions", description = "User's permissions list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserGroupPermissionDto.class))) }) })
	@GetMapping("permissions")
	public ResponseEntity<?> permissions() {
		return new ResponseEntity<>(userPermissionService.permissions().getPermissions(), HttpStatus.OK);
	}

	@Operation(summary = "Return user's password exiration information", description = "User's password expiration information", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BooleanDto.class)) }) })
	@GetMapping("isAlertForPasswordExpire")
	public ResponseEntity<?> isAlertForPasswordExpire() {
		return new ResponseEntity<>(userService.isAlertForPasswordExpire(), HttpStatus.OK);
	}

	@Operation(summary = "Delete users based list of user'ds", description = "Return api status", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("delete")
	public ResponseEntity<?> delete(@RequestBody List<Long> ids, HttpServletRequest request) {
		return new ResponseEntity<>(userService.delete(ids, request), HttpStatus.OK);
	}

	@Operation(summary = "Activate users based list of user'ds", description = "Return api status", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }) })
	@PostMapping("active")
	public ResponseEntity<?> active(@RequestBody List<Long> ids, HttpServletRequest request) {
		return new ResponseEntity<>(userService.active(ids, request), HttpStatus.OK);
	}

	@Operation(summary = "Reset users password list of user'ds", description = "Return api status", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)) }) })
	@PostMapping("reset-password")
	public ResponseEntity<?> resetpassword(@RequestBody List<Long> ids, HttpServletRequest request) {
		return userService.resetpassword(ids, request);
	}

	@Operation(summary = "Return current logged in user's type", description = "User Type", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserTypeEnum.class)) }) })
	@GetMapping("getUserType")
	public ResponseEntity<?> getUserType(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		map.put("userType", userService.findUserType().type);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@Operation(hidden = true)
	@GetMapping("findUsersByUserNameForTickets/{userName}")
	public ResponseEntity<?> getUserType(@PathVariable String userName) {
		userName = ObjectUtils.isNotEmpty(userForTesting) ? userForTesting : userName;
		log.info("Username:{}", userName);
		return userService.users(userName);
	}

	@Operation(summary = "Send otp on user's email/msisdn based on otp channel", description = "Send otp response", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ApiStatusDto.class)) }) })
	@GetMapping("send-otp/{otpchannel}/{emailormsisdn}/{userId}")
	public ResponseEntity<?> sendotp(@PathVariable OtpChannelTypeEnum otpchannel, @PathVariable String emailormsisdn, @PathVariable Long userId) {
		log.info("Otp request for:{}, receiver id:{}", otpchannel, emailormsisdn);
		return new ResponseEntity<>(userService.sendotp(otpchannel, emailormsisdn, userId), HttpStatus.OK);
	}

	@Operation(summary = "Verify otp on user's email/msisdn based on otp channel", description = "Send otp response", hidden = false)
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ApiStatusDto.class)) }) })
	@GetMapping("verify-otp/{emailormsisdn}/{otp}")
	public ResponseEntity<?> sendotp(@PathVariable String emailormsisdn, @PathVariable String otp) {
		log.info("Otp request receiver id:{}", emailormsisdn);
		return new ResponseEntity<>(userService.verifyOtp(emailormsisdn, otp), HttpStatus.OK);
	}

	@Operation(hidden = true)
	@RequestMapping("create-redmine-user")
	public ResponseEntity<?> createUserOnRedmine() {
		return new ResponseEntity<>(redmineBackendService.saveAllUser(), HttpStatus.OK);
	}
}
