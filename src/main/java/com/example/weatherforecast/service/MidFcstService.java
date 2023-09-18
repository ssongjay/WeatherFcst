package com.example.weatherforecast.service;

import com.example.weatherforecast.repository.MidFcstRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.weatherforecast.entity.MidFcst;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Service
public class MidFcstService {
    private final MidFcstRepository midFcstRepository;

    @Value("${service-key}")
    private String apiKey;

    private final String BASE_URL = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
    private final WebClient webClient;

    private String pageNo = "1";
    private String numOfRows = "10";
    private String dataType = "JSON";
    private String stnId = "108";
    private String tmFc = "202309180600";
    @Autowired
    public MidFcstService(MidFcstRepository midFcstRepository) {
        this.webClient = WebClient.create();
        this.midFcstRepository = midFcstRepository;
    }

    private URI encodeApi() throws UnsupportedEncodingException, URISyntaxException {
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
        builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
        builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
        builder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
        builder.append("&" + URLEncoder.encode("stnId", "UTF-8") + "=" + URLEncoder.encode(stnId, "UTF-8"));
        builder.append("&" + URLEncoder.encode("tmFc", "UTF-8") + "=" + URLEncoder.encode(tmFc, "UTF-8"));
        URI uri = new URI(builder.toString());
        return uri;
    }

    public Mono<MidFcst> getResponse() throws UnsupportedEncodingException, URISyntaxException {
        return webClient.get()
                .uri(encodeApi())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> saveMidFcst(jsonNode));
    }

    public List<MidFcst> getMidFcstList() {
        return midFcstRepository.findAll();
    }

    private MidFcst saveMidFcst(JsonNode jsonNode) {
        if (jsonNode != null && jsonNode.has("response") && jsonNode.get("response").has("body")
                && jsonNode.get("response").get("body").has("items")
                && jsonNode.get("response").get("body").get("items").has("item")) {

            JsonNode item = jsonNode.get("response").get("body").get("items").get("item");

            if (item.isArray() && item.size() > 0) {
                JsonNode firstItem = item.get(0);
                if (firstItem.has("wfSv")) {
                    String wfSv = firstItem.get("wfSv").asText();
                    MidFcst midFcst = new MidFcst(wfSv);
                    return midFcstRepository.save(midFcst);
                } else {
                    throw new RuntimeException("JSON response does not contain 'wfSv' field");
                }
            } else {
                throw new RuntimeException("JSON 'item' array is empty or missing");
            }
        } else {
            throw new RuntimeException("Invalid JSON response format");
        }
    }
}
