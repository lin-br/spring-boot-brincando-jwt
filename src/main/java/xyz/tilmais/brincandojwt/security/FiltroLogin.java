package xyz.tilmais.brincandojwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import xyz.tilmais.brincandojwt.models.LoginModel;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroLogin extends AbstractAuthenticationProcessingFilter {

    protected FiltroLogin(String url, AuthenticationManager authenticationManager) {
        super(url);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {

        LoginModel loginModel = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(loginModel.getNome(), loginModel.getSenha()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        TokenAutenticacao.adicionarToken(response, authResult);
    }
}
