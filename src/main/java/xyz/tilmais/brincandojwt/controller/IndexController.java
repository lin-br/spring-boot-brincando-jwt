package xyz.tilmais.brincandojwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.status(HttpStatus.OK).body("{\"Cacheador\":\"online *-*\"}");
    }
}
