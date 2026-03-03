package com.freightfox.transporter.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponseDTO {

    private String status;
    private Double totalCost;
    private List<LaneAssignmentDTO> assignments;
    private List<Long> selectedTransporters;
}