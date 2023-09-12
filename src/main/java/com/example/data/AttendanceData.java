package com.example.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

//apiから読み込み
@Data
public class AttendanceData {

	@JsonProperty("id")
	private int id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("hometown")
	private String hometown;

	@JsonProperty("joining_month")
	private String joining_month;

	@JsonProperty("created_at")
	private String created_at;

	@JsonProperty("updated_at")
	private String updated_at;

}

//@Data
//public class AttendanceData {
//
//	@JsonProperty("id")
//	private int id;
//
//	@JsonProperty("name")
//	private String name;
//
//	@JsonProperty("hometown")
//	private String hometown;
//
//	@JsonProperty("joining_month")
//	private String joining_month;
//
//	@JsonProperty("created_at")
//	private String created_at;
//
//	@JsonProperty("updated_at")
//	private String updated_at;
//
//	@JsonProperty("employee_id")
//	private int employee_id;
//
//	@JsonProperty("clock_in")
//	private String clock_in;
//
//	@JsonProperty("clock_out")
//	private String clock_out;
//
//	@JsonProperty("break_start")
//	private String break_start;
//
//	@JsonProperty("break_end")
//	private String break_end;
//
//}
