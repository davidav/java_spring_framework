package com.example.listcontacts.repo;

import com.example.listcontacts.model.Contact;

import java.util.List;


public interface ContactRepository {
    Contact findById(Long id);
    List<Contact> findAll();
    Contact save(Contact contact);
    Contact update(Contact contact);
    void deleteById(Long id);
    void batchInsert(List<Contact> contacts);

}
