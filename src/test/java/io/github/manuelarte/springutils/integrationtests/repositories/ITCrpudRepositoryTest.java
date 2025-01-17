package io.github.manuelarte.springutils.integrationtests.repositories;

import io.github.manuelarte.springutils.integrationtests.exampleapp.MyExampleApp;
import io.github.manuelarte.springutils.integrationtests.exampleapp.models.ExampleEntity;
import io.github.manuelarte.springutils.integrationtests.exampleapp.repositories.ExampleEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MyExampleApp.class)
public class ITCrpudRepositoryTest {

    @Autowired
    private ExampleEntityRepository repository;

    @Test
    void testPartialSaveName() {
        final ExampleEntity original = new ExampleEntity(null, "Manuel", "Github");
        final ExampleEntity saved = repository.save(original);

        final ExampleEntity partialUpdateSurname = new ExampleEntity(saved.getId(), null, "Doncel");
        final ExampleEntity partiallyUpdated = repository.partialSave(saved.getId(), partialUpdateSurname);

        final ExampleEntity expected = new ExampleEntity(saved.getId(), original.getName(), partialUpdateSurname.getSurname());
        assertEquals(partiallyUpdated, expected, "Expecting only the surname updated");
    }
}
