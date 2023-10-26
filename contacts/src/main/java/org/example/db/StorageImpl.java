package org.example.db;

import org.example.model.Contact;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageImpl implements Storage {

    private final Map<String, Contact> contacts = new HashMap<>();


    public Contact get(String email) {
        Contact contact = contacts.get(email);
        if (contact == null) {
            System.out.println("contact with email [" + email + "] not found");
        }
        return contact;

    }

    public List<Contact> getAll() {
        Collection<Contact> values = contacts.values();
        if (values.size() == 0){
            System.out.println("phone book is empty");
        }
        return new ArrayList<>(values);
    }

    public void save(Contact contact) {
        contacts.put(contact.email(),contact);
        System.out.println("contact saved");
    }

    public void delete(String email) {
        contacts.remove(email);
        System.out.println("contact deleted");
    }
}
