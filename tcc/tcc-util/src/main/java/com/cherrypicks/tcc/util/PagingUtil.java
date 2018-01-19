package com.cherrypicks.tcc.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cherrypicks.tcc.util.paging.PagingList;

public final class PagingUtil {

    protected static Log logger = LogFactory.getLog(PagingUtil.class);

    private PagingUtil() {
    }

    public static int[] toArgs(final Integer startIndex, final Integer endIndex) {
        int[] args = null;
        if (startIndex != null && endIndex != null) {
            if (startIndex > 0 && endIndex - startIndex > 0) {
                args = new int[] {startIndex, endIndex - startIndex + 1};
            } else {
                args = new int[] {startIndex, endIndex + 1};
            }
        }
        return args;
    }

    /**
     * <pre>
     * return PagingList's total size
     * resultList must instanceof PagingList
     * </pre>
     *
     * @param resultList
     * @return
     */
    public static int getPagingListTotalSize(final List<?> resultList) {
        // get total size
        int totalSize = 0;
        if (resultList == null) {
            return totalSize;
        }
        if (resultList instanceof PagingList) {
            final PagingList<?> pagingList = (PagingList<?>) resultList;
            totalSize = pagingList.getTotalRecords() != 0 ? (int) pagingList.getTotalRecords() : pagingList.size();
        } else {
            totalSize = resultList.size();
        }
        return totalSize;
    }

    public static void main(final String[] args) {
        logger.info(PagingUtil.toArgs(0, 20)[0] + "," + PagingUtil.toArgs(0, 20)[1]);
        logger.info(PagingUtil.toArgs(21, 40)[0] + "," + PagingUtil.toArgs(21, 40)[1]);
        logger.info(PagingUtil.toArgs(41, 60)[0] + "," + PagingUtil.toArgs(41, 60)[1]);
        logger.info(PagingUtil.toArgs(61, 80)[0] + "," + PagingUtil.toArgs(61, 80)[1]);

//        logger.info(PagingUtil.toArgs(0, 9)[0] + "," + PagingUtil.toArgs(0, 9)[1]);
//        logger.info(PagingUtil.toArgs(10, 19)[0] + "," + PagingUtil.toArgs(10, 19)[1]);
//        logger.info(PagingUtil.toArgs(20, 29)[0] + "," + PagingUtil.toArgs(20, 29)[1]);
//        logger.info(PagingUtil.toArgs(30, 39)[0] + "," + PagingUtil.toArgs(30, 39)[1]);
//        logger.info(PagingUtil.toArgs(40, 45)[0] + "," + PagingUtil.toArgs(40, 45)[1]);
    }
}
