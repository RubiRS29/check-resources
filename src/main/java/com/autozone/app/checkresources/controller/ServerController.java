package com.autozone.app.checkresources.controller;

import com.autozone.app.checkresources.dto.ServerDto;
import com.autozone.app.checkresources.dto.ServerRequestDto;
import com.autozone.app.checkresources.response.Response;
import com.autozone.app.checkresources.response.ResponseHandler;
import com.autozone.app.checkresources.service.ServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/admin/check_resources")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response> createAServerConnection(@RequestBody ServerRequestDto serverRequestDto){
        System.out.println(serverRequestDto);
        ServerDto server = serverService.createServer(serverRequestDto);

        return ResponseHandler.response(server, "created");

    }

    @GetMapping(value = "/server/{host}")
    public String getServerDisks(@PathVariable String host, Model model){

        System.out.println(host);

        model.addAttribute("disks", serverService.getServerByHostName(host));

        System.out.println(serverService.getServerByHostName(host));
        return "list_servers";

    }


}
