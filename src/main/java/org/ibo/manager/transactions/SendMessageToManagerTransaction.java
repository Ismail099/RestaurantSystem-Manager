package org.ibo.manager.transactions;

import org.ibo.manager.Util;

public class SendMessageToManagerTransaction extends SendMessageTransaction {

    public SendMessageToManagerTransaction( String contents ) {
        super( contents );
    }

    @Override
    String getSender() {
        return Util.CHAT_USER_WAITER;
    }

    @Override
    String getReceiver() {
        return Util.CHAT_USER_MANAGER;
    }
}
