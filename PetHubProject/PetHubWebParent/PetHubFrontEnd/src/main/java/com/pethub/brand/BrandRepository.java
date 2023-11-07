package com.pethub.brand;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pethub.common.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
