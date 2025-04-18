package co.com.nequi.consumer;


import co.com.nequi.consumer.rest.RestConsumer;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import java.io.IOException;


class RestConsumerTest {

//    private static RestConsumer restConsumer;
//
//    private static MockWebServer mockBackEnd;
//
//
//    @BeforeAll
//    static void setUp() throws IOException {
//        mockBackEnd = new MockWebServer();
//        mockBackEnd.start();
//        var webClient = WebClient.builder().baseUrl(mockBackEnd.url("/").toString()).build();
//        restConsumer = new RestConsumer(webClient);
//    }
//
//    @AfterAll
//    static void tearDown() throws IOException {
//
//        mockBackEnd.shutdown();
//    }
//
//    @Test
//    @DisplayName("Validate the function testGet.")
//    void validateTestGet() {
//
//        mockBackEnd.enqueue(new MockResponse()
//                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .setResponseCode(HttpStatus.OK.value())
//                .setBody("{\"state\" : \"ok\"}"));
//        var response = restConsumer.testGet();
//
//        StepVerifier.create(response)
//                .expectNextMatches(objectResponse -> objectResponse.getState().equals("ok"))
//                .verifyComplete();
//    }
//
//    @Test
//    @DisplayName("Validate the function testPost.")
//    void validateTestPost() {
//
//        mockBackEnd.enqueue(new MockResponse()
//                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .setResponseCode(HttpStatus.OK.value())
//                .setBody("{\"state\" : \"ok\"}"));
//        var response = restConsumer.testPost();
//
//        StepVerifier.create(response)
//                .expectNextMatches(objectResponse -> objectResponse.getState().equals("ok"))
//                .verifyComplete();
//    }
}