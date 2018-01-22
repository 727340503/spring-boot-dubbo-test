package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;
import com.cherrypicks.tcc.cms.system.service.SystemFuncService;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;

@RestController
public class SystemFuncController extends BaseController<SystemFunction>{
	
	@Reference(version="1.0")
	private SystemFuncService systemFuncService;
	
	@Override
	@Reference(version="1.0",interfaceClass=SystemRoleService.class)
	public void setBaseService(IBaseService<SystemFunction> systemFuncService) {
		// TODO Auto-generated method stub
		super.setBaseService(systemFuncService);
	}
	
	@RequestMapping(value="/getSystemFuncAllList",method=RequestMethod.POST)
	public ResultVO getSystemFuncList(){
		List<SystemFunction> funcs =  systemFuncService.findAll();
		
		ResultVO result = new ResultVO();
		result.setResult(funcs);
		
		return result;
	}
	
}
