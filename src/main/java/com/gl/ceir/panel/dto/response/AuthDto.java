package com.gl.ceir.panel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthDto {
	@Schema(example = "true")
	private boolean isLogin;
	@Schema(example = "false")
	private boolean isPasswordExpire;
	@Schema(example = "false")
	private boolean isTemparoryPassword;
	private Long id;
	private String userName;
	private String email;
}
