package org.example.db;

import org.example.model.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StorageImpl implements Storage {

    private final Map<String, Contact> contacts = new HashMap<>();


    public Contact get(String email){
        return contacts.get(email);
    }

    public List<Contact> getAll(){
        return new ArrayList<>(contacts.values());
    }

    public Contact save(Contact contact){
        return contacts.put(contact.getEmail(), contact);
    }

    public void delete(String email){
        contacts.remove(email);
    }
}
