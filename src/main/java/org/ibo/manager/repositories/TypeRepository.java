package org.ibo.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ibo.manager.models.Type;

public interface TypeRepository extends CrudRepository<Type, String> {
}
