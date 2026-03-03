package com.freightfox.transporter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaneRequestDTO {

    private Long id;
    private String origin;
    private String destination;
}