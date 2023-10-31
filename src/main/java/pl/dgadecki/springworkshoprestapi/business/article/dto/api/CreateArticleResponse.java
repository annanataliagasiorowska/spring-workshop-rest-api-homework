package pl.dgadecki.springworkshoprestapi.business.article.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.article.dto.Article;

import java.math.BigDecimal;

@Schema(description = "Response representing an article")
public record CreateArticleResponse(
        Long id,
        String name,
        String description,
        String producer,
        String eanCode,
        BigDecimal price
) {
    public static CreateArticleResponse fromArticle(Article article) {
        return new CreateArticleResponse(
                article.id(),
                article.name(),
                article.description(),
                article.producer(),
                article.eanCode(),
                article.price()
        );
    }
}
