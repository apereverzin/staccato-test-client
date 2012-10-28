package com.creek.staccato.client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import com.creek.staccato.client.command.CommandFactory;
import com.creek.staccato.client.command.StaccatoCommand;
import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.domain.message.CommunicationException;
import com.creek.staccato.domain.message.MessageCommunicator;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.repository.email.MailMessageCommunicator;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class StaccatoClient {
    private MessageCommunicator communicator;
    private MailMessageConnector connector;
    
    public StaccatoClient(Properties props) throws RepositoryException {
        connector = new MailMessageConnector(props);
        communicator = new MailMessageCommunicator(connector);
    }
    
    public static void main(String[] args) {
        if(args.length < 2) {
            printHelp();
            System.exit(0);
        }
        
        try {
            Properties props = readProperties(args[0]);
            StaccatoClient client = new StaccatoClient(props);
            ProfileKey profileKey = new ProfileKey(props.getProperty("mail.username"));
            Profile myProfile = new Profile(profileKey);
            StaccatoCommand command = CommandFactory.getCommand(myProfile, Arrays.copyOfRange(args, 1, args.length));
            System.out.println("Executing command: " + command);
            client.executeCommand(command);
            System.out.println("Command executed");
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        } catch(CommunicationException ex) {
            ex.printStackTrace();
            System.exit(1);
        } catch(RepositoryException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    public void executeCommand(StaccatoCommand command) throws CommunicationException {
        communicator.sendMessage(command.getMessage());
    }
    
    private static Properties readProperties(String clientName) throws IOException {
        Properties allProps = new Properties();
        allProps.load(new FileReader("src/main/resources/client.properties"));
        Properties props = new Properties();
        Enumeration keys = allProps.keys();
        String prefix = "client." + clientName + ".";
        while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            if(key.startsWith(prefix)) {
                String value = allProps.getProperty(key);
                props.put(key.substring(prefix.length()), value);
            }
        }
        return props;
    }

    private static void printHelp() {
        System.out.println("Usage: <client_name> <command> [<arguments>]");
    }
}
