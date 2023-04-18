package com.autozone.app.checkresources.repository;

import com.autozone.app.checkresources.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    Optional<Server> findByHost(String hostName);

    Optional<Server> findByUuid(String uuid);

    List<Server> findAllByIsEnable(Boolean isEnable);

}
