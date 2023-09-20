package com.example.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.data.AttendanceData;
import com.example.data.ClockData;
import com.example.repositry.AttendanceRepository;
import com.example.service.AttendanceService;

@Controller
public class AttendanceController {

	public final AttendanceService attendanceService;
	private final AttendanceRepository attendanceRepository;

	public AttendanceController(AttendanceService attendanceService, AttendanceRepository attendanceRepository) {
		this.attendanceService = attendanceService;
		this.attendanceRepository = attendanceRepository;
	}

	//社員一覧:Tableに社員情報employeeListを渡す	
	@GetMapping("/attendanceTable")
	public String attendanceTable(Model model) throws IOException {

		List<String> employeeList = attendanceService.getEmployee();

		model.addAttribute("employeeList", employeeList);
		System.out.println("Controller method called");

		return "attendanceTable";
		

	}

	@GetMapping("/attendanceDetails/{id}")
	public String getAttendanceDetails(@PathVariable("id") int id, Model model) throws IOException {
		AttendanceData attendanceDetails = attendanceService.searchEmployee(id);
		model.addAttribute("attendanceDetails", attendanceDetails);
		return "attendanceDetails";
	}

	//社員新規登録
	@GetMapping("/attendanceNew")
	public String attendanceNew() {
		return "attendanceNew";
	}

	@PostMapping("/attendanceNew")
	public String addEmployee(@RequestParam("name") String NAME, @RequestParam("hometown") String HOMETOWN,
			@RequestParam("joining_month") String JOINING_MONTH) throws IOException {

		attendanceRepository.addEmployee(NAME, HOMETOWN, JOINING_MONTH);

		return "redirect:/employeeList";

	}

	//社員詳細
	@PostMapping("/details")
	public String attendanceDetails(@RequestParam("id") int employeeId, @RequestParam("picId") String picId,
			@RequestParam("submit") String submit,
			RedirectAttributes redirectAttributes) throws IOException {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String attendanceTime = now.format(formatter);

		ClockData newClockData = new ClockData();
		newClockData.setEmployee_id(employeeId);

		if ("in".equals(submit)) {
			newClockData.setClock_in(attendanceTime);
		}

		if ("break_start".equals(submit)) {
			newClockData.setBreak_start(attendanceTime);
		}

		if ("break_end".equals(submit)) {
			newClockData.setBreak_end(attendanceTime);
		}

		if ("out".equals(submit)) {
			newClockData.setClock_out(attendanceTime);
		}

		boolean success = attendanceService.getAttendClock(newClockData);

		if (success) {
			redirectAttributes.addAttribute("id", employeeId);
			return "redirect:/details/{id}";
		} else {
			return "error-page";
		}
	}

}
