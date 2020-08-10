package com.markoni.interv.commons.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class used for wrapping search results
 * @param <T> parameter representing content type
 */
@Getter
@Setter
@AllArgsConstructor
public class PageResponse<T> {

    private T content;

    private int pageNumber;

    private int totalPages;

    private long totalElements;
}
