package me.vainio.aoc.client;

import me.vainio.aoc.cache.AocCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AocClientTest {

    @Mock
    private AocCache mockCache;
    
    @Mock
    private HttpClient mockHttpClient;
    
    @Mock
    private HttpResponse<String> mockHttpResponse;
    
    private AocClient aocClient;
    private static final String SESSION_COOKIE = "test-session-cookie";
    private static final int YEAR = 2023;
    private static final int DAY = 5;
    private static final int PART = 1;
    private static final String INPUT_DATA = "test input data\nwith multiple lines";
    private static final String ANSWER = "12345";

    @BeforeEach
    void setUp() {
        aocClient = new AocClient(mockCache, SESSION_COOKIE, mockHttpClient);
    }

    @Test
    void fetchInputWhenInputAlreadyCachedShouldNotFetchFromApi() throws IOException, InterruptedException {
        when(mockCache.hasInput(YEAR, DAY)).thenReturn(true);

        aocClient.fetchInput(YEAR, DAY);

        verify(mockCache).hasInput(YEAR, DAY);
        verify(mockHttpClient, never()).send(any(), any());
        verify(mockCache, never()).saveInput(any(Integer.class), any(Integer.class), any(String.class));
    }

    @Test
    void fetchInputWhenInputNotCachedShouldFetchAndSave() throws IOException, InterruptedException {
        when(mockCache.hasInput(YEAR, DAY)).thenReturn(false);
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(INPUT_DATA + "   \n");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        aocClient.fetchInput(YEAR, DAY);

        verify(mockCache).hasInput(YEAR, DAY);
        verify(mockHttpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
        verify(mockCache).saveInput(YEAR, DAY, INPUT_DATA);
    }

    @Test
    void submitAnswerWhenAnswerNotCachedShouldNotSubmitToApi() throws IOException, InterruptedException {
        when(mockCache.hasAnswer(YEAR, DAY, PART)).thenReturn(false);

        aocClient.submitAnswer(YEAR, DAY, PART);

        verify(mockCache).hasAnswer(YEAR, DAY, PART);
        verify(mockCache, never()).getAnswer(any(Integer.class), any(Integer.class), any(Integer.class));
        verify(mockHttpClient, never()).send(any(), any());
    }

    @Test
    void submitAnswerWhenAnswerCachedShouldSubmitToApi() throws IOException, InterruptedException {
        when(mockCache.hasAnswer(YEAR, DAY, PART)).thenReturn(true);
        when(mockCache.getAnswer(YEAR, DAY, PART)).thenReturn(ANSWER);
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn("That's the right answer!");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        aocClient.submitAnswer(YEAR, DAY, PART);

        verify(mockCache).hasAnswer(YEAR, DAY, PART);
        verify(mockCache).getAnswer(YEAR, DAY, PART);
        verify(mockHttpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
    }
}
