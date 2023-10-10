package com.pethub.admin.setting;

import org.springframework.data.repository.CrudRepository;

import com.pethub.common.entity.Setting;

public interface SettingRepository extends CrudRepository<Setting, String> {

}
