package com.gl.ceir.panel.repository.app;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gl.ceir.panel.entity.app.EirsResponseParamEntity;

@Repository
public interface EirsResponseParamRepository extends CrudRepository<EirsResponseParamEntity, Long>, JpaSpecificationExecutor<EirsResponseParamEntity> {
	public EirsResponseParamEntity findOneByTagAndLanguage(String tag, String language);
}