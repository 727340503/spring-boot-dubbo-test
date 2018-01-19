package com.cherrypicks.tcc.cms.system.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class CurrencyUnit extends BaseModel {

	private static final long serialVersionUID = -3645967866890107199L;

	private String currencyCode;
	private String currencyFontCode;
	private String descr;
	private String image;
	private Integer sortOrder;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyFontCode() {
		return currencyFontCode;
	}

	public void setCurrencyFontCode(String currencyFontCode) {
		this.currencyFontCode = currencyFontCode;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}