package com.example.weatherforecast.controller;

import com.example.weatherforecast.entity.UltraSrtFcst;
import com.example.weatherforecast.entity.VilageFcst;
import com.example.weatherforecast.service.UltraSrtFcstService;
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

@WebFluxTest(controllers = UltraSrtFcstController.class)
public class UltraSrtFcstControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UltraSrtFcstService ultraSrtFcstService;

    @Test
    public void testGetUltraSrtFcst() throws UnsupportedEncodingException, URISyntaxException {
        String mockResponse = "[" + "{" +
                "\"baseDate\": \"20230918\"," +
                "\"baseTime\": \"0630\"," +
                "\"fcstDate\": \"20230918\"," +
                "\"fcstTime\": \"0700\"," +
                "\"category\": \"LGT\"," +
                "\"fcstValue\": \"0\"," +
                "\"nx\": \"55\"," +
                "\"ny\": \"127\"" +
                "}" + "]";
        List<UltraSrtFcst> ultraSrtFcstList = new ArrayList<>();
        UltraSrtFcst mockResponse1 = new UltraSrtFcst("20230918", "0630", "20230918", "0700", "LGT", "0", "55", "127");
        ultraSrtFcstList.add(mockResponse1);
        Mockito.when(ultraSrtFcstService.getResponse()).thenReturn(Mono.just(ultraSrtFcstList));

        webTestClient.get().uri("/ultra-srt-fcst")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .json(mockResponse);
    }
}
