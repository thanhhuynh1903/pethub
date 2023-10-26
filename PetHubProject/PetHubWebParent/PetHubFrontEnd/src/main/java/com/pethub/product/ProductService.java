package com.pethub.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pethub.common.entity.product.Product;
import com.pethub.common.exception.ProductNotFoundException;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 12;
	public static final int SEARCH_RESULTS_PER_PAGE = 12;

	@Autowired
	private ProductRepository repo;

	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

		return repo.listByCategory(categoryId, categoryIdMatch, pageable);
	}

	public Product getProduct(String alias) throws ProductNotFoundException {
		Product product = repo.findByAlias(alias);
		if (product == null) {
			throw new ProductNotFoundException("Could not find any product with alias " + alias);
		}

		return product;
	}

	public Page<Product> search(String keyword, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);
	}

	public Page<Product> listByCategory1(int pageNum, Integer categoryId, String sortDir) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

		if (sortDir.equals("latest")) {
			return repo.listByCategoryLatest(categoryId, categoryIdMatch, pageable);
		} else if (sortDir.equals("asc")) {
			return repo.listByCategoryPriceAsc(categoryId, categoryIdMatch, pageable);
		} else {
			return repo.listByCategoryPriceDesc(categoryId, categoryIdMatch, pageable);
		}
	}

	public Page<Product> search1(String keyword, int pageNum, String sortDir) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		if ("asc".equals(sortDir)) {
			return repo.searchSortPriceAsc(keyword, pageable);
		} else if ("desc".equals(sortDir)) {
			return repo.searchSortPriceDesc(keyword, pageable);
		} else if ("latest".equals(sortDir)) {
			return repo.searchSortByLatest(keyword, pageable);
		} else {
			return repo.search(keyword, pageable);
		}
	}
}
