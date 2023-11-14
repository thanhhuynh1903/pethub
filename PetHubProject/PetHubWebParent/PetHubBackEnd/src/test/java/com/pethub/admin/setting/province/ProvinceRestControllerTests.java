package com.pethub.admin.setting.province;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pethub.admin.setting.country.CountryRepository;
import com.pethub.common.entity.Country;
import com.pethub.common.entity.Province;

@SpringBootTest
@AutoConfigureMockMvc
public class ProvinceRestControllerTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CountryRepository countryRepo;

	@Autowired
	ProvinceRepository ProvinceRepo;

	@Test
	@WithMockUser(username = "huyenntse161803", password = "12345678", roles = "Admin")
	public void testListByCountries() throws Exception {
		Integer countryId = 2;
		String url = "/Provinces/list_by_country/" + countryId;

		MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andDo(print()).andReturn();

		String jsonResponse = result.getResponse().getContentAsString();
		Province[] Provinces = objectMapper.readValue(jsonResponse, Province[].class);

		assertThat(Provinces).hasSizeGreaterThan(1);
	}

	@Test
	@WithMockUser(username = "huyenntse161803", password = "12345678", roles = "Admin")
	public void testCreateProvince() throws Exception {
		String url = "/Provinces/save";
		Integer countryId = 2;
		Country country = countryRepo.findById(countryId).get();
		Province Province = new Province("Arizona", country);

		MvcResult result = mockMvc.perform(
				post(url).contentType("application/json").content(objectMapper.writeValueAsString(Province)).with(csrf()))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		Integer ProvinceId = Integer.parseInt(response);
		Optional<Province> findById = ProvinceRepo.findById(ProvinceId);

		assertThat(findById.isPresent());
	}

	@Test
	@WithMockUser(username = "huyenntse161803", password = "12345678", roles = "Admin")
	public void testUpdateProvince() throws Exception {
		String url = "/Provinces/save";
		Integer ProvinceId = 9;
		String ProvinceName = "Alaska";

		Province Province = ProvinceRepo.findById(ProvinceId).get();
		Province.setName(ProvinceName);

		mockMvc.perform(
				post(url).contentType("application/json").content(objectMapper.writeValueAsString(Province)).with(csrf()))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(String.valueOf(ProvinceId)));

		Optional<Province> findById = ProvinceRepo.findById(ProvinceId);
		assertThat(findById.isPresent());

		Province updatedProvince = findById.get();
		assertThat(updatedProvince.getName()).isEqualTo(ProvinceName);

	}

	@Test
	@WithMockUser(username = "huyenntse161803", password = "12345678", roles = "Admin")
	public void testDeleteProvince() throws Exception {
		Integer ProvinceId = 6;
		String uri = "/Provinces/delete/" + ProvinceId;

		mockMvc.perform(delete(uri).with(csrf())).andExpect(status().isOk());

		Optional<Province> findById = ProvinceRepo.findById(ProvinceId);

		assertThat(findById).isNotPresent();
	}
}
