package com.freightfox.transporter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lane_quotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaneQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lane_id", nullable = false)
    private Lane lane;

    @ManyToOne
    @JoinColumn(name = "transporter_id", nullable = false)
    private Transporter transporter;

    private Double quote;
}