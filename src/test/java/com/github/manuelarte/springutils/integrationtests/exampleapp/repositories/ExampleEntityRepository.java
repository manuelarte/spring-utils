package com.github.manuelarte.springutils.integrationtests.exampleapp.repositories;

import com.github.manuelarte.springutils.integrationtests.exampleapp.models.ExampleEntity;
import com.github.manuelarte.springutils.repositories.CrpudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleEntityRepository extends CrpudRepository<ExampleEntity, Long> {
}
