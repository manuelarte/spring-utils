package com.github.manuelarte.springutils.integrationtests.exampleapp.controllers;

import com.github.manuelarte.springutils.constraints.Exists;
import com.github.manuelarte.springutils.constraints.groups.New;
import com.github.manuelarte.springutils.constraints.groups.PartialUpdate;
import com.github.manuelarte.springutils.constraints.groups.Update;
import com.github.manuelarte.springutils.integrationtests.exampleapp.models.ExampleEntity;
import com.github.manuelarte.springutils.integrationtests.exampleapp.repositories.ExampleEntityRepository;
import jakarta.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example-entity")
@Validated
@lombok.AllArgsConstructor
public class ExampleEntityController {

    private final ExampleEntityRepository repository;

    @PostMapping
    public ExampleEntity save(@Validated(value = {Default.class, New.class}) @RequestBody ExampleEntity newEntity) {
        return repository.save(newEntity);
    }

    @PostMapping("/{id}")
    public ExampleEntity update(@PathVariable @Exists(ExampleEntity.class) Long id, @Validated(value = {Default.class, Update.class}) @RequestBody ExampleEntity updateEntity) {
        updateEntity.setId(id);
        return repository.save(updateEntity);
    }

    @PatchMapping("/{id}")
    public ExampleEntity partialUpdate(@PathVariable @Exists(ExampleEntity.class) Long id, @Validated(value = {Default.class, PartialUpdate.class}) @RequestBody ExampleEntity partialUpdateEntity) {
        partialUpdateEntity.setId(id);
        return repository.save(partialUpdateEntity);
    }

}
