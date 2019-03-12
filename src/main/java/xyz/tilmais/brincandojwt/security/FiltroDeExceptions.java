package xyz.tilmais.brincandojwt.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FiltroDeExceptions extends OncePerRequestFilter {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Solicita que o próximo filtro seja executado.
            filterChain.doFilter(request, response);

        } catch (MethodNotAllowedException methodNotAllowedException) {

            this.logger.error(methodNotAllowedException.getMessage());
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        } catch (ExpiredJwtException expiredJwtException) {

            this.logger.error(expiredJwtException.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write("{\"mensagem\":\"Token vencido ou inválido!\"}");
        }
    }
}
