package com.example.news.repository;

import com.example.news.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


//    @Override
//    @EntityGraph(attributePaths = {"newses","comments"})
//    List<User> findAll();

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByLogin(String login);


}
