package com.freightfox.transporter.service;

import com.freightfox.transporter.algorithm.AssignmentOptimizer;
import com.freightfox.transporter.dto.*;
import com.freightfox.transporter.model.*;
import com.freightfox.transporter.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransporterServiceImpl implements TransporterService {

    private final LaneRepository laneRepository;
    private final TransporterRepository transporterRepository;
    private final LaneQuoteRepository laneQuoteRepository;

    @Override
    @Transactional
    public void saveInputData(InputRequestDTO request) {

        laneQuoteRepository.deleteAll();
        transporterRepository.deleteAll();
        laneRepository.deleteAll();

        Map<Long, Lane> laneMap = new HashMap<>();

        for (LaneRequestDTO laneDTO : request.getLanes()) {

            Lane lane = Lane.builder()
                    .id(laneDTO.getId())
                    .origin(laneDTO.getOrigin())
                    .destination(laneDTO.getDestination())
                    .build();

            laneRepository.save(lane);
            laneMap.put(lane.getId(), lane);
        }

        for (TransporterRequestDTO transporterDTO : request.getTransporters()) {

            Transporter transporter = Transporter.builder()
                    .id(transporterDTO.getId())
                    .name(transporterDTO.getName())
                    .build();

            transporterRepository.save(transporter);

            for (LaneQuoteRequestDTO quoteDTO : transporterDTO.getLaneQuotes()) {

                Lane lane = laneMap.get(quoteDTO.getLaneId());

                if (lane == null) {
                    throw new RuntimeException("Invalid laneId: " + quoteDTO.getLaneId());
                }

                LaneQuote laneQuote = LaneQuote.builder()
                        .lane(lane)
                        .transporter(transporter)
                        .quote(quoteDTO.getQuote())
                        .build();

                laneQuoteRepository.save(laneQuote);
            }
        }
    }

    @Override
    public AssignmentResponseDTO getOptimalAssignment(Integer maxTransporters) {

        List<Lane> lanes = laneRepository.findAll();
        List<Transporter> transporters = transporterRepository.findAll();
        List<LaneQuote> quotes = laneQuoteRepository.findAll();

        if (lanes.isEmpty() || transporters.isEmpty()) {
            return AssignmentResponseDTO.builder()
                    .status("failed")
                    .totalCost(0.0)
                    .assignments(Collections.emptyList())
                    .selectedTransporters(Collections.emptyList())
                    .build();
        }

        AssignmentResponseDTO result = AssignmentOptimizer.optimize(
                lanes,
                transporters,
                quotes,
                maxTransporters
        );

        if (result.getTotalCost() == Double.MAX_VALUE) {
            return AssignmentResponseDTO.builder()
                    .status("failed")
                    .totalCost(0.0)
                    .assignments(Collections.emptyList())
                    .selectedTransporters(Collections.emptyList())
                    .build();
        }

        return result;
    }
}