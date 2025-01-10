package com.gl.ceir.panel.dto.response;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 3851888651538943509L;
	@Schema(name = "apiResult", example = "success/fail")
	private String apiResult;
	@Schema(name = "message", example = "success/fail/groupNotAssigned/failedAttemptReached")
	private String message;
	@Schema(name = "token")
	private String token;
	@Default
	private String type = "Bearer";
	private Long id;
	private String userName;
	private String email;
	private boolean isTemparoryPassword;
	@Default
	private long remainingExpiryDays = 0l;
}
