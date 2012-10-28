package com.creek.staccato.client.command;

import com.creek.staccato.client.addressee.AddresseeProducer;
import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class CommandFactory {
    private static final String SEND_INFORMATION_MESSAGE_COMMAND = "send_information_message";
    
    public static StaccatoCommand getCommand(Profile profileFrom, String... args) {
        MessageKey messageKey = new MessageKey(profileFrom.getProfileKey(), System.currentTimeMillis());
        String command = args[0];
        String emailAddress = args[1];
        String title = args[2];
        String text = args[3];
        AddresseeProducer producer = new AddresseeProducer(emailAddress);
        if(SEND_INFORMATION_MESSAGE_COMMAND.equals(command)) {
            InformationMessage message = new InformationMessage(title, text, producer.getGroupKeys(), producer.getProfileKeys(), messageKey, AbstractRepository.VERSION);
            return new StaccatoCommand(message);
        }
        throw new IllegalArgumentException(String.format("Unknown command: %s", args[0]));
    }
}
