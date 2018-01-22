package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.model.Language;
import com.cherrypicks.tcc.cms.system.service.LanguageService;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;

@RestController
public class LanguageController extends BaseController<Language>{
	
	@Reference(version="1.0")
	private LanguageService languageService;
	
	@Override
	@Reference(version="1.0",interfaceClass=SystemRoleService.class)
	public void setBaseService(IBaseService<Language> languageService) {
		super.setBaseService(languageService);
	}
	
	@RequestMapping(value="/getLanguageAllList")
	public ResultVO getLanguageAllList(){
		
		List<Language> languageList = languageService.findAll();
		
		ResultVO result = new ResultVO();
		result.setResult(languageList);
		return result;
	}
	
}
