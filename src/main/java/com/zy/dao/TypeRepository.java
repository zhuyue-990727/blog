package com.zy.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zy.po.Type;

public interface TypeRepository extends JpaRepository<Type,Long>{

	
	Type findByName(String name);

	Type save(Optional<Type> t);

	@Query("select t from Type t")
	List<Type> findTop(Pageable pageable);
	
}
