package com.pethub.setting;

import org.springframework.data.repository.CrudRepository;

import com.pethub.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}
