package com.github.manuelarte.exampledemo;

import io.github.manuelarte.springutils.constraints.Exists;
import io.github.manuelarte.springutils.constraints.groups.New;
import io.github.manuelarte.springutils.constraints.groups.PartialUpdate;
import io.github.manuelarte.springutils.constraints.groups.Update;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/entity-demo")
@Validated
@lombok.AllArgsConstructor
public class EntityDemoController {

    private final EntityDemoRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<EntityDemo> getById(@PathVariable @Exists(EntityDemo.class) Long id) {
        return ResponseEntity.ok(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @PostMapping
    public ResponseEntity<EntityDemo> save(@RequestBody @Validated(New.class) EntityDemo entityDemo) {
        final EntityDemo saved = repository.save(entityDemo);
        return ResponseEntity.created(URI.create(String.format("./entity-demo/%d", saved.getId()))).body(saved);
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityDemo> updateById(@PathVariable @Exists(EntityDemo.class) Long id, @RequestBody @Validated(Update.class) EntityDemo entityDemo) {
        entityDemo.setId(id);
        return ResponseEntity.ok(repository.save(entityDemo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityDemo> partialUpdateById(@PathVariable @Exists(EntityDemo.class) Long id, @RequestBody @Validated(PartialUpdate.class) EntityDemo entityDemo) {
        entityDemo.setId(id);
        return ResponseEntity.ok(repository.partialSave(id, entityDemo));
    }
}
