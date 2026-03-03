package com.freightfox.transporter.algorithm;

import com.freightfox.transporter.dto.AssignmentResponseDTO;
import com.freightfox.transporter.model.Lane;
import com.freightfox.transporter.model.LaneQuote;
import com.freightfox.transporter.model.Transporter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentOptimizerTest {

    @Test
    void shouldMinimizeTotalCost() {

        Lane lane1 = Lane.builder()
                .id(1L)
                .origin("A")
                .destination("B")
                .build();

        Transporter t1 = Transporter.builder()
                .id(1L)
                .name("T1")
                .build();

        Transporter t2 = Transporter.builder()
                .id(2L)
                .name("T2")
                .build();

        LaneQuote q1 = LaneQuote.builder()
                .lane(lane1)
                .transporter(t1)
                .quote(1000.0)
                .build();

        LaneQuote q2 = LaneQuote.builder()
                .lane(lane1)
                .transporter(t2)
                .quote(2000.0)
                .build();

        AssignmentResponseDTO result = AssignmentOptimizer.optimize(
                List.of(lane1),
                List.of(t1, t2),
                List.of(q1, q2),
                2
        );

        assertEquals("success", result.getStatus());
        assertEquals(1000.0, result.getTotalCost());

        // Because of maximize usage logic, both transporters selected
        assertEquals(2, result.getSelectedTransporters().size());
        assertTrue(result.getSelectedTransporters().containsAll(List.of(1L, 2L)));
    }

    @Test
    void shouldMaximizeTransporterUsageOnTieCost() {

        Lane lane1 = Lane.builder()
                .id(1L)
                .origin("A")
                .destination("B")
                .build();

        Transporter t1 = Transporter.builder()
                .id(1L)
                .name("T1")
                .build();

        Transporter t2 = Transporter.builder()
                .id(2L)
                .name("T2")
                .build();

        // Same cost for both transporters
        LaneQuote q1 = LaneQuote.builder()
                .lane(lane1)
                .transporter(t1)
                .quote(1000.0)
                .build();

        LaneQuote q2 = LaneQuote.builder()
                .lane(lane1)
                .transporter(t2)
                .quote(1000.0)
                .build();

        AssignmentResponseDTO result = AssignmentOptimizer.optimize(
                List.of(lane1),
                List.of(t1, t2),
                List.of(q1, q2),
                2
        );

        assertEquals("success", result.getStatus());
        assertEquals(1000.0, result.getTotalCost());

        // Should choose both because cost is equal and we maximize usage
        assertEquals(2, result.getSelectedTransporters().size());
        assertTrue(result.getSelectedTransporters().containsAll(List.of(1L, 2L)));
    }

    @Test
    void shouldFailWhenNoCoveragePossible() {

        Lane lane1 = Lane.builder()
                .id(1L)
                .origin("A")
                .destination("B")
                .build();

        Transporter t1 = Transporter.builder()
                .id(1L)
                .name("T1")
                .build();

        // No quotes → impossible to assign
        AssignmentResponseDTO result = AssignmentOptimizer.optimize(
                List.of(lane1),
                List.of(t1),
                List.of(),
                1
        );

        assertEquals("failed", result.getStatus());
        assertEquals(0.0, result.getTotalCost());
        assertTrue(result.getSelectedTransporters().isEmpty());
        assertTrue(result.getAssignments().isEmpty());
    }
}