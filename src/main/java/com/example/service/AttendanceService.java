package com.example.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.data.AttendanceData;
import com.example.data.ClockData;
import com.example.repositry.AttendanceRepository;

@Service
public class AttendanceService {
	
	private	final AttendanceRepository attendanceRepository;
	
	public AttendanceService(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}
		
	public List<AttendanceData> getEmployee() throws IOException {
		
		AttendanceData[] employeeList = attendanceRepository.getEmployee();
		
		return Arrays.asList(employeeList);
		
	}
	
	public List<ClockData> getAttendClock(int picId) throws IOException{
		
		ClockData[] attendClockList = attendanceRepository.getAttendClock(picId);
		
		return Arrays.asList(attendClockList);
		
	}

}
