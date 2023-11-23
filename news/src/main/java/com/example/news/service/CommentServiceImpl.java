package com.example.news.service;

import com.example.news.model.Comment;
import com.example.news.repository.CommentRepository;
import com.example.news.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;


    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Комментарий с id {} не найден", id).getMessage()));
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        Comment savedCommit = commentRepository.save(comment);
        userService.update(savedCommit.getUser());
        newsService.update(savedCommit.getNews());
        return savedCommit;
    }

    @Override
    public Comment update(Comment comment) {
        Comment existedComment = findById(comment.getId());
        AppHelperUtils.copyNonNullProperties(comment, existedComment);

        return commentRepository.save(existedComment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }
}
