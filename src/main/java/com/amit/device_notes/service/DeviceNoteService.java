package com.amit.device_notes.service;

import java.util.List;

import com.amit.device_notes.dto.CreateDeviceNoteRequest;
import com.amit.device_notes.dto.DeviceNoteResponse;

public interface DeviceNoteService {

	DeviceNoteResponse createNote(Long deviceId, String user, CreateDeviceNoteRequest request);

	List<DeviceNoteResponse> getNotes(Long deviceId, Integer limit);
}