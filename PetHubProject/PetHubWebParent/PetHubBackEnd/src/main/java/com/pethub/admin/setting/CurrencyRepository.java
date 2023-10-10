package com.pethub.admin.setting;

import org.springframework.data.repository.CrudRepository;

import com.pethub.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}
