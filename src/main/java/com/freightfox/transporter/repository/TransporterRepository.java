package com.freightfox.transporter.repository;

import com.freightfox.transporter.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}