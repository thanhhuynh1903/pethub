package com.pethub.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useRepresentation;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.pethub.common.entity.Role;
import com.pethub.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE) // run the test on the real db
@Rollback(false) // commit the changes to the underlying db after each test
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userHuyennt = new User("thuonghuyennth.8499@gmail.com", "123", "Thuong Huyen", "Nguyen");
		userHuyennt.addRole(roleAdmin);

		User savedUser = repo.save(userHuyennt);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userThanhhb = new User("thanhhuynh@gmail.com", "123", "Ba Thanh", "Huynh");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userThanhhb.addRole(roleEditor);
		userThanhhb.addRole(roleAssistant);

		User savedUser = repo.save(userThanhhb);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserById() {
		User userHuyennt = repo.findById(1).get();
		System.out.println(userHuyennt);
		assertThat(userHuyennt).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userHuyennt = repo.findById(1).get();
		userHuyennt.setEnabled(true);
		userHuyennt.setEmail("huyenntse161803@fpt.edu.vn");

		repo.save(userHuyennt);
	}

	@Test
	public void testUpdateUserRoles() {
		User userThanh = repo.findById(3).get();
		Role roleEditor = new Role(3);
		Role roleSaleperson = new Role(2);
		userThanh.getRoles().remove(roleEditor);
		userThanh.addRole(roleSaleperson);

		repo.save(userThanh);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);

	}

	@Test
	public void testGetUserByEmail() {
		String email = "huyenntse161803@fpt.edu.vn";
		User user = repo.getUserByEmail(email);

		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);

		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisabledUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}

	@Test
	public void testEnabledUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}

	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
}