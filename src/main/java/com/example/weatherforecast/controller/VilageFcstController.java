package com.example.weatherforecast.controller;


import com.example.weatherforecast.entity.VilageFcst;
import com.example.weatherforecast.service.VilageFcstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/vilage-fcst")
public class VilageFcstController {
    private final VilageFcstService vilageFcstService;

    @Autowired
    public VilageFcstController(VilageFcstService VilageFcstService) {
        this.vilageFcstService = VilageFcstService;
    }

    @GetMapping
    public Mono<ResponseEntity<List<VilageFcst>>> getVilageFcst() throws UnsupportedEncodingException, URISyntaxException {
        return vilageFcstService.getResponse()
                .map(vilageFcstList -> ResponseEntity.ok(vilageFcstList));
    }

    @GetMapping("/all")
    public ResponseEntity<List<VilageFcst>> getAllVilageFcst() {
        List<VilageFcst> vilageFcstList = vilageFcstService.getVilageFcstList();

        if (vilageFcstList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vilageFcstList);
    }
}
