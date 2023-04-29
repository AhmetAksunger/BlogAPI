package com.ahmetaksunger.BlogAPI.repository;

import com.ahmetaksunger.BlogAPI.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    List<Blog> findAllByUserId(Long userId);

}
