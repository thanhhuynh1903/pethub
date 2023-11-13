package com.pethub.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pethub.common.entity.Country;
import com.pethub.common.entity.Province;

public interface ProvinceRepository extends CrudRepository<Province, Integer> {

	public List<Province> findByCountryOrderByNameAsc(Country country);
}
