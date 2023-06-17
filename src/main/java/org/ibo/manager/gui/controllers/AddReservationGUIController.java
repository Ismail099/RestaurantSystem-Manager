package org.ibo.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.AddReservationTransaction;
import org.ibo.manager.transactions.ImmediateReservationTransaction;
import org.ibo.manager.transactions.ScheduledReservationTransaction;
import org.springframework.stereotype.Component;
import org.ibo.manager.Util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Component
@FxmlView("add_reservation.fxml")
public class AddReservationGUIController extends Controller {
    // TODO use Alert to show errors instead of exceptions
    public ToggleGroup reservationType;
    public ChoiceBox<Long> customerSelector;
    public ChoiceBox<Long> tableSelector;
    public JFXDatePicker reservationDatePicker;
    public JFXTimePicker reservationTimePicker;
    public GridPane root;
    public JFXButton reservationButton;
    private LocalDate reservationDate = LocalDate.now();

    public void initialize() {
        tableSelector.setOnShowing( e -> fillTableSelector( reservationDate ) );
        customerSelector.setOnShowing( e -> fillCustomerSelector() );
        BooleanBinding customerOrTableNullProperty = customerSelector.valueProperty().isNull().or( tableSelector.valueProperty().isNull() );
        reservationButton.disableProperty().bind( customerOrTableNullProperty );
    }

    private void fillTableSelector( LocalDate date ) {
        tableSelector.getItems().clear();
        if ( date == null )
            return;
        Set<Long> freeTables = Database.getFreeTablesOn( date );

        tableSelector.getItems().addAll( freeTables );
    }

    private void fillCustomerSelector() {
        customerSelector.getItems().clear();
        customerSelector.getItems().addAll( Database.getCustomersId() );
    }

    public void activateImmediateMode( ActionEvent actionEvent ) {
        reservationDate = LocalDate.now();
        reservationDatePicker.setDisable( true );
        reservationTimePicker.setDisable( true );
    }

    public void activateScheduledMode( ActionEvent actionEvent ) {
        reservationDate = null;
        reservationDatePicker.setDisable( false );
        reservationTimePicker.setDisable( false );
        tableSelector.getItems().clear();
    }

    public void reserve( ActionEvent actionEvent ) {
        Long tableId = tableSelector.getValue();
        Long customerId = customerSelector.getValue();

        try {
            AddReservationTransaction addReservationTransaction = getReservationTransaction( tableId, customerId );
            addReservationTransaction.execute();
            close( actionEvent );
        }
        catch ( IllegalArgumentException ex ) {
            Util.createAlert( "Error", ex.getMessage(), getScene().getWindow(), ButtonType.CLOSE ).showAndWait();
        }
    }

    private AddReservationTransaction getReservationTransaction( Long tableId, Long customerId ) {
        AddReservationTransaction addReservationTransaction;

        if ( isImmediateReservation() ) {
            addReservationTransaction = new ImmediateReservationTransaction( tableId, customerId );
        }
        else {
            LocalDate reservationDate = reservationDatePicker.getValue();
            LocalTime reservationTime = reservationTimePicker.getValue();
            addReservationTransaction =  new ScheduledReservationTransaction( tableId, customerId, reservationDate, reservationTime );
        }

        return addReservationTransaction;
    }

    private boolean isImmediateReservation() {
        return reservationDatePicker.isDisabled();
    }

    public void close( ActionEvent actionEvent ) {
        customerSelector.setValue( null );
        tableSelector.setValue( null );
        closeWindow();
    }

    public void updateTableSelector( ActionEvent actionEvent ) {
        reservationDate = reservationDatePicker.getValue();
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {

    }
}
