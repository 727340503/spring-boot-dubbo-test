package com.cherrypicks.tcc.cms.api.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cherrypicks.tcc.cms.dto.BaseModel;
import com.cherrypicks.tcc.cms.enums.Lang;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.vo.PagingResultVo;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.paging.PagingList;


public class BaseController<T extends BaseModel> {
    public static final String RESULT_MESSAGE = "resultMsg";
    public static String ERROR_CODE_PARAMETER_NAME = "errorCode";
    public static final String ERROR_MESSAGE = "errorMsg";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final String lang = Lang.getDefaultLang().toValue();
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATETIME_PATTERN_DEFAULT);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat(DateUtil.TIME_PATTERN_DEFAULT);
    
    protected IBaseService<T> baseService;

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    @PreDestroy
    public void destroy() {
        logger.info("destroy");
    }
    
    /**
	 * fetch data
	 * 
	 * @param dsRequest
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected PagingResultVo doFetch(Integer startRow,Integer maxRows,String sortField,String sortType,Map<String, Object> criteriaMap) {
		logger.debug("procesing fetch operation...");

		int[] args = null;
		if (null != startRow && null != maxRows && startRow >= 0 && maxRows > 0) {
			args = new int[] { startRow, maxRows };
		}

		logger.debug("sortBy........" + sortField);
		if(null == sortType){
			sortType = "ASC";
		}

		List<? extends Object> resultList = baseService.findByFilter(criteriaMap, sortField, sortType, args);
		
		PagingResultVo result = new PagingResultVo();
		result.setResultList(resultList);
		
		if (args != null && resultList instanceof PagingList) {
			PagingList<Object> pagingResultList = (PagingList<Object>) resultList;
			result.setTotalRows(pagingResultList.getTotalRecords());
		}
		
		return result;
	}

    /**
     * Set up a custom property editor for converting form inputs to real objects
     *
     * @param request
     *            the current request
     * @param binder
     *            the data binder
     */
    @InitBinder
    public void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Time.class, null, new CustomDateEditor(timeFormat, true));
    }

    protected Integer toInteger(final String data) {
        return (StringUtils.isNotBlank(data) && StringUtils.isNumeric(data)) ? Integer.parseInt(data) : null;
    }

    protected Long toLong(final String data) {
        return (StringUtils.isNotBlank(data) && StringUtils.isNumeric(data)) ? Long.parseLong(data) : null;
    }

    protected Boolean toBoolean(final String data) {
        return StringUtils.isNotBlank(data) ? Boolean.parseBoolean(data) : false;
    }

    protected Double toDouble(final String data) {
        return StringUtils.isNotBlank(data) ? Double.parseDouble(data) : null;
    }
    
    public IBaseService<T> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<T> baseService) {
		this.baseService = baseService;
	}

}
