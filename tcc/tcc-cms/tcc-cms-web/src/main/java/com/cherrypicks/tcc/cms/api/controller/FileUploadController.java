package com.cherrypicks.tcc.cms.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.system.service.FileUploadService;
import com.cherrypicks.tcc.cms.system.vo.FileUploadVO;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class FileUploadController{
	
	@Reference(version="1.0")
	private FileUploadService fileUploadService;
	
	@RequestMapping(value="/uploadImg",method=RequestMethod.POST)
	public ResultVO fileUpload(MultipartFile file,final String lang) throws Exception{
		
		String fileName = fileUploadService.upload(file,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(fileName);
		
		return result;
	}
	
	@RequestMapping(value="/uploadBase64ImageFile",method=RequestMethod.POST)
	public ResultVO uploadBase64ImageFile(final String image,final Integer type,final Long merchantId, final String lang) throws Exception{
		
		
		AssertUtil.notBlank(image, I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(merchantId, "Merchant id " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notNull(type, "Type " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		FileUploadVO fileInfo = fileUploadService.uploadBase64File(image,merchantId,type,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(fileInfo);
		
		return result;
	}
}
