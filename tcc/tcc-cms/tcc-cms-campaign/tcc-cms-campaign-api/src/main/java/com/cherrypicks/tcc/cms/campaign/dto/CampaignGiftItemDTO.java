package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class CampaignGiftItemDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private Long giftId;
	private String name;
	private Date createdTime;
	private Integer status;
	private Date startTime;
	private Date endTime;
	private Integer isReservation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(Integer isReservation) {
		this.isReservation = isReservation;
	}

}
