package com.pethub.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.pethub.common.entity","com.pethub.admin.user"})
public class PetHubBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetHubBackEndApplication.class, args);
	}

}
