package org.ibo.manager.transactions;

import org.ibo.manager.Util;

public class SendMessageToWaiterTransaction extends SendMessageTransaction {

    public SendMessageToWaiterTransaction( String contents ) {
        super( contents );
    }

    @Override
    String getSender() {
        return Util.CHAT_USER_MANAGER;
    }

    @Override
    String getReceiver() {
        return Util.CHAT_USER_WAITER;
    }
}
