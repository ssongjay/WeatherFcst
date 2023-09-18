package com.example.weatherforecast.service;

import com.example.weatherforecast.repository.VilageFcstRepository;
import com.example.weatherforecast.repository.VilageFcstRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.weatherforecast.entity.VilageFcst;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VilageFcstService {
    private final VilageFcstRepository vilageFcstRepository;

    @Value("${service-key}")
    private String apiKey;

    private final String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    private final WebClient webClient;

    private String pageNo = "1";
    private String numOfRows = "10";
    private String dataType = "JSON";
    private String baseDate = "20230917";
    private String baseTime = "0500";
    private String nx = "55";
    private String ny = "127";

    @Autowired
    public VilageFcstService(VilageFcstRepository vilageFcstRepository) {
        this.webClient = WebClient.create();
        this.vilageFcstRepository = vilageFcstRepository;
    }

    private URI encodeApi() throws UnsupportedEncodingException, URISyntaxException {
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
        builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
        builder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
        builder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
        builder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        builder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
        builder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));

        URI uri = new URI(builder.toString());
        return uri;
    }

    public Mono<List<VilageFcst>> getResponse() throws UnsupportedEncodingException, URISyntaxException {
        return webClient.get()
                .uri(encodeApi())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> saveVilageFcst(jsonNode));
    }

    public List<VilageFcst> getVilageFcstList() {
        return vilageFcstRepository.findAll();
    }

    private List<VilageFcst> saveVilageFcst(JsonNode jsonNode) {
        if (jsonNode != null && jsonNode.has("response") && jsonNode.get("response").has("body")
                && jsonNode.get("response").get("body").has("items")
                && jsonNode.get("response").get("body").get("items").has("item")) {

            JsonNode itemArray = jsonNode.get("response").get("body").get("items").get("item");

            if (itemArray.isArray() && itemArray.size() > 0) {
                List<VilageFcst> vilageFcstList = new ArrayList<>();

                for (JsonNode itemNode : itemArray) {
                    if (itemNode.has("category") && itemNode.has("fcstValue")) {
                        String baseDate = itemNode.get("baseDate").asText();
                        String baseTime = itemNode.get("baseTime").asText();
                        String fcstDate = itemNode.get("fcstDate").asText();
                        String fcstTime = itemNode.get("fcstTime").asText();
                        String category = itemNode.get("category").asText();
                        String fcstValue = itemNode.get("fcstValue").asText();
                        String nx = itemNode.get("nx").asText();
                        String ny = itemNode.get("ny").asText();

                        VilageFcst vilageFcst = new VilageFcst(baseDate, baseTime, fcstDate, fcstTime, category, fcstValue, nx, ny);
                        vilageFcstRepository.save(vilageFcst);
                        vilageFcstList.add(vilageFcst);
                    } else {
                        throw new RuntimeException("JSON item does not contain require field");
                    }
                }
                return vilageFcstList;
            } else {
                throw new RuntimeException("JSON 'item' array is empty or missing");
            }
        } else {
            throw new RuntimeException("Invalid JSON response format");
        }
    }
}
