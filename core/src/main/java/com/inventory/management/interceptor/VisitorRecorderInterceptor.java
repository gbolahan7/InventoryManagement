package com.inventory.management.interceptor;

import com.inventory.management.domain.Visitor;
import com.inventory.management.repository.UserRepository;
import com.inventory.management.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@Order
@RequiredArgsConstructor
public class VisitorRecorderInterceptor extends OncePerRequestFilter {
    private final List<String> excludeUrlPatterns = List.of("/api/auth/**");
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final VisitorRepository visitorRepository;

    @Value("${api.base-path:/api/v1}")
    private final String basePath;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            String user = getUser();
            String[] page = extractControllerPage(request.getServletPath());
            String domainPage = page[0], auxiliaryPage = page[1];
            Visitor visitor = new Visitor();
            visitor.setUser(user);
            visitor.setDomain(domainPage);
            visitor.setAux(auxiliaryPage);
            visitorRepository.save(visitor);
        }
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludeUrlPatterns.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    private String[] extractControllerPage(String path) {
        int len = (basePath + "/").length();
        String[] base = path.substring(len).split("/");
        return new String[]{base[0], base.length < 2 ? "" : base[1]};
    }

    private String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return "Anonymous";
        return authentication.getName();
    }

}
