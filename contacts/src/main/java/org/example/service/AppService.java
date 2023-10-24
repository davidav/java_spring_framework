package org.example.service;

import org.example.model.Contact;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

@Service
public class AppService {
    private final Scanner scanner;
    private final ContactService contactService;

    @Value("${screen.titleApp}")
    private String titleApp;
    @Value("${screen.selectAction}")
    private String selectAction;
    @Value("${screen.contactExampleLabel}")
    private String contactExampleLabel;
    @Value("${screen.contactExampleExample}")
    private String contactExampleExample;
    @Value("${screen.enterEmailFoDeleteContact}")
    private String enterEmailFoDeleteContact;
    @Value("${screen.enterEmailFoSearchContact}")
    private String enterEmailFoSearchContact;
    @Value("${screen.emailExample}")
    private String emailExample;
    @Value("${screen.delete}")
    private String delete;
    @Value("${screen.exit}")
    private String exit;
    @Value("${screen.enterContactDetails}")
    private String enterContactDetails;
    @Value("${screen.persistAll}")
    private String persistAll;
    @Value("${screen.save}")
    private String save;
    @Value("${screen.search}")
    private String search;
    @Value("${screen.showAll}")
    private String showAll;
    private final String[] COMMANDS = new String[]{"1", "2", "3", "4", "5", "0"};

    @Autowired
    public AppService(Scanner scanner, ContactService contactService) {
        this.scanner = scanner;
        this.contactService = contactService;
    }

    public void start() {
        clearScreen();
        getCommand(" ", titleApp, selectAction, save, search, showAll, delete, persistAll, exit);
    }
    public void clearScreen() {
//        ToDo clearScreen
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private void getCommand(String... messages) {
        for (; ; ) {
            String data = getDataFromConsole(messages);
            if (validation(data)) {
                commandResolver(data);
            }
        }
    }
    private String getDataFromConsole(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
        return scanner.nextLine().trim();
    }
    private boolean validation(String data) {
        for (String c : COMMANDS) {
            if (data.equals(c)) {
                return true;
            }
        }
        System.out.println("bad command");
        return false;
    }
    private void commandResolver(@NotNull String data) {
        switch (data) {
            case ("1"):
                String dataFromConsole = getDataFromConsole(enterContactDetails, contactExampleLabel, contactExampleExample);
                if(dataFromConsole.split(";").length == 3){
                    Contact contact = saveContact(dataFromConsole);
                    contactService.save(contact);
                }else {
                    System.out.println("bad format");
                    getDataFromConsole(enterContactDetails, contactExampleLabel, contactExampleExample);
                }
                start();
            case ("2"):
                Contact contactFromDB = contactService.get(getDataFromConsole(enterEmailFoSearchContact, emailExample));
                System.out.println(contactFromDB);
                start();
            case ("3"):
                printAll();
                start();
            case ("4"):
                contactService.delete(getDataFromConsole(enterEmailFoDeleteContact, emailExample));
                start();
            case ("5"):
                contactService.persist();
                System.out.println("Contacts saved");
                start();
            case ("0"):
                System.out.println("By by");
                exit(0);
            default:
                System.out.println("Bad command :(");
        }

    }
    private Contact saveContact(@NotNull String dataFromConsole) {
        String[] fragments = dataFromConsole.split(";");
        return new Contact(fragments[0], fragments[1], fragments[2]);
    }
    private void printAll() {
        List<Contact> all = contactService.getAll();
        for (Contact contact : all) {
            System.out.println(contact);
        }
    }
    @Override
    public String toString() {
        return "AppService";
    }
}
