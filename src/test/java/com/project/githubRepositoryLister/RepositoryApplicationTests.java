package com.project.githubRepositoryLister;

import com.project.githubRepositoryLister.Repository.RepositoryController;
import com.project.githubRepositoryLister.Repository.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RepositoryApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void testRepositoryServiceBean() {
		assertThat(applicationContext.getBean(RepositoryService.class)).isNotNull();
	}

	@Test
	void testRepositoryControllerBean() {
		assertThat(applicationContext.getBean(RepositoryController.class)).isNotNull();
	}
}
