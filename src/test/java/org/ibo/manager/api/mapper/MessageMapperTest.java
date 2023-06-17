package org.ibo.manager.api.mapper;

import org.ibo.manager.Util;
import org.ibo.manager.api.model.MessageDTO;
import org.ibo.manager.models.Message;
import org.ibo.manager.transactions.SendMessageToWaiterTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class MessageMapperTest {
    private final MessageMapper messageMapper;

    @Autowired
    public MessageMapperTest( MessageMapper messageMapper ) {
        this.messageMapper = messageMapper;
    }

    @Test
    void testMessageToMessageDTO() {
        Message message = new SendMessageToWaiterTransaction( "Hi Kids!" ).getMessage();

        MessageDTO messageDTO = messageMapper.messageToMessageDTO( message );

        assertEquals( message.getContents(), messageDTO.getContents() );
        assertEquals( message.getSender(), messageDTO.getSender() );
        assertEquals( message.getReceiver(), messageDTO.getReceiver() );
        assertEquals( message.getCreatedAt(), messageDTO.getCreatedAt() );
        assertEquals( message.getSeen(), messageDTO.isSeen() );
    }

    @Test
    void testMessageDTOToMessage() {
        MessageDTO messageDTO = new MessageDTO( "Hi Kids!", Util.CHAT_USER_MANAGER, Util.CHAT_USER_WAITER, LocalDateTime.now(), false );

        Message message = messageMapper.messageDTOToMessage( messageDTO );

        assertEquals( messageDTO.getContents(), message.getContents() );
        assertEquals( messageDTO.getSender(), message.getSender() );
        assertEquals( messageDTO.getReceiver(), message.getReceiver() );
        assertEquals( messageDTO.getCreatedAt(), message.getCreatedAt() );
        assertEquals( messageDTO.isSeen(), message.getSeen() );
    }
}
