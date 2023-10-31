package com.pethub.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pethub.common.entity.product.Product;

public interface ProductRepository
		extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)" + " ORDER BY p.name ASC")
	public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);

	public Product findByAlias(String alias);

	@Query(value = "SELECT * FROM products p ORDER BY p.discount_percent DESC LIMIT 6", nativeQuery = true)
	public List<Product> findTop6ByOrderByDiscountDesc();

	@Query(value = "SELECT * FROM products p ORDER BY p.price ASC LIMIT 6", nativeQuery = true)
	public List<Product> findTop6ByOrderByPriceAsc();

	List<Product> findAll();

	@Query(value = "SELECT * FROM products WHERE enabled = true AND "
			+ "(name LIKE %?1% OR short_description LIKE %?1% OR full_description LIKE %?1%)", nativeQuery = true)
	public Page<Product> search(String keyword, Pageable pageable);

	// 1.Sorting by Cate
	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY (p.price * (1 - p.discountPercent / 100)) ASC")
	public Page<Product> listByCategoryPriceAsc(Integer categoryId, String categoryIDMatch, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY (p.price * (1 - p.discountPercent / 100)) DESC")
	public Page<Product> listByCategoryPriceDesc(Integer categoryId, String categoryIDMatch, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY p.createdTime DESC")
	public Page<Product> listByCategoryLatest(Integer categoryId, String categoryIDMatch, Pageable pageable);

	// 2.Sorting after Search
	@Query(value = "SELECT * FROM products WHERE enabled = true AND "
			+ "(name LIKE %?1% OR short_description LIKE %?1% OR full_description LIKE %?1%)"
			+ " ORDER BY (price * (1 - discount_percent / 100)) ASC", nativeQuery = true)
	public Page<Product> searchSortPriceAsc(String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM products WHERE enabled = true AND "
			+ "(name LIKE %?1% OR short_description LIKE %?1% OR full_description LIKE %?1%)"
			+ " ORDER BY (price * (1 - discount_percent / 100)) DESC", nativeQuery = true)
	public Page<Product> searchSortPriceDesc(String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM products WHERE enabled = true AND "
			+ "(name LIKE %?1% OR short_description LIKE %?1% OR full_description LIKE %?1%)"
			+ " ORDER BY created_time DESC", nativeQuery = true)
	public Page<Product> searchSortByLatest(String keyword, Pageable pageable);

	// 3.Brand
	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.brand.name = ?1")
	public Page<Product> findByBrandName(String brandName, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.brand.name = ?1 ORDER BY p.price ASC")
	public Page<Product> findByBrandNameOrderByPriceAsc(String brandName, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.brand.name = ?1 ORDER BY p.price DESC")
	public Page<Product> findByBrandNameOrderByPriceDesc(String brandName, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.enabled = true AND p.brand.name = ?1 ORDER BY p.createdTime DESC")
	public Page<Product> findByBrandNameOrderByCreatedDateDesc(String brandName, Pageable pageable);

}
