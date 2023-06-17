package org.ibo.manager.transactions;

import lombok.Getter;
import org.ibo.manager.models.Message;
import org.ibo.manager.repositories.Database;

import java.time.LocalDateTime;

public abstract class SendMessageTransaction implements Transaction {
    @Getter
    private Message message;

    public SendMessageTransaction( String contents ) {
        // IMPORTANT: As it turns out since we're using MySQL it has limited precision for split seconds.
        // So we decided to remove them for the tests for now.
        this.message = new Message( contents, getSender(), getReceiver(), LocalDateTime.now().withNano( 0 ), false );
    }

    @Override
    public void execute() {
        Database.addMessage( message );
    }

    abstract String getSender();
    abstract String getReceiver();
}
