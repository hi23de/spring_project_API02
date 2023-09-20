package com.example.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.data.AttendanceData;
import com.example.data.ClockData;
import com.example.repositry.AttendanceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AttendanceService {

	private final AttendanceRepository attendanceRepository;

	public AttendanceService(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}

	public List<String> getEmployee() throws IOException {

		return attendanceRepository.getEmployee();

	}

	public AttendanceData searchEmployee(int id) throws IOException {
		AttendanceData employee = attendanceRepository.searchEmployee(id);
		List<ClockData> attendances = attendanceRepository.getClock(id);
		if (employee != null) {
			ClockData latestAttendance = !attendances.isEmpty() ? attendances.get(attendances.size() - 1) : null;
			employee.setCreated_at(latestAttendance.getClock_in());
			employee.setUpdated_at(latestAttendance.getClock_out());
		}
		return employee;
	}

	public boolean getAttendClock(ClockData clockData) throws IOException {
		String url = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/clock";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String clock_in = clockData.getClock_in() != null ? clockData.getClock_in() : "";
		String break_start = clockData.getBreak_start() != null ? clockData.getBreak_start() : "";
		String break_end = clockData.getBreak_end() != null ? clockData.getBreak_end() : "";
		String clock_out = clockData.getClock_out() != null ? clockData.getClock_out() : "";

		String requestBody = "{\"body\":\"{\\\"employee_id\\\":\\\"" + clockData.getEmployee_id() +
				"\\\",\\\"clock_in\\\":\\\"" + clock_in +
				"\\\",\\\"break_start\\\":\\\"" + break_start +
				"\\\",\\\"break_end\\\":\\\"" + break_end +
				"\\\",\\\"clock_out\\\":\\\"" + clock_out + "\\\"}\"}";
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
		String responseBody = responseEntity.getBody();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(responseBody);

		if (root.has("error")) {
			return false;
		} else {
			return true;
		}
	}

}
