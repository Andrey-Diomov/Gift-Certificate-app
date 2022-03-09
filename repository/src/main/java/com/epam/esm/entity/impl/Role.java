package com.epam.esm.entity.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Role extends AbstractEntity{

    @Column
    private String name;
}
