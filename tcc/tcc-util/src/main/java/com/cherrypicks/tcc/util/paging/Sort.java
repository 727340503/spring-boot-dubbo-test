package com.cherrypicks.tcc.util.paging;

import org.apache.commons.lang.StringUtils;

public class Sort {

    private String sortType;
    private String column;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(final String sortType) {
        this.sortType = sortType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(final String column) {
        this.column = column;
    }

    public enum SortType {
        ASC("ASC"), // asc 升序
        DESC("DESC"); // desc 倒序

        private final String type;

        SortType(final String type) {
            this.type = type;
        }

        public String toStringValue() {
            return this.type;
        }

        public static SortType toType(final String flag) {
            if (StringUtils.isBlank(flag)) {
                return ASC;
            } else if (StringUtils.equals("-", flag)) {
                return DESC;
            } else {
                return null;
            }
        }
    }

}
