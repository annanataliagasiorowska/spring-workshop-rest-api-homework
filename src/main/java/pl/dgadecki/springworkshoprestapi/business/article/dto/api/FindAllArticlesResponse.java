package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.util.List;

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
