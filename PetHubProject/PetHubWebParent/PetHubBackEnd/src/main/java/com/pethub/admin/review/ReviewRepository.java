package com.pethub.admin.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pethub.admin.paging.SearchRepository;
import com.pethub.common.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
