package com.example.listcontacts.service;

import com.example.listcontacts.model.Contact;
import com.example.listcontacts.repo.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Contact get(Long id) {
        return contactRepository.findById(id);
    }
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public void save(Contact contact) {
        if (contact.getId() == null){
            contactRepository.save(contact);
        }
        contactRepository.update(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
