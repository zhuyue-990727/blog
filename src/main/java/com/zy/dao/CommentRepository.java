package com.zy.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zy.po.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{
List<Comment> findByBlogIdAndParentCommentNull(Long blogId,Sort sort);
}
