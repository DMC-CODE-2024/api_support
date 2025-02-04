package com.gl.ceir.panel.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PlaceholderUtil {
	
	public String message(Map<String, String> values, String message) {
		return replaceParams(values, message);
	}
	
	public String replaceParams(Map<String, String> hashMap, String template) {
		return hashMap.entrySet().stream().reduce(template, (s, e) -> s.replace("{" + e.getKey() + "}", StringUtils.isEmpty(e.getValue()) ? "": e.getValue()), (s, s2) -> s);
	}
}
