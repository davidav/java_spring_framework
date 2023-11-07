package com.example.listcontacts.repo;


import com.example.listcontacts.model.Contact;
import com.example.listcontacts.repo.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ContactRepositoryImpl implements ContactRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("colling ContactRepositoryImpl->findAll");

        String sql = "SELECT * FROM contact";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Contact findById(int id) {
        return null;
    }



    @Override
    public Contact save(Contact contact) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
