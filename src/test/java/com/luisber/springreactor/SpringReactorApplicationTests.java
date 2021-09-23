package com.luisber.springreactor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class SpringReactorApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void simpleFluxExample() {
        Flux<String> fluxColors = Flux.just("red", "green", "blue");
        fluxColors.subscribe(System.out::println);
    }

    @Test
    void exampleWebClient(){
        String query="name:gardevoir (subtypes:mega OR subtypes:vmax)";
        var client = WebClient.create("https://api.pokemontcg.io")
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/v2/cards")
                        .queryParam("q", "{query}")
                        .build(query))
                .header("Accept", "application/json, text/plain, */*")
                .retrieve()
                .bodyToMono(String.class);

        client.subscribe(System.out::println);
        StepVerifier.create(client).expectNextCount(1).verifyComplete();
    }

}
