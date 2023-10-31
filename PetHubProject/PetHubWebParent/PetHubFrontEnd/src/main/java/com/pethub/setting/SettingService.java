package com.pethub.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pethub.common.entity.Currency;
import com.pethub.common.entity.setting.Setting;
import com.pethub.common.entity.setting.SettingCategory;

@Service
public class SettingService {
	@Autowired
	private SettingRepository settingRepo;

	public List<Setting> getGeneralSettings() {

		return settingRepo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
	}

	public EmailSettingBag getEmailSettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.MAIL_SERVER);
		settings.addAll(settingRepo.findByCategory(SettingCategory.MAIL_TEMPLATES));

		return new EmailSettingBag(settings);
	}

	public CurrencySettingBag getCurrencySettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.CURRENCY);
		return new CurrencySettingBag(settings);
	}

	public PaymentSettingBag getPaymentSettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.PAYMENT);
		return new PaymentSettingBag(settings);
	}

}
