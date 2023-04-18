package com.autozone.app.checkresources.scheduler;


import com.autozone.app.checkresources.entity.Disk;
import com.autozone.app.checkresources.entity.Server;
import com.autozone.app.checkresources.repository.DiskRepository;
import com.autozone.app.checkresources.repository.ServerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.autozone.app.checkresources.service.ConnectionSshService.getInformation;

@Component
public class DiskInformationScheduler {

    private final ServerRepository serverRepository;

    private final DiskRepository diskRepository;

    private final Logger LOGGER = LogManager.getLogger(DiskInformationScheduler.class);

    public DiskInformationScheduler(ServerRepository serverRepository, DiskRepository diskRepository) {
        this.serverRepository = serverRepository;
        this.diskRepository = diskRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void getInformationFromServerSchedulerTask(){

        System.out.println("--------------- I'm running -----------------");

        List<Server> servers = serverRepository.findAllByIsEnable(true);

        if(servers.isEmpty()){
            LOGGER.warn("THERE ARE NOT SERVERS");
            return;
        }

        for (Server server : servers) {

            List<Disk> information = getInformation(server.getUserName(), server.getHost());

            if(server.getDiskPartitions().isEmpty()){

                server.setDiskPartitions(information);
            }else{

                List<Disk> disks = server.getDiskPartitions();

                List<Disk> diskToSave = information.stream()
                        .filter(type -> !disks.stream()
                                .filter(tp -> tp.getMountedOn().equals(type.getMountedOn())).findFirst().isPresent() )
                        .collect(Collectors.toList());

//                System.out.println(diskToSave);

                if(!diskToSave.isEmpty()){
                    disks.addAll(diskToSave);
                }

                for (Disk disk : disks) {
                    Disk ds = information.stream().filter(d -> d.getMountedOn().equals(disk.getMountedOn())).findFirst().get();
                    disk.setMountedOn(ds.getMountedOn());
                    disk.setAvail(ds.getAvail());
                    disk.setFilesSystem(ds.getFilesSystem());
                    disk.setPercent(ds.getPercent());
                    disk.setSize(ds.getSize());
                    disk.setUsed(ds.getUsed());
                }
            }
            serverRepository.save(server);

        }

    }

}
