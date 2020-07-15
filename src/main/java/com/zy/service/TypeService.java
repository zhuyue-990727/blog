package com.zy.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zy.po.Type;

public interface TypeService {
Type saveType(Type type);

Type getTypeByName(String name);

Type getType(Long id);

Page<Type> listType(Pageable pageable);

Type updateType(Long id,Type type);

void deleteType(Long id);

List<Type> listType();

List<Type> listTypeTop(Integer size);

}
