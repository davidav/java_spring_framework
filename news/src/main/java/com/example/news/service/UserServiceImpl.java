package com.example.news.service;

import com.example.news.dto.PagesRequest;
import com.example.news.dto.user.UserFilter;
import com.example.news.model.User;
import com.example.news.repository.UserRepository;
import com.example.news.repository.UserSpecification;
import com.example.news.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> filterBy(UserFilter filter) {
        return userRepository.findAll(
                (UserSpecification.withFilter(filter)),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();

    }

    @Override
    public List<User> findAll(PagesRequest request) {
        return userRepository.findAll(
                PageRequest.of(request.getPageNumber(), request.getPageSize())).getContent();
        }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Пользователь с id {} не найден", id).getMessage()));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());
        AppHelperUtils.copyNonNullProperties(user, existedUser);

        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
