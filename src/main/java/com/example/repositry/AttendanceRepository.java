package com.example.repositry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.data.AttendanceData;
import com.example.data.ClockData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class AttendanceRepository {

	public List<String> employeeList(String data) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		List<String> List = new ArrayList<>();

		for (JsonNode node : rootNode) {
			if (node instanceof ObjectNode) {
				ObjectNode objectNode = (ObjectNode) node;
				objectNode.remove("created_at");
				objectNode.remove("updated_at");
				List.add(objectNode.toString());
			}
		}

		return List;

	}

	public List<String> getEmployee() throws IOException {
		String url = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/employee";

		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(url, String.class);
		String json = response.getBody();
		return employeeList(json);
		//引数json
	}

	public AttendanceData searchEmployee(int id) throws IOException {
		String url = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/employee";

		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.getForEntity(url, String.class);
		String json = response.getBody();

		ObjectMapper mapper = new ObjectMapper();
		List<AttendanceData> employees = mapper.readValue(json, new TypeReference<List<AttendanceData>>() {
		});

		return employees.stream()
				.filter(employee -> employee.getId() == id)
				.findFirst()
				.orElse(null);
	}

	public boolean addEmployee(String NAME, String HOMETOWN, String JOINING_MONTH) throws IOException {
		String requestBody = "{\"body\": \"{\\\"name\\\":\\\"" + NAME + "\\\",\\\"HOMETOWN\\\":\\\"" + HOMETOWN
				+ "\\\",\\\"joining_month\\\":\\\"" + JOINING_MONTH + "\\\"}\"}";

		String apiUrl = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/employee";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				return true;
			} else {
				return false;
			}
		} catch (HttpServerErrorException ex) {
			return false;
		}
	}

	public List<ClockData> getClock(int employeeId) throws IOException {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromUriString(
							"https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/clock")
					.queryParam("employeeId", employeeId);

			String attendanceUrl = builder.toUriString();

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> attendanceResponse = restTemplate.getForEntity(attendanceUrl, String.class);

			if (attendanceResponse.getStatusCode().is2xxSuccessful()) {
				String attendanceJson = attendanceResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				List<ClockData> attendances = mapper.readValue(attendanceJson, new TypeReference<List<ClockData>>() {
				});
				Collections.reverse(attendances);
				return attendances;
			} else {
				return Collections.emptyList();
			}
		} catch (Exception ex) {
			return Collections.emptyList();
		}
	}

}
