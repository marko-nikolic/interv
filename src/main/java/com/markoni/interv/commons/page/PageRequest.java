package com.markoni.interv.commons.page;

import lombok.Getter;
import lombok.Setter;

/**
 * Used for requesting specific page in searching mechanism
 */
@Getter
@Setter
public class PageRequest {

    private int pageNumber = 1;
    private int pageSize = 10;
    private String orderBy;
    private Direction direction;
}
