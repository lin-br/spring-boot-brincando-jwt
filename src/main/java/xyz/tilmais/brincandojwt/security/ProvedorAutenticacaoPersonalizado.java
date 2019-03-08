package xyz.tilmais.brincandojwt.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import xyz.tilmais.brincandojwt.database.entity.Usuario;
import xyz.tilmais.brincandojwt.database.repository.UsuarioRepository;

import java.sql.SQLException;

@Component
public class ProvedorAutenticacaoPersonalizado implements AuthenticationProvider {

    private String mensagem = "Usuário ou senha não encontrados!";
    private Logger logger = LogManager.getLogger(this.getClass());
    private UsuarioRepository usuarioRepository;

    @Autowired
    public ProvedorAutenticacaoPersonalizado(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String nome = authentication.getName();
        String senha = authentication.getCredentials().toString();

        this.logger.info("Autenticação solicitada com os dados: [" + nome + "," + senha + "]");

        try {
            Usuario login = this.usuarioRepository.obterLogin(nome);

            this.logger.info("Usuário recuperado da base de dados: " + login);

            if (login == null) {
                throw new AuthenticationCredentialsNotFoundException(this.mensagem);
            } else {
                if (BCrypt.checkpw(senha, login.getSenha())) {
                    return new UsernamePasswordAuthenticationToken(login.getNome(), login.getSenha(), login.getListaRegras());
                } else {
                    throw new AuthenticationCredentialsNotFoundException(this.mensagem);
                }
            }
        } catch (SQLException e) {
            this.logger.error(e);
            throw new BadCredentialsException(this.mensagem, e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
