package com.cherrypicks.tcc.cms.system.service;

import org.springframework.web.multipart.MultipartFile;

import com.cherrypicks.tcc.cms.system.vo.FileUploadVO;


public interface FileUploadService{

	String upload(MultipartFile file, String lang) throws Exception;

	FileUploadVO uploadBase64File(final String base64ImageFile,final Long merchantId,final Integer type,final String lang) throws Exception;

}
