package com.ahmetaksunger.BlogAPI.repository;

import com.ahmetaksunger.BlogAPI.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog,Long> {
}
