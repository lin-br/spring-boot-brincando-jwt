package xyz.tilmais.brincandojwt.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import xyz.tilmais.brincandojwt.database.repository.PostRepository;
import xyz.tilmais.brincandojwt.models.PostModel;

import java.sql.SQLException;

@Service
public class PostService {

    private Logger logger = LogManager.getLogger(this.getClass());
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PreAuthorize("hasAuthority('DESIGN')")
    public Integer cadastrarPost(PostModel postModel) throws SQLException {

        logger.info("Post para ser salvo: " + postModel);
        logger.info("Salvando Post...");
        Integer id = this.postRepository.salvar(postModel);
        logger.info("Post salvo! :)");

        return id;
    }
}
