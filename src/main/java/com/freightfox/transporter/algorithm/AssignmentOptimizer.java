package com.freightfox.transporter.algorithm;

import com.freightfox.transporter.dto.AssignmentResponseDTO;
import com.freightfox.transporter.dto.LaneAssignmentDTO;
import com.freightfox.transporter.model.Lane;
import com.freightfox.transporter.model.LaneQuote;
import com.freightfox.transporter.model.Transporter;

import java.util.*;
import java.util.stream.Collectors;

public class AssignmentOptimizer {

    public static AssignmentResponseDTO optimize(
            List<Lane> lanes,
            List<Transporter> transporters,
            List<LaneQuote> quotes,
            int maxTransporters
    ) {

        Map<Long, Map<Long, Double>> transporterLaneMap = new HashMap<>();

        for (LaneQuote quote : quotes) {
            transporterLaneMap
                    .computeIfAbsent(quote.getTransporter().getId(), k -> new HashMap<>())
                    .put(quote.getLane().getId(), quote.getQuote());
        }

        List<Long> transporterIds = transporters.stream()
                .map(Transporter::getId)
                .collect(Collectors.toList());

        double minCost = Double.MAX_VALUE;
        List<Long> bestSelection = new ArrayList<>();
        List<LaneAssignmentDTO> bestAssignments = new ArrayList<>();

        List<List<Long>> combinations = generateCombinations(transporterIds, maxTransporters);

        for (List<Long> combo : combinations) {

            Map<Long, LaneAssignmentDTO> laneAssignmentMap = new HashMap<>();
            double totalCost = 0;
            boolean valid = true;

            for (Lane lane : lanes) {

                double bestLaneCost = Double.MAX_VALUE;
                Long selectedTransporter = null;

                for (Long transporterId : combo) {

                    Map<Long, Double> laneMap = transporterLaneMap.get(transporterId);

                    if (laneMap != null && laneMap.containsKey(lane.getId())) {

                        double cost = laneMap.get(lane.getId());

                        if (cost < bestLaneCost) {
                            bestLaneCost = cost;
                            selectedTransporter = transporterId;
                        }
                    }
                }

                if (selectedTransporter == null) {
                    valid = false;
                    break;
                }

                totalCost += bestLaneCost;

                laneAssignmentMap.put(
                        lane.getId(),
                        LaneAssignmentDTO.builder()
                                .laneId(lane.getId())
                                .transporterId(selectedTransporter)
                                .build()
                );
            }

            if (valid) {

                if (totalCost < minCost) {

                    minCost = totalCost;
                    bestSelection = new ArrayList<>(combo);
                    bestAssignments = new ArrayList<>(laneAssignmentMap.values());

                } else if (totalCost == minCost
                        && combo.size() > bestSelection.size()) {

                    // Tie on cost → prefer more transporters
                    bestSelection = new ArrayList<>(combo);
                    bestAssignments = new ArrayList<>(laneAssignmentMap.values());
                }
            }
        }

        if (minCost == Double.MAX_VALUE) {
            return AssignmentResponseDTO.builder()
                    .status("failed")
                    .totalCost(0.0)
                    .assignments(Collections.emptyList())
                    .selectedTransporters(Collections.emptyList())
                    .build();
        }

        return AssignmentResponseDTO.builder()
                .status("success")
                .totalCost(minCost)
                .assignments(bestAssignments)
                .selectedTransporters(bestSelection)
                .build();
    }

    private static List<List<Long>> generateCombinations(List<Long> ids, int maxSize) {

        List<List<Long>> result = new ArrayList<>();
        int n = ids.size();

        for (int i = 1; i < (1 << n); i++) {

            List<Long> combo = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    combo.add(ids.get(j));
                }
            }

            if (combo.size() <= maxSize) {
                result.add(combo);
            }
        }

        return result;
    }
}