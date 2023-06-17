package org.ibo.manager.api.mapper;

import javax.annotation.processing.Generated;
import org.ibo.manager.api.model.MessageDTO;
import org.ibo.manager.models.Message;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-17T16:43:40+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDTO messageToMessageDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setContents( message.getContents() );
        messageDTO.setSender( message.getSender() );
        messageDTO.setReceiver( message.getReceiver() );
        messageDTO.setCreatedAt( message.getCreatedAt() );
        if ( message.getSeen() != null ) {
            messageDTO.setSeen( message.getSeen() );
        }

        return messageDTO;
    }
}
