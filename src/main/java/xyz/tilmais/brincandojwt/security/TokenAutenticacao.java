package xyz.tilmais.brincandojwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class TokenAutenticacao {

    private static final long TEMPO_VALIDACAO = TimeUnit.SECONDS.toMillis(1);
    private static final byte[] chaveBytes = MacProvider.generateKey(SignatureAlgorithm.HS256).getEncoded();
    private static final String PALAVRA_CHAVE = Base64.getEncoder().encodeToString(chaveBytes);
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER = "Authorization";
    private static final String CLAIMS_KEY = "permissoes";
    private static Logger logger = LogManager.getLogger(TokenAutenticacao.class.getName());

    static void adicionarToken(HttpServletResponse response, Authentication authentication) {
        Date validacao = new Date(System.currentTimeMillis() + TEMPO_VALIDACAO);

        logger.info("Validação do token: " + validacao);

        Claims claims = Jwts.claims()
                .setIssuedAt(new Date())
                .setExpiration(validacao)
                .setSubject(authentication.getName());

        claims.put(CLAIMS_KEY, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, PALAVRA_CHAVE);

        String jwt = jwtBuilder.compact();

        logger.info("JWT: " + jwt);

        response.addHeader(HEADER, TOKEN_PREFIX + " " + jwt);
    }

    static Authentication obterAutenticacao(HttpServletRequest request) {
        String token = request.getHeader(HEADER);

        if (token != null) {
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(CLAIMS_KEY)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String nome = claims.getSubject();

            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            if (claims.containsKey(CLAIMS_KEY)) {
                List<String> permissoes = (List<String>) claims.get(CLAIMS_KEY);
                for (String permissao : permissoes) {
                    grantedAuthorities.add(() -> permissao);
                }
            }

            if (nome != null) {
                return new UsernamePasswordAuthenticationToken(nome, null, grantedAuthorities);
            }
        }
        return null;
    }
}
