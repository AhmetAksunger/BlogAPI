package com.ahmetaksunger.BlogAPI.repository;

import com.ahmetaksunger.BlogAPI.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    List<Blog> findAllByOrderByTitle();

    List<Blog> findAllByOrderByCreatedAtDesc();

    List<Blog> findAllByOrderByUpdatedAtDesc();

    List<Blog> findAllByUserId(Long userId);

    //IgnoreCase makes it case independent
    List<Blog> findAllByTitleContainingIgnoreCase(String title);

    List<Blog> findAllByUserUsernameContainingIgnoreCase(String username);

}
