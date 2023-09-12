package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.data.AttendanceData;
import com.example.data.ClockData;
import com.example.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AttendanceController {

	public final AttendanceService attendanceService;

	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	//社員一覧:Tableに社員情報employeeListを渡す	
	@GetMapping("/attendanceTable")
	public String attendanceTable(Model model) throws IOException {

		List<AttendanceData> employeeList = attendanceService.getEmployee();

		model.addAttribute("employeeList", employeeList);

		return "attendanceTable.html";

	}

	//社員新規登録
	@GetMapping("/new")
	public String attendanceNew() {
		return "attendanceNew";
	}
	
	@PostMapping("/addEmployee")
	public String addEmployee(@RequestParam("name") String NAME, @RequestParam("hometown") String HOMETOWN, @RequestParam("joining_month") String JOINING_MONTH) {
				
		String newEmployee = "{"id":,"name":"NAME","hometown":"HOMETOWN","joining_month":"JOINING_MONTH"}";
		
		String url = "https://jsn9xu2vsk.execute-api.ap-northeast-1.amazonaws.com/sample/attendanceandabsence/employee";
		
		RestTemplate rest = new RestTemplate();
		
		ResponseEntity<String> response = rest.getForEntity(url, String.class);

		String json = response.getBody();

		ObjectMapper mapper = new ObjectMapper();

		AttendanceData[] employeeList = mapper.readValue(json, AttendanceData[].class);

		return employeeList;

	}
	
	//社員詳細
	@PostMapping("/details")
	public String attendanceDetails(@RequestParam("id") int picId, Model model) throws IOException{
		
		List<ClockData> attendClockList = attendanceService.getAttendClock(picId);
		
		model.addAttribute("attendClockList", attendClockList);
		
		return "attendanceDatails";
		
	}

}
