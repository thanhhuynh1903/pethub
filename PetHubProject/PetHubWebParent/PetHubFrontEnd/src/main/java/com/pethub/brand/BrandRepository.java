package com.pethub.brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pethub.common.entity.Brand;
import com.pethub.common.entity.Category;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
 public List<Brand> findByCategoriesContaining(Category category);

  public Brand findByName(String name);

}
