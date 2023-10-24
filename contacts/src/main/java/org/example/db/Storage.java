package org.example.db;

import org.example.model.Contact;


import java.util.List;

public interface Storage {

    public Contact get(String email);

    public List<Contact> getAll();

    public void save(Contact contact);

    public void delete(String email);

}
