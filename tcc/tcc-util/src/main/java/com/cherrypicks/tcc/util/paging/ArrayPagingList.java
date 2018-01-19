package com.cherrypicks.tcc.util.paging;

import java.util.ArrayList;

public class ArrayPagingList<E> extends ArrayList<E>implements PagingList<E> {

    private static final long serialVersionUID = 7058814061801146498L;

    private int startRecord;

    private int maxRecords;

    private long totalRecords;

    private int page;

    private int totalPages;

    @Override
    public int getStartRecord() {
        return startRecord;
    }

    @Override
    public void setStartRecord(final int startRecord) {
        this.startRecord = startRecord;
    }

    @Override
    public int getMaxRecords() {
        return maxRecords;
    }

    @Override
    public void setMaxRecords(final int maxRecords) {
        this.maxRecords = maxRecords;
    }

    @Override
    public long getTotalRecords() {
        return totalRecords;
    }

    @Override
    public void setTotalRecords(final long totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(final int page) {
        this.page = page;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

}
