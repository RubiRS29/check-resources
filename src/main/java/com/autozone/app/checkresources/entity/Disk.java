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
public class Disk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String uuid;
    private String filesSystem;
    private String size;
    private String used;
    private String avail;
    private Double percent;
    private String mountedOn;
    private Boolean notify = false;
    private Boolean isAlmostFull = true;

    @ManyToOne(cascade=CascadeType.ALL)
    private Server server;

    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

}
