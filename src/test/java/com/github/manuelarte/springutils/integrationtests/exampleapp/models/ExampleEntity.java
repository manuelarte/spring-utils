package com.github.manuelarte.springutils.integrationtests.exampleapp.models;

import com.github.manuelarte.springutils.constraints.groups.New;
import com.github.manuelarte.springutils.constraints.groups.PartialUpdate;
import com.github.manuelarte.springutils.constraints.groups.Update;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;


@Entity
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class ExampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = {New.class, Update.class, PartialUpdate.class})
    private Long id;

    @NotNull(groups = {New.class, Update.class})
    private String name;

    @NotNull(groups = {New.class, Update.class})
    private String surname;
}
