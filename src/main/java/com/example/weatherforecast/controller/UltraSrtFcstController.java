package com.example.weatherforecast.controller;


import com.example.weatherforecast.entity.UltraSrtFcst;
import com.example.weatherforecast.service.UltraSrtFcstService;
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
@RequestMapping("/ultra-srt-fcst")
public class UltraSrtFcstController {
    private final UltraSrtFcstService ultraSrtFcstService;

    @Autowired
    public UltraSrtFcstController(UltraSrtFcstService ultraSrtFcstService) {
        this.ultraSrtFcstService = ultraSrtFcstService;
    }

    @GetMapping
    public Mono<ResponseEntity<List<UltraSrtFcst>>> getUltraSrtFcst() throws UnsupportedEncodingException, URISyntaxException {
        return ultraSrtFcstService.getResponse()
                .map(UltraSrtFcstList -> ResponseEntity.ok(UltraSrtFcstList));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UltraSrtFcst>> getAllUltraSrtFcst() {
        List<UltraSrtFcst> ultraSrtFcstList = ultraSrtFcstService.getUltraSrtFcstList();

        if (ultraSrtFcstList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ultraSrtFcstList);
    }
}
