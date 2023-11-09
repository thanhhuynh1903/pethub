package com.pethub.product;

import java.util.List;

import java.util.NoSuchElementException;

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

	public Product getProduct(Integer id) throws ProductNotFoundException {
		try {
			Product product = repo.findById(id).get();
			return product;
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
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
		} else if ("desc".equals(sortDir)) {
			return repo.listByCategoryPriceDesc(categoryId, categoryIdMatch, pageable);
		}else if("top_sales".equals(sortDir)){
			return repo.listByCategoryTopSales(categoryId, categoryIdMatch, pageable);		
		} else {
			return repo.listByCategory(categoryId, categoryIdMatch, pageable);
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
		}else if("top_sales".equals(sortDir)){
			return repo.searchTopSales(keyword, pageable);
		} else {
			return repo.search(keyword, pageable);
		}
	}

	public List<Product> getAllProducts() {
		return (List<Product>) repo.findAll();
	}

	public List<Product> get6Products() {
		return (List<Product>) repo.findTop6ByOrderByDiscountDesc();
	}

	public List<Product> get6BestSaleProducts() {
		return (List<Product>) repo.findTop6ByOrderByQuantityDesc();
	}

	public Page<Product> getProductsByBrandName(String brandName, int pageNum, String sortDir) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);

		if ("asc".equals(sortDir)) {
			return repo.findByBrandNameOrderByPriceAsc(brandName, pageable);
		} else if ("desc".equals(sortDir)) {
			return repo.findByBrandNameOrderByPriceDesc(brandName, pageable);
		} else if ("latest".equals(sortDir)) {
			return repo.findByBrandNameOrderByCreatedDateDesc(brandName, pageable);
		}else if("top_sales".equals(sortDir)){
			return repo.listByBrandTopSales(brandName, pageable);
		} else {
			return repo.findByBrandName(brandName, pageable);
		}
	}

}
