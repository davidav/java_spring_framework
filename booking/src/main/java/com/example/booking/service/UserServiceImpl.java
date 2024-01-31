package com.example.booking.service;



import com.example.booking.dto.PagesRequest;
import com.example.booking.entity.RoleType;
import com.example.booking.entity.User;
import com.example.booking.repo.UserRepository;
import com.example.booking.util.AppHelperUtils;
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
    public List<User> findAll(PagesRequest request) {
        return userRepository.findAll(
                PageRequest.of(request.getPageNumber(), request.getPageSize())).getContent();
        }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("User with id={} not found", id).getMessage()));
    }

    @Override
    public User save(User user, RoleType role) {
        if (userRepository.existsUserByUsernameAndEmail(user.getUsername(), user.getEmail())){
            throw new RuntimeException(
                    MessageFormatter.format("User with username={} and email={} already exist",
                            user.getUsername(), user.getEmail()).getMessage());
        }
        user.setRole(role);
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

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(
                        MessageFormatter.format("User with username {} not found", username).getMessage()));
    }

}
