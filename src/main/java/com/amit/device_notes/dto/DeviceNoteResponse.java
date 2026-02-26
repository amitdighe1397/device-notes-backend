package com.amit.device_notes.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceNoteResponse {

	private Long id;
	private Long deviceId;
	private String note;
	private String createdBy;
	private LocalDateTime createdAt;
}
