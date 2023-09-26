package com.pethub.admin.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pethub.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
