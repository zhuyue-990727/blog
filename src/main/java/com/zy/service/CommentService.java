package com.zy.service;

import java.util.List;

import com.zy.po.Comment;

public interface CommentService {
List<Comment> listCommentByblogId(Long blogId);
Comment saveComment(Comment comment);
}
