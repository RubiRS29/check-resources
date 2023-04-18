package com.autozone.app.checkresources.controller;

import com.autozone.app.checkresources.dto.DiskDto;
import com.autozone.app.checkresources.dto.ServerDto;
import com.autozone.app.checkresources.dto.ServerResponseDto;
import com.autozone.app.checkresources.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ServersController {

    private final ServerService serverService;

    public ServersController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping("/servers")
    public String listOfServers(Model model){
        List<ServerResponseDto> servers = new ArrayList<>();

        for (ServerDto server : serverService.getServers()) {
            ServerResponseDto srd = new ServerResponseDto(
                    server.getId(),
                    server.getUuid(),
                    server.getHost(),
                    server.getDiskPartitions().size(),
                    server.getIsEnable(),
                    server.getUpdateDate()
            );
            servers.add(srd);
        }

        model.addAttribute("servers", servers);
        return "list_servers";

    }

    @GetMapping("/server")
    public String getServerInformation(@RequestParam(required = false) String uuid,
                                       @RequestParam(required = false) String serverName, Model model){

        ServerDto server = serverService.getServer(uuid, serverName);

        List<DiskDto> diskPartitions = server.getDiskPartitions();

        List<DiskDto> diskPartitionsInDanger = diskPartitions.stream()
                .filter(disk -> disk.getPercent() > 70 && disk.getNotify())
                .collect(Collectors.toList());

        if (!diskPartitionsInDanger.isEmpty()){
            diskPartitionsInDanger = diskPartitionsInDanger.subList(0,diskPartitionsInDanger.size());
        }

        model.addAttribute("disks", diskPartitions);
        model.addAttribute("serverName", server.getHost());
        model.addAttribute("diskPartitionsInDanger", diskPartitionsInDanger);

        return "server";

    }
}
