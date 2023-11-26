package com.example.news.service;

import com.example.news.dto.PagesRequest;
import com.example.news.dto.user.UserFilter;
import com.example.news.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> filterBy(UserFilter filter);

    List<User> findAll(PagesRequest request);

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

}
