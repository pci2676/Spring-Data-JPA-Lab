package com.javabom.bomjpa.repository.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class FixedPageRequest extends PageRequest {

    public FixedPageRequest(Pageable pageable, long totalCount) {
        super(getPageNo(pageable, totalCount), pageable.getPageSize(), pageable.getSort());
    }

    private static int getPageNo(Pageable pageable, long totalCount) {
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        long requestCount = pageNo * pageSize;

        if (totalCount > requestCount) {
            return pageNo;
        }

        return (int) Math.ceil((double) totalCount / pageNo);
    }
}