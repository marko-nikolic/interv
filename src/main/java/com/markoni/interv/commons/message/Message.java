package com.markoni.interv.commons.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Class used to unify response messages through the system i.e. validation messages, exception messages
 */
@Getter
@Setter
@Builder
public class Message {

    private String message;

    private Severity severity;
}
