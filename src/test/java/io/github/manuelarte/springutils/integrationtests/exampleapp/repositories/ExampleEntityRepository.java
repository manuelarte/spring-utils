package io.github.manuelarte.springutils.integrationtests.exampleapp.repositories;

import io.github.manuelarte.springutils.integrationtests.exampleapp.models.ExampleEntity;
import io.github.manuelarte.springutils.repositories.CrpudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleEntityRepository extends CrpudRepository<ExampleEntity, Long> {
}
