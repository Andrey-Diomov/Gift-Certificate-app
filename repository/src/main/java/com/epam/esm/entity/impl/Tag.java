package com.epam.esm.entity.impl;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Tag extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, mappedBy = "tags")
    List<Certificate> certificates;
}