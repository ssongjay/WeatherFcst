package com.example.weatherforecast.controller;

import com.example.weatherforecast.entity.MidFcst;
import com.example.weatherforecast.entity.VilageFcst;
import com.example.weatherforecast.service.MidFcstService;
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
@RequestMapping("/mid-fcst")
public class MidFcstController {
    private final MidFcstService midFcstService;

    @Autowired
    public MidFcstController(MidFcstService midFcstService) {
        this.midFcstService = midFcstService;
    }

    @GetMapping
    public Mono<ResponseEntity<MidFcst>> getMidFcst() throws UnsupportedEncodingException, URISyntaxException {
        return midFcstService.getResponse()
                .map(midFcst -> ResponseEntity.ok(midFcst));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MidFcst>> getAllVilageFcst() {
        List<MidFcst> MidFcsttList = midFcstService.getMidFcstList();

        if (MidFcsttList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(MidFcsttList);
    }
}
