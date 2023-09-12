package com.example.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClockData {

	@JsonProperty("id")
	private int id;

	@JsonProperty("employee_id")
	private int employee_id;

	@JsonProperty("clock_in")
	private String clock_in;

	@JsonProperty("clock_out")
	private String clock_out;

	@JsonProperty("break_start")
	private String break_start;

	@JsonProperty("break_end")
	private String break_end;

	private AttendanceData attendanceData;

}
