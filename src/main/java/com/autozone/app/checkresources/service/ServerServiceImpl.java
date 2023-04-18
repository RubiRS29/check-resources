package com.autozone.app.checkresources.service;

import com.autozone.app.checkresources.dto.DiskDto;
import com.autozone.app.checkresources.dto.ServerDto;
import com.autozone.app.checkresources.dto.ServerRequestDto;
import com.autozone.app.checkresources.entity.Disk;
import com.autozone.app.checkresources.entity.Server;
import com.autozone.app.checkresources.exceptions.BadRequestException;
import com.autozone.app.checkresources.exceptions.ObjectAlreadyExistsException;
import com.autozone.app.checkresources.exceptions.ResourceNotFoundException;
import com.autozone.app.checkresources.repository.CpuRepository;
import com.autozone.app.checkresources.repository.DiskRepository;
import com.autozone.app.checkresources.repository.RamRepository;
import com.autozone.app.checkresources.repository.ServerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static com.autozone.app.checkresources.mapper.ServerMapper.SERVER_MAPPER;
import static com.autozone.app.checkresources.service.ConnectionSshService.getInformation;

@Service
public class ServerServiceImpl implements ServerService{

    private final ServerRepository serverRepository;

    private final Logger LOGGER = LogManager.getLogger(ServerServiceImpl.class);

    public ServerServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public ServerDto createServer(ServerRequestDto serverRequestDto) {

        String uuid =  UUID.randomUUID().toString();
        Server server = new Server();

        LOGGER.info("CREATING SERVER");
        LOGGER.info(serverRequestDto.toString());

        if(!StringUtils.hasText(serverRequestDto.getUserName())
                || !StringUtils.hasText(serverRequestDto.getHost())){
            throw new BadRequestException("User or server is missing");
        }

        LOGGER.info("Finding server");

        Optional<Server> serverOptional = serverRepository.findByHost(serverRequestDto.getHost().trim().toLowerCase());

        if(serverOptional.isPresent()){
            throw new ObjectAlreadyExistsException(
                    String.format("The server %s already exists in the database", serverRequestDto.getHost()));
        }else {
            server = SERVER_MAPPER.dtoToServer(serverRequestDto);
        }

        server.setUuid(uuid);

//        List<Disk> information = getInformation(serverRequestDto.getUserName(), serverRequestDto.getHost());
//        server.setDiskPartitions(information);


        ServerDto serverDto = SERVER_MAPPER.serverToDto(serverRepository.save(server));

        return serverDto;
    }

    @Override
    public ServerDto getServer(String uuid, String host) {

//        LOGGER.info(String.format("-------- Getting Server By Host %s ------------", host));
        ServerDto server;

        if(StringUtils.hasText(uuid)){
            server = this.getServerByUuid(uuid);

        } else if (StringUtils.hasText(host)) {
            server = this.getServerByHostName(host);
        }else {
            throw new ResourceNotFoundException("Server was not found");
        }

        return server;
    }

    @Override
    public ServerDto getServerByUuid(String uuid) {

        LOGGER.info(String.format("-------- Getting Server By UUID %s ------------", uuid));

        Server server = serverRepository.findByUuid(uuid.trim())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Server was not found with uuid %s", uuid)));

        return SERVER_MAPPER.serverToDto(server);
    }

    @Override
    public ServerDto getServerByHostName(String host) {

        LOGGER.info(String.format("-------- Getting Server By HOST %s ------------", host));

        Server server = serverRepository.findByHost(host.trim())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Server was not found with host %s", host)));

        return SERVER_MAPPER.serverToDto(server);

    }



    @Override
    public List<ServerDto> getAllServers() {

        LOGGER.info("-------- Getting All Servers ------------");
        return SERVER_MAPPER.serversToDtos(serverRepository.findAllByIsEnable(true));

    }

    @Override
    public List<ServerDto> getServers() {

        LOGGER.info("-------- Getting All Servers ------------");
        return SERVER_MAPPER.serversToDtos(serverRepository.findAll());

    }



    @Override
    public List<DiskDto> getAllDislByServer(Long serverId) {
        return null;
    }
}
