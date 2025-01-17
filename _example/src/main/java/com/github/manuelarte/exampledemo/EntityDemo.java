package com.github.manuelarte.exampledemo;

import io.github.manuelarte.springutils.constraints.groups.New;
import io.github.manuelarte.springutils.constraints.groups.PartialUpdate;
import io.github.manuelarte.springutils.constraints.groups.Update;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

@Entity
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class EntityDemo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = {New.class, Update.class, PartialUpdate.class})
    private Long id;

    @NotNull(groups = {New.class, Update.class})
    private String name;

    @NotNull(groups = {New.class, Update.class})
    private String surname;

}
