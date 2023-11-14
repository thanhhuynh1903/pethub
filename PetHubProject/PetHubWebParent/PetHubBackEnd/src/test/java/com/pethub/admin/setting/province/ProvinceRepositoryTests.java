package com.pethub.admin.setting.province;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import com.pethub.common.entity.Country;
import com.pethub.common.entity.Province;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProvinceRepositoryTests {

	@Autowired
	private ProvinceRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProvincesInIndia() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);

		// Province Province = repo.save(new Province("Karnataka", country));
		// Province Province = repo.save(new Province("Punjab", country));
		// Province Province = repo.save(new Province("Uttar Pradesh", country));
		Province Province = repo.save(new Province("West Bengal", country));

		assertThat(Province).isNotNull();
		assertThat(Province.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateProvincesInUS() {
		Integer countryId = 2;
		Country country = entityManager.find(Country.class, countryId);

//		Province Province = repo.save(new Province("California", country));
//		Province Province = repo.save(new Province("Texas", country));
//		Province Province = repo.save(new Province("New York", country));
		Province Province = repo.save(new Province("Washington", country));

		assertThat(Province).isNotNull();
		assertThat(Province.getId()).isGreaterThan(0);
	}

	@Test
	public void testListProvincesByCountry() {
		Integer countryId = 2;
		Country country = entityManager.find(Country.class, countryId);
		List<Province> listProvinces = repo.findByCountryOrderByNameAsc(country);

		listProvinces.forEach(System.out::println);

		assertThat(listProvinces.size()).isGreaterThan(0);
	}

	@Test
	public void testUpdateProvince() {
		Integer ProvinceId = 3;
		String ProvinceName = "Tamil Nadu";
		Province Province = repo.findById(ProvinceId).get();

		Province.setName(ProvinceName);
		Province updatedProvince = repo.save(Province);

		assertThat(updatedProvince.getName()).isEqualTo(ProvinceName);
	}

	@Test
	public void testGetProvince() {
		Integer ProvinceId = 1;
		Optional<Province> findById = repo.findById(ProvinceId);
		assertThat(findById.isPresent());
	}

	@Test
	public void testDeleteProvince() {
		Integer ProvinceId = 8;
		repo.deleteById(ProvinceId);

		Optional<Province> findById = repo.findById(ProvinceId);
		assertThat(findById.isEmpty());
	}
}
