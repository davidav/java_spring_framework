package com.example.listcontacts.repo.mapper;


import com.example.listcontacts.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Contact.builder()
                .id(rs.getLong(Contact.Fields.id))
                .firstName(rs.getString(Contact.Fields.firstName))
                .lastName(rs.getString(Contact.Fields.lastName))
                .email(rs.getString(Contact.Fields.email))
                .phone(rs.getLong(Contact.Fields.phone))
                .build();
    }
}
