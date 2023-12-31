package com.pethub.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pethub.common.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query("SELECT a FROM Article a WHERE a.alias = ?1")
	public Article findByAlias(String alias);

	List<Article> findAll();

	@Query("SELECT a FROM Article a WHERE a.alias LIKE %:keyword%")
	public List<Article> searchArticles(String keyword);
}
