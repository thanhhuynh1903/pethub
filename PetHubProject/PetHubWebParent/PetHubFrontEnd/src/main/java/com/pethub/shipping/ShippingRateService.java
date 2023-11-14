package com.pethub.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pethub.common.entity.Address;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.ShippingRate;

@Service
public class ShippingRateService {

	@Autowired
	private ShippingRateRepository repo;

	public ShippingRate getShippingRateForCustomer(Customer customer) {
		String province = customer.getProvince();
		if (province == null || province.isEmpty()) {
			province = customer.getCity();
		}

		return repo.findByCountryAndProvince(customer.getCountry(), province);
	}

	public ShippingRate getShippingRateForAddress(Address address) {
		String province = address.getProvince();
		if (province == null || province.isEmpty()) {
			province = address.getCity();
		}

		return repo.findByCountryAndProvince(address.getCountry(), province);
	}
}
