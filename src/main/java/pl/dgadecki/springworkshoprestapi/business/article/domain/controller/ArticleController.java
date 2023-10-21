package pl.dgadecki.springworkshoprestapi.business.article.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dgadecki.springworkshoprestapi.business.article.domain.service.ArticleService;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public FindAllArticlesResponse findAllArticles() {
        return FindAllArticlesResponse.fromArticlesList(articleService.findAllArticles());
    }

    @PostMapping
    public CreateArticleResponse createArticle(@RequestBody CreateArticleRequest createArticleRequest) {
        Article createdArticle = articleService.saveArticle(createArticleRequest.toArticle());
        return CreateArticleResponse.fromArticle(createdArticle);
    }

    @PutMapping("/{id}")
    public UpdadeArticleResponse updateArticle(@PathVariable("id") Long articleId,
                              @RequestBody UpdateArticleRequest updateArticleRequest) {
        Article articleToUpdate = articleService.updateArticle(articleId, updateArticleRequest.toArticle());
        return UpdadeArticleResponse.fromArticle(articleToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteArticle(articleId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> findArticle(@PathVariable("id") Long articleId) {
        return new ResponseEntity<>(articleService.fetchArticleById(articleId),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> findArticleByNameAndDescription(
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="description", required = false) String description) {
        return new ResponseEntity<>(
                articleService.findArticleByNameAndDescription(name, description),
                HttpStatus.OK);
    }

    @GetMapping("/producer")
    public ResponseEntity<List<Article>> findArticleByProducer(
            @RequestParam(name="producer") String producer) {
        return new ResponseEntity<>(articleService.findByProducer(producer),
                HttpStatus.OK);
    }


}
