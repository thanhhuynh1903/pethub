package com.pethub.shipping;

import org.springframework.data.repository.CrudRepository;

import com.pethub.common.entity.Country;
import com.pethub.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

	public ShippingRate findByCountryAndProvince(Country country, String province);
}
