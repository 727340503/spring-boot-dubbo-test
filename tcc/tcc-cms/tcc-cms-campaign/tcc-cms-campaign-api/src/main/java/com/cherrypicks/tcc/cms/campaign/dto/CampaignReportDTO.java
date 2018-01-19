package com.cherrypicks.tcc.cms.campaign.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class CampaignReportDTO extends BaseObject {

	private static final long serialVersionUID = -6446194093037111796L;
	private Long totalStamps;
	private Long totalUsedStamps;
	private Long totalRedemptionCount;
	private Long totalReservationCount;
	private Double averageUsedStamps;
	private Double averageCollectStamps;
	private Double averageRedemptionCount;
	private Long totalCollectCoupon;
	private Long totalActiveCoupon;
	private Long totalInactiveCoupon;
	private Long totalRedeemCoupon;
	private Long totalExpiredCoupon;

	public Long getTotalStamps() {
		return totalStamps;
	}

	public void setTotalStamps(Long totalStamps) {
		this.totalStamps = totalStamps;
	}

	public Long getTotalUsedStamps() {
		return totalUsedStamps;
	}

	public void setTotalUsedStamps(Long totalUsedStamps) {
		this.totalUsedStamps = totalUsedStamps;
	}

	public Long getTotalRedemptionCount() {
		return totalRedemptionCount;
	}

	public void setTotalRedemptionCount(Long totalRedemptionCount) {
		this.totalRedemptionCount = totalRedemptionCount;
	}

	public Long getTotalReservationCount() {
		return totalReservationCount;
	}

	public void setTotalReservationCount(Long totalReservationCount) {
		this.totalReservationCount = totalReservationCount;
	}

	public Double getAverageUsedStamps() {
		return averageUsedStamps;
	}

	public void setAverageUsedStamps(Double averageUsedStamps) {
		this.averageUsedStamps = averageUsedStamps;
	}

	public Double getAverageCollectStamps() {
		return averageCollectStamps;
	}

	public void setAverageCollectStamps(Double averageCollectStamps) {
		this.averageCollectStamps = averageCollectStamps;
	}

	public Double getAverageRedemptionCount() {
		return averageRedemptionCount;
	}

	public void setAverageRedemptionCount(Double averageRedemptionCount) {
		this.averageRedemptionCount = averageRedemptionCount;
	}

	public Long getTotalCollectCoupon() {
		return totalCollectCoupon;
	}

	public void setTotalCollectCoupon(Long totalCollectCoupon) {
		this.totalCollectCoupon = totalCollectCoupon;
	}

	public Long getTotalRedeemCoupon() {
		return totalRedeemCoupon;
	}

	public void setTotalRedeemCoupon(Long totalRedeemCoupon) {
		this.totalRedeemCoupon = totalRedeemCoupon;
	}

	public Long getTotalExpiredCoupon() {
		return totalExpiredCoupon;
	}

	public void setTotalExpiredCoupon(Long totalExpiredCoupon) {
		this.totalExpiredCoupon = totalExpiredCoupon;
	}

	public Long getTotalActiveCoupon() {
		return totalActiveCoupon;
	}

	public void setTotalActiveCoupon(Long totalActiveCoupon) {
		this.totalActiveCoupon = totalActiveCoupon;
	}

	public Long getTotalInactiveCoupon() {
		return totalInactiveCoupon;
	}

	public void setTotalInactiveCoupon(Long totalInactiveCoupon) {
		this.totalInactiveCoupon = totalInactiveCoupon;
	}

}
