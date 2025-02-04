package com.gl.ceir.panel.service.helper;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gl.ceir.panel.dto.NotificationDto;
import com.gl.ceir.panel.entity.app.EirsResponseParamEntity;
import com.gl.ceir.panel.repository.app.EirsResponseParamRepository;
import com.gl.ceir.panel.repository.remote.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class NotificationHelper {
	private final NotificationRepository notificationRepository;
	private final EirsResponseParamRepository eirsResponseParamRepository;
	
	public void send(NotificationDto notification) {
		try {
			log.info("Notification request:{}", notification);
			Object response = notificationRepository.sendemail(notification);
			log.info("Notification response:{}", response);
		}catch(Exception e) {
			log.error("Error while send notification:{}", e.getMessage());
		}
	}
	public EirsResponseParamEntity getMessage(String tag, String language) {
		language = "us".equals(language) || StringUtils.isEmpty(language) ? "en": language;
		EirsResponseParamEntity message = eirsResponseParamRepository.findOneByTagAndLanguage(tag, language);
		log.info("Message:{} for key:{} with language:{}", message, tag, language);
		return ObjectUtils.isEmpty(message) ? EirsResponseParamEntity.builder().value("").subject("").language("en").build() : message;
	}
}
