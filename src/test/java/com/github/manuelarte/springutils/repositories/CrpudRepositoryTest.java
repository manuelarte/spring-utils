package com.github.manuelarte.springutils.repositories;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class CrpudRepositoryTest {

  @SuppressWarnings("unchecked")
  private final CrpudRepository<ParentEntity, Long> parentRepository = spy(CrpudRepository.class);
  @SuppressWarnings("unchecked")
  private final CrpudRepository<ChildEntity, Long> childRepository = spy(CrpudRepository.class);


  @Test
  void testPartialUpdate() {
    final ParentEntity original = new ParentEntity();
    original.firstName = "Manuel";
    original.lastName = "Doncel";
    when(parentRepository.findById(any())).thenReturn(Optional.of(original));
    when(parentRepository.save(any())).thenAnswer((Answer<ParentEntity>) invocation -> {
      Object[] args = invocation.getArguments();
      return (ParentEntity) args[0];
    });
    ParentEntity partialEntity = new ParentEntity();
    partialEntity.firstName = "New";
    final ParentEntity actual = parentRepository.partialSave(1L, partialEntity);
    final ParentEntity expected = new ParentEntity();
    expected.firstName = partialEntity.firstName;
    expected.lastName = original.lastName;
    assertEquals(expected, actual);
  }

  @Test
  void testPartialUpdateChildEntity() {
    final ChildEntity original = new ChildEntity(5);
    original.firstName = "Manuel";
    original.lastName = "Doncel";
    when(childRepository.findById(any())).thenReturn(Optional.of(original));
    when(childRepository.save(any())).thenAnswer((Answer<ChildEntity>) invocation -> {
      Object[] args = invocation.getArguments();
      return (ChildEntity) args[0];
    });
    ChildEntity partialEntity = new ChildEntity(18);
    partialEntity.firstName = "new name";
    final ChildEntity actual = childRepository.partialSave(1L, partialEntity);
    final ChildEntity expected = new ChildEntity(partialEntity.age);
    expected.firstName = partialEntity.firstName;
    expected.lastName = original.lastName;
    assertEquals(expected, actual);
  }

  @lombok.Data
  public static class ParentEntity {
    protected Long id;
    protected String firstName;
    protected String lastName;
  }

  @lombok.Data
  @lombok.EqualsAndHashCode(callSuper=true)
  @lombok.AllArgsConstructor
  @SuppressWarnings("MissingOverride")
  public static class ChildEntity extends ParentEntity {
    private int age;
  }

}
