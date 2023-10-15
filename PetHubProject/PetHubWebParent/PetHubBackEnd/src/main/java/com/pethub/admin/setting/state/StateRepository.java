package com.pethub.admin.setting.state;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pethub.common.entity.Country;
import com.pethub.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {

	public List<State> findByCountryOrderByNameAsc(Country country);
}
