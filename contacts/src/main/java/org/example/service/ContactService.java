package org.example.service;


import org.example.db.Storage;
import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactService {

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

    public void save(Contact contact){
        storage.save(contact);
    }

    public void delete(String email){
        storage.delete(email);
    }


    public void saveAll(List<Contact> contacts) {
        for (Contact contact:contacts){
            storage.save(contact);
        }
        System.out.println(" stop");
    }

    public void persist() {
        // todo: write to file
        System.out.println("soon");
    }
}
