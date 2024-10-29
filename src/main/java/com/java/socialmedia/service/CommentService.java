package com.java.socialmedia.service;

import com.java.socialmedia.models.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception;
    Comment findCommentById(Integer commentId) throws Exception;
    Comment likeComment(Integer commentId, Integer userId) throws Exception;
}
