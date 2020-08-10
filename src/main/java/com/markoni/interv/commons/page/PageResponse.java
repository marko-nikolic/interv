package com.markoni.interv.commons.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class used for wrapping search results
 * @param <T> parameter representing content type
 */
@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;

    private int pageNumber;

    private int totalPages;

    private long totalElements;
}
