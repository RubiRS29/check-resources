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
public class ServerResponseDto {

    private Long id;
    private String uuid;
    private String host;
    private Integer disks;
    private Boolean isEnable;
    private LocalDateTime updateOn;
}
