package com.example.repositry;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.data.AttendanceData;
import com.example.data.ClockData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class AttendanceRepository {

	//社員情報
	public AttendanceData[] getEmployee() throws IOException {

		String url = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/employee";

		RestTemplate rest = new RestTemplate();

		ResponseEntity<String> response = rest.getForEntity(url, String.class);

		String json = response.getBody();

		ObjectMapper mapper = new ObjectMapper();

		AttendanceData[] employeeList = mapper.readValue(json, AttendanceData[].class);

		return employeeList;

	}

	//勤怠情報
	public ClockData[] getAttendClock(int picId) throws IOException {

		String url = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/clock?employeeId="
				+ picId;

		RestTemplate rest = new RestTemplate();

		ResponseEntity<String> response = rest.getForEntity(url, String.class);

		String json = response.getBody();

		ObjectMapper mapper = new ObjectMapper();

		ClockData[] attendClockList = mapper.readValue(json, ClockData[].class);

		return attendClockList;

	}

}
