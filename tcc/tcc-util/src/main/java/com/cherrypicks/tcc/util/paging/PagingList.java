package com.cherrypicks.tcc.util.paging;

import java.util.List;

public interface PagingList<E> extends List<E> {

    int getStartRecord();

    void setStartRecord(int startRecord);

    int getMaxRecords();

    void setMaxRecords(int maxRecords);

    long getTotalRecords();

    void setTotalRecords(long totalRecords);

    int getPage();

    void setPage(int page);

    int getTotalPages();

    void setTotalPages(int totalPages);

}
