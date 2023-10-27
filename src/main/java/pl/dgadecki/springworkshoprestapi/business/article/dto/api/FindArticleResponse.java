package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.math.BigDecimal;

@Schema(description = "Response representing article found by given criteria")
public record FindArticleResponse(
        Long id,
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) {
    public static FindArticleResponse fromArticle(Article article) {
        return new FindArticleResponse(
                article.id(),
                article.name(),
                article.description(),
                article.producer(),
                article.eanCode(),
                article.price()
        );
    }
}
