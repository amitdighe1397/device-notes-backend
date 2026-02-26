package com.amit.device_notes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amit.device_notes.dto.CreateDeviceNoteRequest;
import com.amit.device_notes.dto.DeviceNoteResponse;
import com.amit.device_notes.service.DeviceNoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/devices/{deviceId}/notes")
@RequiredArgsConstructor
@Slf4j
public class DeviceNoteController {

	private final DeviceNoteService service;

	@PostMapping
	public DeviceNoteResponse createNote(@PathVariable Long deviceId,
			@RequestHeader(value = "X-User", required = false) String user,
			@RequestBody CreateDeviceNoteRequest request) {

		log.info("Incoming create note request deviceId={} user={}", deviceId, user);

		return service.createNote(deviceId, user, request);
	}

	@GetMapping
	public List<DeviceNoteResponse> getNotes(@PathVariable Long deviceId,
			@RequestParam(required = false) Integer limit) {

		log.info("Incoming list notes request deviceId={} limit={}", deviceId, limit);

		return service.getNotes(deviceId, limit);
	}
}