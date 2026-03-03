package com.freightfox.transporter.service;

import com.freightfox.transporter.dto.AssignmentResponseDTO;
import com.freightfox.transporter.dto.InputRequestDTO;

public interface TransporterService {

    void saveInputData(InputRequestDTO request);

    AssignmentResponseDTO getOptimalAssignment(Integer maxTransporters);
}