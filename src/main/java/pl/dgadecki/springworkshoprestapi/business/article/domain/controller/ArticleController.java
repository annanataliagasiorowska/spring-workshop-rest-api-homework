package pl.dgadecki.springworkshoprestapi.business.article.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dgadecki.springworkshoprestapi.business.article.domain.service.ArticleService;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;
import pl.dgadecki.springworkshoprestapi.business.article.dto.api.*;
import pl.dgadecki.springworkshoprestapi.errorhandling.ErrorResponse;

@Tag(name = "Article API", description = "All operations available for articles")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "The articles were successfully found", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FindAllArticlesResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        })
})
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @Operation(summary = "Find all articles", description = "Returns all articles from the system")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllArticlesResponse findAllArticles() {
        return FindAllArticlesResponse.fromArticlesList(articleService.findAllArticles());
    }

    @Operation(summary = "Create article", description = "Given data from user creates new article in the system")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateArticleResponse createArticle(@Parameter(description = "Data passed to create article") @RequestBody CreateArticleRequest createArticleRequest) {
        Article createdArticle = articleService.saveArticle(createArticleRequest.toArticle());
        return CreateArticleResponse.fromArticle(createdArticle);
    }

    @Operation(summary = "Find article by id", description = "Given a valid id it returns proper article, otherwise error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FindArticleResponse findArticle(@Parameter(description = "Identifier of the article to be found") @PathVariable("id") Long articleId) {
        return FindArticleResponse.fromArticle(articleService.fetchArticleById(articleId));
    }

    @Operation(summary = "Update article", description = "Updates article found by id using data from request")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateArticleResponse updateArticle(@Parameter(description = "Identifier of the article to be updated") @PathVariable("id") Long articleId,
                                               @Parameter(description = "Data passed to update article") @RequestBody UpdateArticleRequest updateArticleRequest) {
        Article articleToUpdate = articleService.updateArticle(articleId, updateArticleRequest.toArticle());
        return UpdateArticleResponse.fromArticle(articleToUpdate);
    }

    @Operation(summary = "Delete article", description = "Deletes article by given id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value =  "/{id}")
    public void deleteArticle(@Parameter(description = "Identifier of the article to be deleted") @PathVariable("id") Long articleId) {
        articleService.deleteArticle(articleId);
    }

    @Operation(summary = "Find articles by name and/or description",
            description = "Given a name, description or both parameters, it will find article or articles which name and description contain searched phrases")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllArticlesResponse findArticleByNameAndDescription(
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="description", required = false) String description) {
        return FindAllArticlesResponse.fromArticlesList(
                articleService.findArticleByNameAndDescription(name, description));
    }

    @Operation(summary = "Find articles by producer",
            description = "Given a producer it will find article or articles which contains searched phrase in producer")
    @GetMapping(value = "/producer", produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllArticlesResponse findArticleByProducer(
            @RequestParam(name="producer", required = false) String producer) {
        return FindAllArticlesResponse.fromArticlesList(articleService.findByProducer(producer));
    }


}
