package com.autozone.app.checkresources.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CpuDto {

    private Long id;
    private String uuid;
    private Integer cores;
}
