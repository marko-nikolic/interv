package com.markoni.interv.commons.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Wrapper class for retrieving service messages (i.e. validation errors, exceptions, info messages )
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private List<Message> messages;

    public static MessageResponse of(Message message) {
        return new MessageResponse(Collections.singletonList(message));
    }
}
