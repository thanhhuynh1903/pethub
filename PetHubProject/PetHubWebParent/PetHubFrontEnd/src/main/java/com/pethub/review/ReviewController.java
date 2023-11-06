package com.pethub.review;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pethub.ControllerHelper;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.Review;
import com.pethub.common.entity.product.Product;
import com.pethub.common.exception.ProductNotFoundException;
import com.pethub.common.exception.ReviewNotFoundException;
import com.pethub.product.ProductService;

@Controller
public class ReviewController {
	private String defaultRedirectURL = "redirect:/reviews/page/1?sortField=reviewTime&sortDir=desc";
	
	@Autowired private ReviewService reviewService;
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private ProductService productService;
	
	@GetMapping("/reviews")
	public String listFirstPage(Model model) {
		return defaultRedirectURL;
	}
	
	@GetMapping("/reviews/page/{pageNum}") 
	public String listReviewsByCustomerByPage(Model model, HttpServletRequest request,
							@PathVariable(name = "pageNum") int pageNum,
							String keyword, String sortField, String sortDir) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		Page<Review> page = reviewService.listByCustomerByPage(customer, keyword, pageNum, sortField, sortDir);		
		List<Review> listReviews = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("moduleURL", "/reviews");
		
		model.addAttribute("listReviews", listReviews);

		long startCount = (pageNum - 1) * ReviewService.REVIEWS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + ReviewService.REVIEWS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		return "reviews/reviews_customer";
	}
	
	@GetMapping("/reviews/detail/{id}")
	public String viewReview(@PathVariable("id") Integer id, Model model, 
			RedirectAttributes ra, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		try {
			Review review = reviewService.getByCustomerAndId(customer, id);
			model.addAttribute("review", review);
			
			return "reviews/review_detail_modal";
		} catch (ReviewNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return defaultRedirectURL;		
		}
	}
	
	
	@GetMapping("/write_review/product/{productId}")
	public String showViewForm(@PathVariable("productId") Integer productId, Model model,
			HttpServletRequest request) {
		
		Review review = new Review();
		
		Product product = null;
		
		try {
			product = productService.getProduct(productId);
		} catch (ProductNotFoundException ex) {
			return "error/404";
		}
		
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
		
		if (customerReviewed) {
			model.addAttribute("customerReviewed", customerReviewed);
		} else {
			boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
			
			if (customerCanReview) {
				model.addAttribute("customerCanReview", customerCanReview);				
			} else {
				model.addAttribute("NoReviewPermission", true);
			}
		}		
		
		model.addAttribute("product", product);
		model.addAttribute("review", review);
		
		return "reviews/review_form";
	}
	
	@PostMapping("/post_review")
	public String saveReview(Model model, Review review, Integer productId, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		Product product = null;
		
		try {
			product = productService.getProduct(productId);
		} catch (ProductNotFoundException ex) {
			return "error/404";
		}
		
		review.setProduct(product);
		review.setCustomer(customer);
		
		Review savedReview = reviewService.save(review);
		
		model.addAttribute("review", savedReview);
		
		return "reviews/review_done";
	}
}
