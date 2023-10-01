package com.pethub.admin.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pethub.common.entity.Product;

public interface ProductRepository
		extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

}
