package com.autozone.app.checkresources.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiskDto {

    private Long id;
    private String uuid;
    private String filesSystem;
    private String size;
    private String used;
    private String avail;
    private Double percent;
    private String mountedOn;
    private Boolean notify;
    private Boolean isAlmostFull;
    private LocalDateTime updateDate;

}
