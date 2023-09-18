package com.example.weatherforecast.controller;

import com.example.weatherforecast.entity.VilageFcst;
import com.example.weatherforecast.service.VilageFcstService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.example.weatherforecast.entity.MidFcst;
import com.example.weatherforecast.service.MidFcstService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@WebFluxTest(controllers = VilageFcstController.class)
public class VilageFcstControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private VilageFcstService vilageFcstService;

    @Test
    public void testGetVilageFcst() throws UnsupportedEncodingException, URISyntaxException {
        String mockResponse = "[" + "{" +
                "\"baseDate\": \"20230918\"," +
                "\"baseTime\": \"0500\"," +
                "\"fcstDate\": \"20230918\"," +
                "\"fcstTime\": \"0600\"," +
                "\"category\": \"TMP\"," +
                "\"fcstValue\": \"22\"," +
                "\"nx\": \"55\"," +
                "\"ny\": \"127\"" +
                "}" + "]";
        List<VilageFcst> vilageFcstList = new ArrayList<>();
        VilageFcst mockResponse1 = new VilageFcst("20230918", "0500", "20230918", "0600", "TMP", "22", "55", "127");
        vilageFcstList.add(mockResponse1);
        Mockito.when(vilageFcstService.getResponse()).thenReturn(Mono.just(vilageFcstList));

        webTestClient.get().uri("/vilage-fcst")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(mockResponse);
    }
}