package xyz.tilmais.brincandojwt.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.tilmais.brincandojwt.models.PostModel;
import xyz.tilmais.brincandojwt.services.PostService;
import xyz.tilmais.brincandojwt.services.UriResponsePost;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("posts")
public class PostsController {

    private Logger logger = LogManager.getLogger(this.getClass());
    private PostService postService;
    private UriResponsePost uriResponsePost;

    @Autowired
    public PostsController(PostService postService, UriResponsePost uriResponsePost) {
        this.postService = postService;
        this.uriResponsePost = uriResponsePost;
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity cadastrarPost(@Valid @RequestBody PostModel request) throws SQLException {

        logger.info("Requisição para a URI: " + ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
        logger.info("Body da requisição: " + request);

        Integer identificadorPost = this.postService.cadastrarPost(request);

        URI uri = this.uriResponsePost.obterUri(identificadorPost);

        logger.info("URI de response: " + uri);

        return ResponseEntity.created(uri).build();
    }
}
