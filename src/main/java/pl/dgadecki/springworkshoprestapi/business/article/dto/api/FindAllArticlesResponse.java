package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.util.List;

@Schema(description = "Response representing all articles or articles found by given criteria")
public record FindAllArticlesResponse(
        List<FindArticleResponse> articles
) {
    public static FindAllArticlesResponse fromArticlesList(List<Article> articles) {
        List<FindArticleResponse> allArticlesResponse = articles.stream()
                .map(FindArticleResponse::fromArticle)
                .toList();
        return new FindAllArticlesResponse(allArticlesResponse);
    }
}
