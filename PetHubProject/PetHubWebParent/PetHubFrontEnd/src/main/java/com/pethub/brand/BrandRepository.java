package com.pethub.brand;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pethub.common.entity.Brand;
import com.pethub.common.entity.Category;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {

  public List<Brand> findByCategoriesContaining(Category category);

  public Brand findByName(String name);

}
