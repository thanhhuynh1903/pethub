package com.pethub.product;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pethub.brand.BrandService;
import com.pethub.ControllerHelper;
import com.pethub.category.CategoryService;
import com.pethub.common.entity.Brand;
import com.pethub.common.entity.Category;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.Question;
import com.pethub.common.entity.Review;
import com.pethub.common.entity.product.Product;
import com.pethub.common.exception.BrandNotFoundException;
import com.pethub.common.exception.CategoryNotFoundException;
import com.pethub.common.exception.ProductNotFoundException;
import com.pethub.question.QuestionService;
import com.pethub.question.vote.QuestionVoteService;
import com.pethub.review.ReviewService;
import com.pethub.review.vote.ReviewVoteService;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ReviewVoteService reviewVoteService;
	@Autowired
	private QuestionVoteService questionVoteService;
	@Autowired
	private ControllerHelper controllerHelper;
	@Autowired
	private QuestionService questionService;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
			Model model) {
		return viewCategoryByPage(alias, 1, alias, model);
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum,
			Model model) {
		try {
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);

			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model,
			HttpServletRequest request) {

		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			List<Question> listQuestions = questionService.getTop3VotedQuestions(product.getId());
			Page<Review> listReviews = reviewService.list3MostVotedReviewsByProduct(product);

			Customer customer = controllerHelper.getAuthenticatedCustomer(request);

			if (customer != null) {
				boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
				reviewVoteService.markReviewsVotedForProductByCustomer(listReviews.getContent(), product.getId(),
						customer.getId());
				questionVoteService.markQuestionsVotedForProductByCustomer(listQuestions, product.getId(),
						customer.getId());

				if (customerReviewed) {
					model.addAttribute("customerReviewed", customerReviewed);
				} else {
					boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
					model.addAttribute("customerCanReview", customerCanReview);
				}
			}

			int numberOfQuestions = questionService.getNumberOfQuestions(product.getId());
			int numberOfAnsweredQuestions = questionService.getNumberOfAnsweredQuestions(product.getId());

			model.addAttribute("listQuestions", listQuestions);
			model.addAttribute("numberOfQuestions", numberOfQuestions);
			model.addAttribute("numberOfAnsweredQuestions", numberOfAnsweredQuestions);

			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getShortName());

			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}

	@GetMapping("/search")
	public String searchFirstPage(String keyword, @RequestParam(defaultValue = "default") String sortDir, Model model) {
		return searchByPage(keyword, sortDir, 1, model);
	}

	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@RequestParam String keyword, @RequestParam String sortDir,
			@PathVariable("pageNum") int pageNum, Model model) {
		Page<Product> pageProducts = productService.search1(keyword, pageNum, sortDir);
		List<Product> listResult = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResult", listResult);
		model.addAttribute("sortDir", sortDir);

		return "product/search_result";
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(defaultValue = "asc") String sortDir,
			Model model) {
		try {
			Category category = categoryService.getCategory(alias);
			boolean hasChildCategories = categoryService.hasChildCategories(category);
			List<Brand> brands = hasChildCategories ? null : brandService.getBrandsByCategory(category);

			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory1(pageNum, category.getId(), sortDir);
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);
			model.addAttribute("brands", brands);
			model.addAttribute("hasChildCategories", hasChildCategories);
			model.addAttribute("sortDir", sortDir);

			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

	@GetMapping("/b/{brandName}/products/page/{pageNum}")
	public String viewProductsByBrand(@PathVariable("brandName") String brandName,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(defaultValue = "asc") String sortDir,
			Model model) {
		Brand brand = brandService.getBrandByName(brandName); // fetch the brand
		Page<Product> page = productService.getProductsByBrandName(brandName, pageNum, sortDir);
		List<Product> products = page.getContent();

		long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("pageTitle", brand.getName());
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("products", products);
		model.addAttribute("categories", brand.getCategories()); // add categories to the model
		model.addAttribute("brand", brand);

		return "product/product_brand";
	}

}