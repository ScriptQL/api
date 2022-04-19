package com.scriptql.scriptqlapi.utils.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public abstract class AbstractEntity {

    @Id
    private Long id;

    private LocalDateTime creationDate;

}
