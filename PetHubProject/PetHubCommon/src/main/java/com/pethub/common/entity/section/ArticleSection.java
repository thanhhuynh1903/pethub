package com.pethub.common.entity.section;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.pethub.common.entity.Article;
import com.pethub.common.entity.IdBasedEntity;

@Entity
@Table(name = "sections_articles")
public class ArticleSection extends IdBasedEntity {

	@Column(name = "article_order")
	private int articleOrder;

	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article article;

	public int getArticleOrder() {
		return articleOrder;
	}

	public void setArticleOrder(int articleOrder) {
		this.articleOrder = articleOrder;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
