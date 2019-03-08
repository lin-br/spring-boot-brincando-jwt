package xyz.tilmais.brincandojwt.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import xyz.tilmais.brincandojwt.database.repository.UsuarioRepository;
import xyz.tilmais.brincandojwt.models.UsuarioModel;

import java.sql.SQLException;

@Service
public class UsuarioService {

    private Logger logger = LogManager.getLogger(this.getClass());
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Long cadastrarUsuario(UsuarioModel requestUsuario) throws SQLException {

        requestUsuario.setSenha(BCrypt.hashpw(requestUsuario.getSenha(), BCrypt.gensalt(5)));

        logger.info("Usuário para ser salvo: " + requestUsuario);
        logger.info("Salvando usuário ...");
        Long id = this.usuarioRepository.salvar(requestUsuario);
        logger.info("Usuário salvo! :)");

        return id;
    }
}
