package com.example.listcontacts.repo;

import com.example.listcontacts.exception.AppHelperException;
import com.example.listcontacts.model.Contact;
import com.example.listcontacts.repo.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ContactRepositoryImpl implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("colling ContactRepositoryImpl->findAll");

        String sql = "SELECT * FROM contact";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Contact findById(Long id) {
        log.debug("colling ContactRepositoryImpl->findById with id: {}", id);
        String sql = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                ));
        if (contact != null) {
            return contact;
        }
        log.warn("Contact with id: {} not found", id);
        throw new AppHelperException("Contact with id: " + id + " not found");
    }


    @Override
    public Contact save(Contact contact) {
        log.debug("colling ContactRepositoryImpl->save contact: {}", contact);

        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("colling ContactRepositoryImpl->update contact: {}", contact);
        findById(contact.getId());
        String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id =?";
        jdbcTemplate.update(sql,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getId());
        return contact;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("colling ContactRepositoryImpl->deleteById with id: {}", id);

        String sql = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("colling ContactRepositoryImpl->batchInsert");

        String sql = "INSERT INTO contact (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contacts.get(i);
                ps.setLong(1, contact.getId());
                ps.setString(2, contact.getFirstName());
                ps.setString(3, contact.getLastName());
                ps.setString(4, contact.getEmail());
                ps.setLong(5, contact.getPhone());
            }

            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
    }
}
