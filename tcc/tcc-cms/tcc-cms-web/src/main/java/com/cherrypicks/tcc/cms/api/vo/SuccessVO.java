package com.cherrypicks.tcc.cms.api.vo;

import com.cherrypicks.tcc.util.Constants;

public class SuccessVO extends ResultVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3724531923596349515L;

	public SuccessVO() {
        super();
        setErrorMessage(Constants.SUCCESSMSG);
    }
}
