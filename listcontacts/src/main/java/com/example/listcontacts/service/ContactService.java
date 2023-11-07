package com.example.listcontacts.service;

import com.example.listcontacts.exception.AppHelperException;
import com.example.listcontacts.model.Contact;
import com.example.listcontacts.repo.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Contact get(int id) {
        return contactRepository.findById(id).orElseThrow(() -> new AppHelperException("not found"));
    }
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public void save(Contact contact) {
        contactRepository.save(contact);
    }



    public void delete(int id) {
        contactRepository.deleteById(id);
    }
}
