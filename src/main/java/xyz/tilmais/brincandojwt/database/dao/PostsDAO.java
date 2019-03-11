package xyz.tilmais.brincandojwt.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.tilmais.brincandojwt.database.entity.Post;
import xyz.tilmais.brincandojwt.models.PostModel;
import xyz.tilmais.brincandojwt.services.ConectorService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostsDAO {

    private ConectorService conector;

    @Autowired
    public PostsDAO(ConectorService conector) {
        this.conector = conector;
    }

    public Integer inserirPost(PostModel postModel) throws SQLException {
        Integer id = null;
        String query = "INSERT INTO posts_tbl(titulo, conteudo) VALUES(?, ?);";

        try (Connection connection = this.conector.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, postModel.getTitulo());
                preparedStatement.setString(2, postModel.getConteudo());
                preparedStatement.execute();

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt("GENERATED_KEY");
                    }
                }
            }
        }

        return id;
    }

    public List<Post> obterTodos() throws SQLException {
        List<Post> listaPost = new ArrayList<>();
        String query = "SELECT * FROM posts_tbl WHERE data_exclusao IS NULL;";

        try (Connection connection = conector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Post post = new Post();
                        post.setId(resultSet.getInt("id_posts"));
                        post.setTitulo(resultSet.getString("titulo"));
                        post.setConteudo(resultSet.getString("conteudo"));
                        post.setDataCriacao(resultSet.getTimestamp("data_criacao"));
                        post.setDataAlteracao(resultSet.getTimestamp("data_alteracao"));
                        post.setDataExclusao(resultSet.getTimestamp("data_exclusao"));

                        listaPost.add(post);
                    }
                }
            }
        }

        return listaPost;
    }
}
