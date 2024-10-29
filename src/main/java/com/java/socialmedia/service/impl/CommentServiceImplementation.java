package com.java.socialmedia.service.impl;

import com.java.socialmedia.models.Comment;
import com.java.socialmedia.models.Post;
import com.java.socialmedia.models.User;
import com.java.socialmedia.repository.CommentRepository;
import com.java.socialmedia.repository.PostRepository;
import com.java.socialmedia.service.CommentService;
import com.java.socialmedia.service.PostService;
import com.java.socialmedia.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImplementation implements CommentService {
    private PostService postService;
    private UserService userService;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        postRepository.save(post);

        return savedComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isEmpty()) {
            throw new Exception("Comment is not exist");
        }

        return comment.get();
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);

        if (!comment.getLiked().contains(user)) {
            comment.getLiked().add(user);
        } else {
            comment.getLiked().remove(user);
        }

        return commentRepository.save(comment);
    }
}
