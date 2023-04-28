package com.ahmetaksunger.BlogAPI.repository;

import com.ahmetaksunger.BlogAPI.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
