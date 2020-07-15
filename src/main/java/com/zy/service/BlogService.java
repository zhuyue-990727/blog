package com.zy.service;

import com.zy.po.Blog;
import com.zy.vo.BlogQuery;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BlogService {

    Blog getBlog(Long id);
    
    Blog getandConvert(Long id);

    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    
    Page<Blog> listBlog(Pageable pageable);
    
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Map<String,List<Blog>> archiveBlog();
    
    Page<Blog> listBlog(String query,Pageable pageable);
    
    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
    
    Long countBlog();
}
