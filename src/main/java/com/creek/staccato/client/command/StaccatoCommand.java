package com.creek.staccato.client.command;

import com.creek.staccato.domain.message.generic.AddressedMessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class StaccatoCommand {
    private AddressedMessage message;
    
    public StaccatoCommand(AddressedMessage message) {
        this.message = message;
    }
    
    public AddressedMessage getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return getClass().getName() + ": [" +
                        "message:=" + message +
                        "]";
    }
}
