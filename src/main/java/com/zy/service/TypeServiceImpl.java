package com.zy.service;






import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zy.NotFoundException;
import com.zy.dao.TypeRepository;
import com.zy.po.Type;
@Service
public class TypeServiceImpl implements TypeService{

	@Override
	public Type getTypeByName(String name) {
		// TODO Auto-generated method stub
		return typeRepository.findByName(name);
	}
	@Autowired
	private TypeRepository typeRepository;
	private Pageable pageable;
	
	
	@Transactional
	@Override
	public Type saveType(Type type) {
		// TODO Auto-generated method stub
		return typeRepository.save(type);
	}
	@Transactional
	@Override
	public Type getType(Long id) {
		// TODO Auto-generated method stub
		return typeRepository.findById(id).get();
	}
	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {
		// TODO Auto-generated method stub
		return typeRepository.findAll(pageable);
	}
	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		// TODO Auto-generated method stub
		Type t=typeRepository.findById(id).get();
		if(t==null) {
			throw new NotFoundException("不存在该类型");
		}
		BeanUtils.copyProperties(type, t);
		
		return typeRepository.save(t);
	}
	@Transactional
	@Override
	public void deleteType(Long id) {
		// TODO Auto-generated method stub
		typeRepository.deleteById(id);
	}
	@Override
	public List<Type> listType() {
		// TODO Auto-generated method stub
		return typeRepository.findAll();
	}
	@Override
	public List<Type> listTypeTop(Integer size) {
		// TODO Auto-generated method stub
        /*Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);*/
		/*Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable=new PageRequest(0, size, sort);*/
		PageRequest.of(0, size, Sort.Direction.DESC, "blogs.size");
		//Pageable pageable=new PageRequest();
		return typeRepository.findTop(pageable);
		
	}

}
