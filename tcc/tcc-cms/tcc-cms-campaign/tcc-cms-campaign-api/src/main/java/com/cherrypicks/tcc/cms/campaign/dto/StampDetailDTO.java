package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StampDetailDTO extends BaseObject {

	private static final long serialVersionUID = -1670868078282813379L;
	private Long id;
	private Long stampCardId;
	private Double stampMoney;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	private Boolean isDeleted = false;
	private List<StampLangMapDetailDTO> stampLangMaps;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStampCardId() {
		return stampCardId;
	}

	public void setStampCardId(Long stampCardId) {
		this.stampCardId = stampCardId;
	}

	public Double getStampMoney() {
		return stampMoney;
	}

	public void setStampMoney(Double stampMoney) {
		this.stampMoney = stampMoney;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<StampLangMapDetailDTO> getStampLangMaps() {
		return stampLangMaps;
	}

	public void setStampLangMaps(List<StampLangMapDetailDTO> stampLangMaps) {
		this.stampLangMaps = stampLangMaps;
	}

}
