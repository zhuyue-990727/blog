package com.zy.service;

import com.zy.NotFoundException;
import com.zy.dao.BlogRepository;
import com.zy.po.Blog;
import com.zy.po.Type;
import com.zy.util.MarkdownUtils;
import com.zy.util.MyBeanUtils;
import com.zy.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;
	private Pageable pageable;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }


    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(blog);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        
        
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findAll(pageable);
	}


	@Override
	public List<Blog> listRecommendBlogTop(Integer size) {
		// TODO Auto-generated method stub
		/*Sort sort =new Sort(Sort.Direction.DESC,"updateTime");
		Pageable pageable=new PageRequest(0,size,sort);*/
		PageRequest.of(0, size, Sort.Direction.DESC, "updateTime");
		return blogRepository.findTop(pageable);
	}


	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findByQuery(query, pageable);
	}


	@Transactional
	@Override
	public Blog getandConvert(Long id) {
		// TODO Auto-generated method stub
		Blog blog=blogRepository.findById(id).orElse(null);
		if(blog==null) {
			throw new NotFoundException("该博客不存在");
		}
		Blog b=new Blog();
		BeanUtils.copyProperties(blog, b);
		String content=b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);

		return b;
	}


	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findAll(new Specification<Blog>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Join<Object, Object> join = root.join("tags");
				cb.equal(join.get("id"),tagId);
				return null;
			}
			
		},pageable);
	}


	@Override
	public Map<String, List<Blog>> archiveBlog() {
		// TODO Auto-generated method stub
		List<String> years=blogRepository.findGroupByYear();
		Map<String, List<Blog>>  map=new HashMap<>();
		for(String year:years) {
			map.put(year,blogRepository.findByYear(year));
		}
		return map;
	}


	@Override
	public Long countBlog() {
		// TODO Auto-generated method stub
		return blogRepository.count();
	}
}
