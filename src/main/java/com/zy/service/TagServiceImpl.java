package com.zy.service;

import com.zy.NotFoundException;
import com.zy.dao.TagRepository;
import com.zy.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
	private Pageable pageable;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        /*Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);*/
		PageRequest.of(0, size, Sort.Direction.DESC, "blogs.size");
        return tagRepository.findTop(pageable);
    }


    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        return tagRepository.findAllById(convertToList(ids));
    }

    @SuppressWarnings("deprecation")
	private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
    
    /*public void convertToList(String ids) {
    	String idsWithNoBlank = ids.replaceAll(" +", "");
        // 其次使用分隔符将代码字符分开
        String[] idsNoBlankArray = idsWithNoBlank.split(",");
        // 使用 org.apache.commons.beanutils 提供的工具类进行类型转换
        // gradle 引入：compile group: 'commons-beanutils', name: 'commons-beanutils', version: '1.9.3'
        Long[] convert = (Long[]) ConvertUtils.convert(idsNoBlankArray, Long.class);
        // 然后转换成为 list
        List<Long> idsLong = Arrays.asList(convert);
        /*log.error(idsWithNoBlank);
        for (Long m : idsLong) {
          log.info(m);
        }*/


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findById(id).orElse(tag);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }



    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
