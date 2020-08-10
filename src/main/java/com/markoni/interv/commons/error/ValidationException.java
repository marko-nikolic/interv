package com.markoni.interv.commons.error;

import com.markoni.interv.commons.message.Message;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception used for custom validation
 */
@Getter
public class ValidationException extends RuntimeException {

    private List<Message> messages;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, List<Message> messages) {
        super(message);
        this.messages = messages;
    }

    public ValidationException(List<Message> messages) {
        this(null, messages);
    }

    public ValidationException(Message message) {
        messages = new ArrayList<>();
        messages.add(message);
    }

}
