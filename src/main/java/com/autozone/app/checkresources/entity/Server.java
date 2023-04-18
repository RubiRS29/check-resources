package com.autozone.app.checkresources.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String userName;
    private String host;

    private Boolean isEnable = false;

    @OneToOne
    private Ram ram;
    @OneToOne
    private Cpu cpu;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Disk> diskPartitions;

    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

}
