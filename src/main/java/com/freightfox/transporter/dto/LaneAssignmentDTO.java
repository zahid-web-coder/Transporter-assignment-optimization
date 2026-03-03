package com.freightfox.transporter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaneAssignmentDTO {

    private Long laneId;
    private Long transporterId;
}