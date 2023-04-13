package com.inventory.management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "attachment")
@Getter
@Setter
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    private String type;
    private String mime;

    @Lob
    @Column
    private byte[] data;


}