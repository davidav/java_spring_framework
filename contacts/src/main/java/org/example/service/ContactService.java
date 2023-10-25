package org.example.service;


import org.example.db.Storage;
import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ContactService {

    @Value("${fileNewContacts}")
    String fileNewContacts;

    private final Storage storage;


    @Autowired
    public ContactService(Storage storage) {
        this.storage = storage;
    }

    public Contact get(String email) {
        return storage.get(email);
    }

    public List<Contact> getAll() {
        return storage.getAll();
    }

    public void save(Contact contact) {
        storage.save(contact);
    }

    public void delete(String email) {
        storage.delete(email);
    }


    public void saveAll(List<Contact> contacts) {
        for (Contact contact : contacts) {
            storage.save(contact);
        }
        System.out.println("contacts loaded from file");
    }

    public void persist() {
        List<Contact> allContacts = storage.getAll();
        String textOut = allContacts.stream()
                .map(Contact::toFile)
                .collect(Collectors.joining("\n"));
        Path pathOut = Paths.get(fileNewContacts);
        try {
            if (Files.exists(pathOut)) {
                Files.delete(pathOut);
            }
            Files.createFile(pathOut);
            Files.writeString(pathOut, textOut);
        } catch (IOException ex) {
            System.out.println("write error");
        }
    }
}
