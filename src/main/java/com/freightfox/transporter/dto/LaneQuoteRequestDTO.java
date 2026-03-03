package com.freightfox.transporter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaneQuoteRequestDTO {

    private Long laneId;
    private Double quote;
}