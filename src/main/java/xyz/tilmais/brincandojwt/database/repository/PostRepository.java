package xyz.tilmais.brincandojwt.database.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.tilmais.brincandojwt.database.dao.PostsDAO;
import xyz.tilmais.brincandojwt.database.entity.Post;
import xyz.tilmais.brincandojwt.models.PostModel;

import java.sql.SQLException;
import java.util.List;

@Repository
public class PostRepository {

    private PostsDAO postsDAO;

    @Autowired
    public PostRepository(PostsDAO postsDAO) {
        this.postsDAO = postsDAO;
    }

    public Integer salvar(PostModel postModel) throws SQLException {
        return this.postsDAO.inserirPost(postModel);
    }

    public List<Post> obterTodos() throws SQLException {
        return this.postsDAO.obterTodos();
    }
}
