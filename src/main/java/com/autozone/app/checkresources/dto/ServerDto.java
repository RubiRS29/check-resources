package com.autozone.app.checkresources.dto;

import com.autozone.app.checkresources.entity.Cpu;
import com.autozone.app.checkresources.entity.Ram;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServerDto {

    private Long id;
    private String uuid;
    private String userName;
    private String host;
    private Boolean isEnable;
    private Ram ram;
    private Cpu cpu;
    private LocalDateTime updateDate;
    private List<DiskDto> diskPartitions;

}
