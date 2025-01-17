# Spring-Utils library

## 📝 Installation

Add the following dependency in your project to start using the features described below.

<details>
<summary>Gradle</summary>

```
implementation 'io.github.manuelarte.spring-utils:{latest-version}'
```
</details>
[source,bash]

## Overview

Some helpful validations and utilities to be used in your [Spring Boot][spring-boot] application. 

Check the features list below.

### Prerequisites

- Java 8 or above
- Spring Data

## Features

### Exists Constraint

The `@Exists` constraint can be used to **check if the entity exists before executing the method**. It checks whether the repository contains an entity with the specified id.
This allows you to abstract the validation logic from the business logic.

#### Prerequisites

- The constraint validations need to be executed, for example annotating your Controller with `@Validated`.
- The entity/document that is going to be checked needs to have a `@Repository` bean.
- The following dependency is needed: `implementation("org.springframework.boot:spring-boot-starter-validation")`

#### Example

Here there is an example of a controller that gets a Document by id. The @Exists contrains check whether the id exists before executing the method.

```java
@Validated
public class DocumentController {
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentEntity> findOne(
    @PathVariable @Exists(DocumentEntity.class) final String id) {
        return ResponseEntity.ok(documentService.findOne(id));
    }
}

@Repository
public interface DocumentEntityRepository extends CrudRepository<DocumentEntity, Long> {}
```

### @New, @PartialUpdate, @Update Validation Groups

Helper annotations to be used in your DTOs to be used
as [validation groups](https://jakarta.ee/specifications/bean-validation/).

#### Example

Imagine that you have an entity that you want to allow to be `created`, `updated` and `partially updated`.
By using validation groups, we can have the same DTO for the different CRUD endpoints. Here is an example:

```java
public class OneEntityDto {

    @Null(groups = {New.class, PartialUpdate.class})
    @NotNull(groups = Update.class)
    // id is mandatory for the @New and @Update validation group, but not for @PartialUpdate 
    private final Long id;

    @NotEmpty(groups = {New.class, Update.class})
    // firstName can't be empty for @New and @Update, but can be empty for @PartialUpdate
    private final String firstName;
    @NotEmpty(groups = {New.class, Update.class})
    // lastName can't be empty for @New and @Update, but can be empty for @PartialUpdate
    private final String lastName;

}

@RestController
@Validated
public class OneEntityController {
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OneEntity> saveOne(
            @Validated({Default.class, New.class}) @RequestBody final OneEntity saveEntity) {
        // saveEntity will be validated with the @New validation group
    }
    
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OneEntity> updateOne(
        @Validated({Default.class, Update.class}) @RequestBody final OneEntity updateEntity) {
        // updateEntity will be validated with the @Update validation group
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OneEntity> partialUpdateOne(
            @Validated({Default.class, PartialUpdate.class}) @RequestBody final OneEntity patchEntity) {
        // patchEntity will be validated with the @PartialUpdate validation group
    }
}
```

### FromAndToDate Constraint

`@FromAndToDate` constraint helps to validate two dates.

#### Class Level Example

```java
@FromAndToDate
public class EntityExample {
    @FromDate
    private final Date from;
    @ToDate
    private final Date to;
    ...
}
```

The constraint will check that the field from is lower than to the to parameter

#### Method Level Example

```java
@FromAndToDate
public void methodExample(final Date from, final Date to) {
    //...
}
```

The constraint will check that the parameters from and to match from is before than to.
By default, the constraint will check the 1st and 2nd parameters indexes. In case they are in a different index it should be set like this:

```java
@FromAndToDate(paramIndexes={x,y})
```

where `x` and `y` are the indexes of the parameters to be checked.

### CrupRepository

Extension for the [Spring Data Repository](https://docs.spring.io/spring-data/jpa/reference/index.html) to allow **partial updates**.

#### Prerequisites

- The entity needs to have a single field with `@Id` attribute

## 🤝 Contributing
Feel free to create a PR or suggest improvements or ideas.

[spring-boot](https://spring.io/projects/spring-boot)
