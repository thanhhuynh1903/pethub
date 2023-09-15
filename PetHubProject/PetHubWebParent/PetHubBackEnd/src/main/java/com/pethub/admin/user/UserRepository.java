package com.pethub.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pethub.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}
