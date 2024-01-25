package com.example.news.aop;

import com.example.news.model.User;
import com.example.news.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserAspect {

    private final UserRepository userRepository;

    @Before("@annotation(com.example.news.aop.UserActionByIdAvailable)")
    public void userActionByIdBefore(JoinPoint joinPoint) throws AuthenticationException {
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        UserDetails userDetails = (UserDetails) args[1];
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        User existUser = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormatter.format(
                        "UserAspect -> User with id {} not found", id).getMessage()));

        for (String role : roles){
            if(role.equals("ADMIN") || role.equals("MODERATOR")){
                break;
            }else if (role.equals("USER") && roles.size() == 1){
                if (!Objects.equals(existUser.getId(), id)){
                    throw new AuthenticationException("Receive, change, delete information about the user by ID" +
                            "available only to users with a roles ADMIN, MODERATOR or USER himself");
                }
            }
        }
    }

}
