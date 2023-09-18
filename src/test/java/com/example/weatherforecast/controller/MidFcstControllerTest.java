package com.example.weatherforecast.controller;

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

@WebFluxTest(controllers = MidFcstController.class)
public class MidFcstControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MidFcstService midFcstService;

    @Test
    public void testGetMidFcst() throws UnsupportedEncodingException, URISyntaxException {
        String mockResponse = "{\"wfSv\":\"Sunny\"}";
        MidFcst mockResponse1 = new MidFcst("Sunny");

        Mockito.when(midFcstService.getResponse()).thenReturn(Mono.just(mockResponse1));

        webTestClient.get().uri("/mid-fcst")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(mockResponse);
    }
}

