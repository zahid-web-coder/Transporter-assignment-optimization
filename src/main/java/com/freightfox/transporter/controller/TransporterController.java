package com.freightfox.transporter.controller;

import com.freightfox.transporter.dto.AssignmentRequestDTO;
import com.freightfox.transporter.dto.InputRequestDTO;
import com.freightfox.transporter.service.TransporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/transporters")
@RequiredArgsConstructor
public class TransporterController {

    private final TransporterService transporterService;

    @PostMapping("/input")
    public ResponseEntity<?> saveInputData(@RequestBody InputRequestDTO request) {

        transporterService.saveInputData(request);

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "message", "Input data saved successfully."
                )
        );
    }

    @PostMapping("/assignment")
    public ResponseEntity<?> getAssignment(
            @RequestBody AssignmentRequestDTO request
    ) {

        if (request.getMaxTransporters() == null || request.getMaxTransporters() <= 0) {
            throw new IllegalArgumentException("maxTransporters must be greater than 0");
        }

        return ResponseEntity.ok(
                transporterService.getOptimalAssignment(request.getMaxTransporters())
        );
    }
}