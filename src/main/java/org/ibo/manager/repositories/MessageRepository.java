package org.ibo.manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.ibo.manager.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
