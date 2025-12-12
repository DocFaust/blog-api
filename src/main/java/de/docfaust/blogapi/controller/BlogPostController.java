package de.docfaust.blogapi.controller;

import de.docfaust.blogapi.dto.CreatePostRequest;
import de.docfaust.blogapi.entity.BlogPost;
import de.docfaust.blogapi.repository.BlogPostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class BlogPostController {

    private final BlogPostRepository repo;

    public BlogPostController(BlogPostRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<BlogPost> create(@RequestBody CreatePostRequest req) {
        if (req.username() == null || req.username().isBlank()
                || req.title() == null || req.title().isBlank()
                || req.content() == null || req.content().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        BlogPost saved = repo.save(new BlogPost(req.username(), req.title(), req.content()));
        return ResponseEntity.created(URI.create("/posts/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<BlogPost> list() {
        return repo.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
