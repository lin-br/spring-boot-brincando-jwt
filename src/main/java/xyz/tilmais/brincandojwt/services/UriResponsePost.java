package xyz.tilmais.brincandojwt.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class UriResponsePost {

    public URI obterUri(Integer id) {
        return this.processar(id);
    }

    public URI obterUri(Long id) {
        return this.processar(id);
    }

    private URI processar(Object object) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{object}").buildAndExpand(object).toUri();
    }
}
