package com.pethub.admin.category;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pethub.common.entity.Category;

public interface CategoryRepository
		extends PagingAndSortingRepository<Category, Integer>, CrudRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories(Sort sort);

	public Category findByName(String name);

	public Category findByAlias(String alias);

	public Long countById(Integer id);

	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id= ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
