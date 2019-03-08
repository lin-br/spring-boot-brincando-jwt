package xyz.tilmais.brincandojwt.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tilmais.brincandojwt.database.entity.Usuario;
import xyz.tilmais.brincandojwt.models.UsuarioModel;
import xyz.tilmais.brincandojwt.services.ConectorService;

import java.sql.*;

@Component
public class UsuariosDAO {

    private ConectorService conector;

    @Autowired
    public UsuariosDAO(ConectorService conector) {
        this.conector = conector;
    }

    public Long inserirUsuario(UsuarioModel usuario) throws SQLException {
        Long id = null;
        String query = "INSERT INTO usuarios_tbl(nome, senha, situacao) VALUES (?, ?, ?);";

        try (Connection connection = this.conector.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, usuario.getNome());
                preparedStatement.setString(2, usuario.getSenha());
                preparedStatement.setInt(3, 1);
                preparedStatement.execute();

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong("GENERATED_KEY");
                    }
                    return id;
                }
            }
        }
    }

    public Usuario obterUsuarioParaLogin(String nome) throws SQLException {
        String query = "SELECT ut.id_usuarios, ut.nome, ut.senha, rt.nome as regra " +
                "FROM usuarios_tbl ut " +
                "JOIN regras_usuarios_tbl rut ON ut.id_usuarios = rut.id_usuarios " +
                "JOIN regras_tbl rt ON rut.id_regras = rt.id_regras " +
                "WHERE ut.nome = ? AND ut.situacao = 1 AND rut.situacao = 1 ORDER BY ut.data_criacao DESC;";

        Usuario usuario = new Usuario();

        try (Connection connection = this.conector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, nome.toLowerCase());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        usuario.setId(resultSet.getLong("id_usuarios"));
                        usuario.setNome(resultSet.getString("nome"));
                        usuario.setSenha(resultSet.getString("senha"));
                        usuario.adicionarRegra(resultSet.getString("regra"));
                    }
                }
            }
        }
        return usuario.getId() != null ? usuario : null;
    }
}
