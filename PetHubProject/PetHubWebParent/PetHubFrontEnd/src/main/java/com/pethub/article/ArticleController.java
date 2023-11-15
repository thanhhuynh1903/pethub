package com.pethub.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pethub.common.entity.Article;

@Controller
public class ArticleController {
	@Autowired
	private ArticleRepository repo;

	@GetMapping("/a/{alias}")
	public String viewArticle(@PathVariable("alias") String alias, Model model) {
		Article article = repo.findByAlias(alias);

		if (article == null) {
			return "error/404";
		}

		model.addAttribute("article", article);
		return "articles/article";
	}

	@GetMapping("/articles_page")
	public String viewAllArticle(Model model) {
		List<Article> listArticles = repo.findAll();
		model.addAttribute("listArticles", listArticles);
		model.addAttribute("currentPage", "articles_page");

		return "articles/articles_page";
	}

	@GetMapping("/searchArticle")
	public String searchArticles(@RequestParam("keyword") String keyword, Model model) {
		List<Article> listArticles = repo.searchArticles(keyword);
		model.addAttribute("listArticles", listArticles);
		model.addAttribute("searchKeyword", keyword);
    model.addAttribute("currentPage", "articles_page");

		return "articles/articles_page";
	}

}
