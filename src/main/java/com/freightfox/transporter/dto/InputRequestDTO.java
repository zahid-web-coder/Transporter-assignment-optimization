package com.freightfox.transporter.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputRequestDTO {

    private List<LaneRequestDTO> lanes;
    private List<TransporterRequestDTO> transporters;
}