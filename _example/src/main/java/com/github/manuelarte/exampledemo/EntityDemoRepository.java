package com.github.manuelarte.exampledemo;

import io.github.manuelarte.springutils.repositories.CrpudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityDemoRepository extends CrpudRepository<EntityDemo, Long> {
}
