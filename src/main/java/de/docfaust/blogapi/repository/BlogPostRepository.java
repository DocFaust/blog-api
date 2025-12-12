package de.docfaust.blogapi.repository;

import de.docfaust.blogapi.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {}
