package com.example.monitor;

import com.example.monitor.model.monitored_endpoint.MonitoredEndpointDTO;
import com.example.monitor.model.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public static final String BASE_URL = "http://localhost:";
    public static final String ENDPOINT_NAME_1 = "Google";
    public static final String ENDPOINT_URL_1 = "https://www.google.com";
    public static final String ENDPOINT_NAME_2 = "Bing";
    public static final String ENDPOINT_URL_2 = "https://www.bing.com";
    public static final String USERNAME_1 = "John";
    public static final String USERNAME_2 = "Jane";
    public static final String EMAIL_1 = "John@example.com";
    public static final String EMAIL_2 = "Jane@example.com";

    private UserDTO createUser(String url, String username, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setEmail(email);

        HttpEntity<UserDTO> request = new HttpEntity<>(user, headers);

        ResponseEntity<UserDTO> response = restTemplate.exchange(url + "/user",
                HttpMethod.POST,
                request,
                UserDTO.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        user = response.getBody();
        assertNotNull(user);

        return user;
    }

    private MonitoredEndpointDTO createEndpoint(String url, UserDTO user, String endpointName, String endpointUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + user.getAccessToken());

        MonitoredEndpointDTO endpoint = new MonitoredEndpointDTO();
        endpoint.setName(endpointName);
        endpoint.setUrl(endpointUrl);

        ResponseEntity<MonitoredEndpointDTO> createEndpointResponse = restTemplate.exchange(url + "/monitored_endpoint",
                HttpMethod.POST,
                new HttpEntity<>(endpoint, headers),
                MonitoredEndpointDTO.class
        );
        assertEquals(HttpStatus.CREATED, createEndpointResponse.getStatusCode());

        endpoint = createEndpointResponse.getBody();
        assertNotNull(endpoint);

        return endpoint;
    }

    private void deleteUser(String url, UserDTO user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(url + "/user/" + user.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class
        );
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }


    private List<MonitoredEndpointDTO> getEndpoints(String url, UserDTO user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + user.getAccessToken());

        ParameterizedTypeReference<List<MonitoredEndpointDTO>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<MonitoredEndpointDTO>> getResponse = restTemplate.exchange(url + "/monitored_endpoint",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                responseType
        );

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        return getResponse.getBody();
    }

    @Test
    public void testCreateUser() {
        String url = BASE_URL + port;

        UserDTO user = createUser(url, USERNAME_1, EMAIL_1);

        assertEquals(USERNAME_1, user.getUsername());
        assertEquals(EMAIL_1, user.getEmail());

        deleteUser(url, user);
    }

    @Test
    public void testCreateEndpoint() {
        String url = BASE_URL + port;

        UserDTO user = createUser(url, USERNAME_1, EMAIL_1);

        MonitoredEndpointDTO endpoint = createEndpoint(url, user, ENDPOINT_NAME_2, ENDPOINT_URL_1);

        assertEquals(ENDPOINT_NAME_2, endpoint.getName());
        assertEquals(ENDPOINT_URL_1, endpoint.getUrl());

        deleteUser(url, user);
    }

    @Test
    public void testAuthorisation() {
        String url = BASE_URL + port;

        UserDTO firstUser = createUser(url, USERNAME_1, EMAIL_1);
        UserDTO secondUser = createUser(url, USERNAME_2, EMAIL_2);

        createEndpoint(url, firstUser, ENDPOINT_NAME_1, ENDPOINT_URL_1);
        createEndpoint(url, secondUser, ENDPOINT_NAME_2, ENDPOINT_URL_2);

        List<MonitoredEndpointDTO> endpoints_1 = getEndpoints(url, firstUser);
        assertNotNull(endpoints_1);

        List<MonitoredEndpointDTO> endpoints_2 = getEndpoints(url, secondUser);
        assertNotNull(endpoints_2);

        assertNotEquals(endpoints_1, endpoints_2);

        deleteUser(url, firstUser);
        deleteUser(url, secondUser);
    }

}

