package org.example.service;

import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("init")
public class ContactLoader{

    private final ContactService contactService;
    @Value("${fileContacts}")
    private String pathFileContacts;

    @Autowired
    public ContactLoader(ContactService contactService) {
        this.contactService = contactService;
    }


    public void load() {
        List<Contact> contacts = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(pathFileContacts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String line : lines) {
            String[] fragments = line.split(";");
            if (fragments.length != 3) {
                System.out.println("Wrong line: " + line);
                continue;
            }
            contacts.add(new Contact(
                    fragments[0],
                    fragments[1],
                    fragments[2]
            ));
        }
        contactService.saveAll(contacts);
    }

}
