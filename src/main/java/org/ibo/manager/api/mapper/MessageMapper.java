package org.ibo.manager.api.mapper;

import org.ibo.manager.models.Message;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.ibo.manager.api.model.MessageDTO;

@Mapper(componentModel = "spring")
@Component
public interface MessageMapper {

    MessageDTO messageToMessageDTO( Message message );

    default Message messageDTOToMessage( MessageDTO messageDTO ) {
        return new Message( messageDTO.getContents(),
                messageDTO.getSender(),
                messageDTO.getReceiver(),
                messageDTO.getCreatedAt(),
                messageDTO.isSeen()
        );
    }
}
