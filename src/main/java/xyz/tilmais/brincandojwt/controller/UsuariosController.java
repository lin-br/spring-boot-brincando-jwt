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
import xyz.tilmais.brincandojwt.models.UsuarioModel;
import xyz.tilmais.brincandojwt.services.UsuarioService;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("usuarios")
public class UsuariosController {

    private Logger logger = LogManager.getLogger(this.getClass());

    private UsuarioService usuarioService;

    @Autowired
    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity post(@Valid @RequestBody UsuarioModel request)
            throws SQLException {

        logger.info("Requisição para a URI: " + ServletUriComponentsBuilder.fromCurrentRequest().build().toString());
        logger.info("Body da requisição: " + request);

        Long identificador = usuarioService.cadastrarUsuario(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{identificador}")
                .buildAndExpand(identificador)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
