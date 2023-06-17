package org.ibo.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.ibo.manager.models.Message;
import org.ibo.manager.repositories.Database;
import org.ibo.manager.services.WaiterChatService;
import org.ibo.manager.transactions.ReadMessagesTransaction;
import org.ibo.manager.transactions.SendMessageToWaiterTransaction;
import org.ibo.manager.transactions.SendMessageTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ibo.manager.Util;
import org.ibo.manager.gui.views.MessagePane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@FxmlView("messenger.fxml")
public class MessengerController extends Controller {
    public VBox root;
    public VBox messagesBox;
    public JFXTextArea enteredMessageArea;
    public JFXButton sendMessageButton;

    private List<Message> todayMessages = new ArrayList<>();
    private final WaiterChatService waiterChatService;

    @Autowired
    public MessengerController( WaiterChatService waiterChatService ) {
        this.waiterChatService = waiterChatService;
    }

    public void handleEnterKey( KeyEvent keyEvent ) {
        if ( keyEvent.getCode().equals( KeyCode.ENTER ) ) {
            if ( keyEvent.isShiftDown() ) {
                enteredMessageArea.appendText( "\n" );
                return;
            }

            // get rid of pressed Enter
            enteredMessageArea.deletePreviousChar();
            sendMessageButton.fire();
        }
    }

    public void sendMessage( ActionEvent actionEvent ) {
        SendMessageTransaction sendMessageTransaction = new SendMessageToWaiterTransaction( enteredMessageArea.getText() );
        if ( waiterChatService.isOnline() ) {
            Message message = sendMessageTransaction.getMessage();
            message.setSeen();
            waiterChatService.addPendingMessage( message );
        }
        sendMessageTransaction.execute();
        addMessage( sendMessageTransaction.getMessage() );
    }

    public void addMessage( Message message ) {
        messagesBox.getChildren().add( new MessagePane( message ) );
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        if ( isManagerOnline() && messagesBox.getChildren().size() != getTodayMessages().size() )
            updateMessages();
    }

    private List<Message> getTodayMessages() {
        return Database.getMessagesAt( LocalDate.now() );
    }

    public void readMessages() {
        new ReadMessagesTransaction( Util.CHAT_USER_MANAGER ).execute();
        updateMessages();
    }

    public void updateMessages() {
        todayMessages = getTodayMessages();
        fillMessagesBox();
    }

    private void fillMessagesBox() {
        messagesBox.getChildren().clear();
        for ( Message message : todayMessages )
            addMessage( message );
    }

    public void setWaiterMessagesRead() {
        for ( Node node : messagesBox.getChildren() ) {
            MessagePane messagePane = (MessagePane)node;
            messagePane.setSeen();
        }
    }

    public boolean isManagerOnline() {
        try {
            return getScene().getWindow().isShowing();
        }
        catch ( Exception e ) {
            return false;
        }
    }
}
