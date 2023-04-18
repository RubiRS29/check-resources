package com.autozone.app.checkresources.repository;

import com.autozone.app.checkresources.entity.Cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends JpaRepository<Cpu, Long> {
}
