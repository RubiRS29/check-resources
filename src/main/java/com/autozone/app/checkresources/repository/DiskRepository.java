package com.autozone.app.checkresources.repository;

import com.autozone.app.checkresources.entity.Disk;
import com.autozone.app.checkresources.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiskRepository extends JpaRepository<Disk, Long> {

    Optional<Disk> findByServer(Server server);
}
