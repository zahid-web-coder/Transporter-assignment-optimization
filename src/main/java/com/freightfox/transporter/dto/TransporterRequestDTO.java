package com.freightfox.transporter.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransporterRequestDTO {

    private Long id;
    private String name;
    private List<LaneQuoteRequestDTO> laneQuotes;
}