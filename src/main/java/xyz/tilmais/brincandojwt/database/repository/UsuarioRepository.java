package xyz.tilmais.brincandojwt.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.tilmais.brincandojwt.database.dao.UsuariosDAO;
import xyz.tilmais.brincandojwt.database.entity.Usuario;
import xyz.tilmais.brincandojwt.models.UsuarioModel;

import java.sql.SQLException;

@Repository
public class UsuarioRepository {

    private UsuariosDAO usuariosDAO;

    @Autowired
    public UsuarioRepository(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    public Long salvar(UsuarioModel usuario) throws SQLException {
        return this.usuariosDAO.inserirUsuario(usuario);
    }

    public Usuario obterLogin(String nome) throws SQLException {
        return this.usuariosDAO.obterUsuarioParaLogin(nome);
    }
}
