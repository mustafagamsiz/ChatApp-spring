package org.gamsiz.chatapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchConversationException extends RuntimeException {

    private static final long serialVersionUID = 6037350057738389725L;
    
    private static final String MESSAGE = "No such conversation with given participant {0}";
    
    public NoSuchConversationException(String participant) {
        super(MessageFormat.format(MESSAGE, participant));
    }

}
