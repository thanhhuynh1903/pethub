package com.pethub.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pethub.common.entity.Setting;
import com.pethub.common.entity.SettingCategory;

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
}
