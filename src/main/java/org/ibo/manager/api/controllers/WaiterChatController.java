package org.ibo.manager.api.controllers;

import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.ibo.manager.gui.controllers.MessengerController;
import org.ibo.manager.services.WaiterChatService;
import org.ibo.manager.transactions.ReadMessagesTransaction;
import org.ibo.manager.transactions.SendMessageToManagerTransaction;
import org.ibo.manager.transactions.SendMessageTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ibo.manager.Util;
import org.ibo.manager.api.model.MessageDTO;
import org.ibo.manager.api.model.MessageListDTO;

@Slf4j
@Controller
@RequestMapping("/api/chat")
public class WaiterChatController {
    private final WaiterChatService waiterChatService;
    private final MessengerController messengerController;

    @Autowired
    public WaiterChatController( WaiterChatService waiterChatService, MessengerController messengerController ) {
        this.waiterChatService = waiterChatService;
        this.messengerController = messengerController;
    }

    @PostMapping
    private ResponseEntity<MessageListDTO> processAcknowledgment() {
        waiterChatService.setAcknowledged();
        ResponseEntity<MessageListDTO> responseEntity = new ResponseEntity<>( new MessageListDTO( messengerController.isManagerOnline() ,waiterChatService.getPendingMessages() ), HttpStatus.OK );
        waiterChatService.clearPendingMessages();
        if ( Util.isGuiInitialized() ) {
            Platform.runLater( messengerController::setWaiterMessagesRead );
        }
        else {
            log.warn( "GUI not initialized. Testing Mode is assumed." );
        }
        return responseEntity;
    }

    @GetMapping(value = "/messages")
    private ResponseEntity<MessageListDTO> getMessages() {
        new ReadMessagesTransaction( Util.CHAT_USER_WAITER ).execute();
        waiterChatService.setAcknowledged();
        if ( Util.isGuiInitialized() ) {
            Platform.runLater( messengerController::updateMessages );
        }
        else {
            log.warn( "GUI not initialized. Testing Mode is assumed." );
        }
        return new ResponseEntity<>( new MessageListDTO( waiterChatService.getAllMessages() ), HttpStatus.OK );
    }

    @PostMapping(value = "/send")
    private ResponseEntity<String> postMessage( @RequestBody MessageDTO messageDTO ) {
        SendMessageTransaction sendMessageTransaction = new SendMessageToManagerTransaction( messageDTO.getContents() );
        sendMessageTransaction.execute();
        String responseBody = "Message Not Read";
        if ( Util.isGuiInitialized() && messengerController.isManagerOnline()  ) {
            Platform.runLater( () -> messengerController.addMessage( sendMessageTransaction.getMessage() ) );
            responseBody = "Message Read";
        }
        else {
            log.warn( "GUI not initialized. Testing Mode is assumed." );
        }
        return new ResponseEntity<>( responseBody, HttpStatus.CREATED );
    }
}
