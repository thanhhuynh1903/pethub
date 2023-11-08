package com.pethub.brand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pethub.common.entity.Brand;
import com.pethub.common.entity.Category;

@Service
public class BrandService {

  @Autowired
  private BrandRepository repo;

  public List<Brand> listAllBrands() {
    return (List<Brand>) repo.findAll();
  }

//  public List<Brand> getBrandsByCategory(Category category) {
//    return repo.findByCategoriesContaining(category);
//  }

  public Brand getBrandByName(String name) {
    return repo.findByName(name);
  }

}
