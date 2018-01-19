package com.cherrypicks.tcc.cms.vo;

import java.io.Serializable;
import java.util.List;

public class PagingResultVo implements Serializable {

	private static final long serialVersionUID = 1494534539318202516L;

	private Long totalRows;
	private List<?> resultList;

	public Long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

}
