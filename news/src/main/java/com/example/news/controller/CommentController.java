package com.example.news.controller;

import com.example.news.dto.ErrorResponse;
import com.example.news.dto.PagesRequest;
import com.example.news.dto.comment.CommentListResponse;
import com.example.news.dto.comment.CommentResponse;
import com.example.news.dto.comment.UpsertCommentRequest;
import com.example.news.dto.mapper.CommentMapper;
import com.example.news.model.Comment;
import com.example.news.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Tag(name = "Comment", description = "Comment API")
public class CommentController {
    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @Operation(
            summary = "Get paginated all comments",
            description = "Get all comments. Return list of paginated comments",
            tags = {"news"}
    )
    @GetMapping
    @PreAuthorize(value = "hasAnyRole('USER','ADMIN','MODERATOR')")
    public ResponseEntity<CommentListResponse> findAll(@Valid PagesRequest request) {

        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(
                        commentService.findAll(request)));
    }

    @Operation(
            summary = "Get comment by id",
            description = "Return comment, userId, newsId comment's with a specific ID",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('USER','ADMIN','MODERATOR')")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentService.findById(id)));
    }

    @Operation(
            summary = "Create new comment",
            description = "Return new comment",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
            )
    })
    @PostMapping
    @PreAuthorize(value = "hasAnyRole('USER','ADMIN','MODERATOR')")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = commentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(newComment));
    }

    @Operation(
            summary = "Edit comment",
            description = "Return edited comment. Only the creator comment can edit it",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = CommentResponse.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('USER','ADMIN','MODERATOR')")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertCommentRequest request) {
        Comment comment = commentMapper.requestToComment(id, request);
        Comment updateComment = commentService.update(comment);
        return ResponseEntity.ok(commentMapper.commentToResponse(updateComment));
    }

    @Operation(
            summary = "Delete comment",
            description = "Delete comment with a specific ID. Only the creator comment can delete it",
            tags = {"comment", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204"
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('USER','ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestParam Long userId) {
        commentService.deleteById(id, userId);
        return ResponseEntity.noContent().build();
    }
}
