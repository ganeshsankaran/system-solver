package hello;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import hello.SystemOfEquations;

@Service
public class SystemSolverService {

  public SystemSolverService() {
  }

  public String solve(SystemOfEquations system) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);

    String uri = "http://system-solver-api.herokuapp.com/api/v1/solve";
    String params = String.format("?a_1_1=%d&a_1_2=%d&a_2_1=%d&a_2_2=%d&b_1=%d&b_2=%d",
      system.getA_1_1(), system.getA_1_2(), system.getA_2_1(), system.getA_2_2(), system.getB_1(), system.getB_2());
    String url = uri + params;
    String response = "";

    try {
      ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
      MediaType contentType = responseEntity.getHeaders().getContentType();
      HttpStatus statusCode = responseEntity.getStatusCode();
      response = responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      response = "{\"error\": \"401: Unauthorized\"}";
    }

    return response;
  }
}