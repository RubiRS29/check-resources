package com.autozone.app.checkresources.service;

import com.autozone.app.checkresources.dto.DiskDto;
import com.autozone.app.checkresources.dto.ServerDto;
import com.autozone.app.checkresources.dto.ServerRequestDto;
import com.autozone.app.checkresources.entity.Server;

import java.util.List;
import java.util.Optional;

public interface ServerService {

    ServerDto createServer(ServerRequestDto serverRequestDto);

    ServerDto getServer(String uuid, String host);

    ServerDto getServerByHostName(String host);

    ServerDto getServerByUuid(String uuid);

    List<ServerDto> getAllServers();

    List<ServerDto> getServers();

    List<DiskDto> getAllDislByServer(Long serverId);


}
