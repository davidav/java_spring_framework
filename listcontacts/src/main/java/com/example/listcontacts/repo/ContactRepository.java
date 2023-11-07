package com.example.listcontacts.repo;

import com.example.listcontacts.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    Contact findById(int id);
    List<Contact> findAll();
    Contact save(Contact contact);
    void deleteById(int id);
}
