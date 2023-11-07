package pl.dgadecki.springworkshoprestapi.webfilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RequestExecutionTimeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        System.out.println("Request URL: " + request.getRequestURI());
        log.info("Request execution time: {}ms", endTime - startTime);
    }
}
