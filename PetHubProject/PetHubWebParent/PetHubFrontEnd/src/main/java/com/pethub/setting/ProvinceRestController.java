package com.pethub.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pethub.common.entity.Country;
import com.pethub.common.entity.Province;
import com.pethub.common.entity.ProvinceDTO;

@RestController
public class ProvinceRestController {

	@Autowired
	private ProvinceRepository repo;

	@GetMapping("/settings/list_provinces_by_country/{id}")
	public List<ProvinceDTO> listByCountry(@PathVariable("id") Integer countryId) {
		List<Province> listProvinces = repo.findByCountryOrderByNameAsc(new Country(countryId));
		List<ProvinceDTO> result = new ArrayList<>();

		for (Province province : listProvinces) {
			result.add(new ProvinceDTO(province.getId(), province.getName()));
		}

		return result;
	}
}
