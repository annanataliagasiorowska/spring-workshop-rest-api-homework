package pl.dgadecki.springworkshoprestapi.configuration.security;

import org.springframework.security.core.GrantedAuthority;

public enum WorkshopAuthority implements GrantedAuthority {

    ACCESS_TO_ARTICLE_API_READ,
    ACCESS_TO_CUSTOMER_API_READ,
    ACCESS_TO_ARTICLE_API_WRITE,
    CREATE_CUSTOMER_API_WRITE;

    @Override
    public String getAuthority() {
        return name().toUpperCase();
    }
}
