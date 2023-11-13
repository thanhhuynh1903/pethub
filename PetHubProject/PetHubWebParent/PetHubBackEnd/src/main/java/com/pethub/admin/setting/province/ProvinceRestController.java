package com.pethub.admin.setting.province;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pethub.common.entity.Country;
import com.pethub.common.entity.Province;
import com.pethub.common.entity.ProvinceDTO;

@RestController
public class ProvinceRestController {

	@Autowired
	private ProvinceRepository repo;

	@GetMapping("/Provinces/list_by_country/{id}")
	public List<ProvinceDTO> listByCountry(@PathVariable("id") Integer countryId) {
		List<Province> listProvinces = repo.findByCountryOrderByNameAsc(new Country(countryId));
		List<ProvinceDTO> result = new ArrayList<>();

		for (Province Province : listProvinces) {
			result.add(new ProvinceDTO(Province.getId(), Province.getName()));
		}

		return result;
	}

	@PostMapping("/Provinces/save")
	public String save(@RequestBody Province Province) {
		Province savedProvince = repo.save(Province);
		return String.valueOf(savedProvince.getId());
	}

	@DeleteMapping("/Provinces/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
}
