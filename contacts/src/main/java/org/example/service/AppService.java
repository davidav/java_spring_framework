package org.example.service;

import org.example.model.Contact;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AppService {

    private final Scanner scanner;

    private final ContactService contactService;

    @Value("${screen.contacts}")
    String titleApp;
    @Value("${screen.selectAction}")
    String selectAction;
    @Value("${screen.contactExampleLabel}")
    String contactExampleLabel;
    @Value("${screen.contactExample}")
    String contactExample;
    @Value("${screen.email}")
    String email;
    @Value("${screen.emailExample}")
    String emailExample;


    @Autowired
    public AppService(Scanner scanner, ContactService contactService) {
        this.scanner = scanner;
        this.contactService = contactService;
    }

    public void start() {
        getCommand(titleApp, selectAction);
    }


    private void getCommand(String message1, String message2) {
        for (; ; ) {
            String data = getDataFromConsole(message1, message2);
            if (validation(data)) {
                commandResolver(data);
                return;
            }
            System.out.println("Не правильная команда :(");
        }

    }

    private void commandResolver(@NotNull String data) {
        switch (data) {
            case ("1"):
                Contact contact = saveContact(getDataFromConsole(contactExampleLabel, contactExample));
                contactService.save(contact);
            case ("2"):
                Contact contactFromDB = contactService.get(getDataFromConsole(email, emailExample));
                System.out.println(contactFromDB);
            case ("3"):
                System.out.println(contactService.getAll());
            case ("4"):
                contactService.delete(getDataFromConsole(email, emailExample));
            case ("5"):
                contactService.persist();
            case ("0"):
                return;
            default:
                System.out.println("Не правильная команда :(");
        }

    }

    private Contact saveContact(String dataFromConsole) {
        String[] fragments = dataFromConsole.split(";");
        return new Contact(fragments[0], fragments[1], fragments[2]);
    }

    private boolean validation(String data) {
        //        ToDo validation
        return true;
    }

    private String getDataFromConsole(String message1, String message2) {
        System.out.println(message1);
        System.out.println(message2);
        return scanner.nextLine().trim();
    }


    @Override
    public String toString() {
        return "AppService";
    }
}
