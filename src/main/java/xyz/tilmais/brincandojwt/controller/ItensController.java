package xyz.tilmais.brincandojwt.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.tilmais.brincandojwt.database.entity.Item;
import xyz.tilmais.brincandojwt.models.ItemModel;
import xyz.tilmais.brincandojwt.services.ItemService;
import xyz.tilmais.brincandojwt.services.UriResponsePost;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("itens")
public class ItensController {

    private Logger logger = LogManager.getLogger(this.getClass());
    private ItemService itemService;
    private UriResponsePost uriResponsePost;

    @Autowired
    public ItensController(ItemService itemService, UriResponsePost uriResponsePost) {
        this.itemService = itemService;
        this.uriResponsePost = uriResponsePost;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obterTodos() throws SQLException {

        logger.info("Requisição GET para a URI: " +
                ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
        List<Item> items = this.itemService.obterTodos();

        logger.info("Itens recuperados: " + items);

        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(items);
        }
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity cadastrarItem(@Valid @RequestBody ItemModel request) throws SQLException {

        logger.info("Requisição POST para a URI: " +
                ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
        logger.info("Body da requisição: " + request);

        Integer identificadorItem = this.itemService.cadastrarItem(request);

        URI uri = this.uriResponsePost.obterUri(identificadorItem);

        logger.info("URI de response: " + uri);

        return ResponseEntity.created(uri).build();
    }
}
