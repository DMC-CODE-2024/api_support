package com.gl.ceir.panel.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.NON_NULL)
public class MessageResponse implements Serializable {
	private static final long serialVersionUID = -3017981618384068100L;
	private String status;
	@Schema(hidden = true)
	private HttpStatus code;
	@Default
	@Schema(hidden = true)
	private List<ErrorDto> messages = new ArrayList<>();
	private String message;
	@Schema(hidden = true)
	private Object data;
}
