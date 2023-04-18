package com.autozone.app.checkresources.service;

import com.autozone.app.checkresources.entity.Disk;
import com.autozone.app.checkresources.exceptions.InternalServerError;
import com.autozone.app.checkresources.repository.DiskRepository;
import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ConnectionSshService {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionSshService.class);
    public static List<Disk> getInformation(String user, String host) {

        JSch jsch = new JSch();
        List<String> diskInformation = new ArrayList<>();
        List<Disk> disks = new ArrayList<>();

        LOGGER.info("Get info Server");

        try {

            // Create a session to the remote server
            jsch.setKnownHosts(System.getProperty("user.home")+"/.ssh/known_hosts");
            jsch.addIdentity(System.getProperty("user.home")+"/.ssh/id_rsa");;

            Session session = jsch.getSession(user, host);
            LOGGER.info("---------- session created. -----------");

            // Set the configuration options for the session
            session.setConfig("StrictHostKeyChecking", "yes");

            // Connect to the remote server
            session.connect(10000);

            // Create a channel to run commands on the remote server
            Channel channel = session.openChannel("exec");

            // Set the command to run
            String command = "df -h";
            ((ChannelExec) channel).setCommand(command);

            // Connect the channel and start the command
            channel.connect();

            // Read the output from the command
            byte[] buffer = new byte[1024];

            StringBuilder filesString = new StringBuilder();

            while (true) {

                if (channel.isClosed()) {
                    int exitStatus = channel.getExitStatus();
                    LOGGER.info("Exit status: " + exitStatus);

                    break;
                }

                int bytesRead = channel.getInputStream().read(buffer);
                if (bytesRead > 0) {
                    filesString.append(new String(buffer, 0, bytesRead));
                }
            }

            diskInformation = Arrays.asList(filesString.toString().split("\n"));

            channel.disconnect();
            session.disconnect();



            diskInformation = diskInformation.subList(1, diskInformation.size());


            diskInformation.forEach(d -> {

                List<String> diskArray = Arrays.asList(d.split("\\s+"));

                Disk disk = new Disk();
                disk.setUuid(UUID.randomUUID().toString());
                disk.setFilesSystem(diskArray.get(0));
                disk.setSize(diskArray.get(1));
                disk.setUsed(diskArray.get(2));
                disk.setAvail(diskArray.get(3));
                disk.setPercent(Double.parseDouble(diskArray.get(4).replace("%", "")));
                disk.setMountedOn(diskArray.get(5));

                disks.add(disk);

            });

        } catch (JSchException | java.io.IOException e) {
            System.out.println(e.getMessage());
            throw new InternalServerError(e.getMessage());
        }

        return disks;
    }

}
