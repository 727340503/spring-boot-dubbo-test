package com.cherrypicks.tcc.cms.system.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class SystemFunctionFilter extends BaseModel {

	private static final long serialVersionUID = 5147622587372429086L;

	private Integer filterCode;
	private Long funcId;
	private String filterName;
	private String filterDesc;

	public Integer getFilterCode() {
		return filterCode;
	}

	public void setFilterCode(Integer filterCode) {
		this.filterCode = filterCode;
	}

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getFilterDesc() {
		return filterDesc;
	}

	public void setFilterDesc(String filterDesc) {
		this.filterDesc = filterDesc;
	}
	
	public enum SystemFunctionFilerCode{
		
		COUPON_MANAGEMENT_FILTER(1,"Coupon management");
		
		private int value;
		private String info;
		
		private SystemFunctionFilerCode(final int value,final String info) {
			this.value = value;
			this.info = info;
		}
		
		public int toValue() {
			return this.value;
		}
		
		public String toInfo() {
			return this.info;
		}
	}
}
