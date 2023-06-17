package org.ibo.manager.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxmlView;
import org.ibo.manager.models.Delivery;
import org.ibo.manager.models.Invoice;
import org.ibo.manager.repositories.Database;
import org.ibo.manager.transactions.CheckOutTransaction;
import org.ibo.manager.transactions.DeleteDeliveryTransaction;
import org.springframework.stereotype.Component;
import org.ibo.manager.Util;
import org.ibo.manager.gui.models.WindowContainer;

import java.time.LocalDate;
import java.util.List;

@Component
@FxmlView("deliveries.fxml")
public class DeliveryController extends Controller {
    public VBox root;
    public TableView<Delivery> deliveriesTable;
    public TableColumn<Delivery, String> customerNameColumn;
    public TableColumn<Delivery, Double> totalColumn;
    public TableColumn<Delivery, LocalDate> dateColumn;
    public TableColumn<Delivery, String> addressColumn;
    public JFXButton cancelDeliveryButton;
    public JFXButton checkOutDeliveryButton;

    public void initialize() {
        customerNameColumn.setCellValueFactory( new PropertyValueFactory<>( "customerName" ) );
        totalColumn.setCellValueFactory( new PropertyValueFactory<>( "total" ) );
        dateColumn.setCellValueFactory( new PropertyValueFactory<>( "date" ) );
        addressColumn.setCellValueFactory( new PropertyValueFactory<>( "deliveryAddress" ) );

        enableCheckOutButtonForTodayDeliveries();
        cancelDeliveryButton.disableProperty().bind( deliveriesTable.getSelectionModel().selectedItemProperty().isNull() );
        fillDeliveriesTable();
    }

    private void enableCheckOutButtonForTodayDeliveries() {
        deliveriesTable.getSelectionModel().selectedItemProperty().addListener( ( observableValue, delivery, t1 ) -> {
            if ( t1 != null )
                checkOutDeliveryButton.setDisable( !t1.getDate().equals( LocalDate.now() ) );
            else
                checkOutDeliveryButton.setDisable( true );
        } );
    }

    private void fillDeliveriesTable() {
        deliveriesTable.getItems().clear();
        List<Delivery> deliveries = Database.getDeliveries();
        for ( Delivery delivery : deliveries ) {
            deliveriesTable.getItems().add( delivery );
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        int currentNumberOfDeliveries = deliveriesTable.getItems().size();

        if ( currentNumberOfDeliveries != Database.getDeliveries().size() ) {
            fillDeliveriesTable();
            Util.createTrayNotification( "New Delivery Has Arrived!",  "Please see Manager for more details" );
        }
    }

    public void cancelDelivery( ActionEvent actionEvent ) {
        Delivery delivery = deliveriesTable.getSelectionModel().getSelectedItem();

        new DeleteDeliveryTransaction( delivery.getCustomerId(), delivery.getDate() ).execute();

        fillDeliveriesTable();
    }

    public void checkOutDelivery( ActionEvent actionEvent ) {
        Delivery delivery = deliveriesTable.getSelectionModel().getSelectedItem();
        CheckOutTransaction checkOutTransaction = new CheckOutTransaction( delivery );
        checkOutTransaction.execute();

        showAndPrintInvoice( checkOutTransaction.getInvoice() );

        fillDeliveriesTable();
    }

    // Remove Duplication with ReservationPane
    private void showAndPrintInvoice( Invoice invoice ) {
        WindowContainer invoiceWindow = Util.getWindowContainer( "Invoice Summary" );
        InvoiceController invoiceController = (InvoiceController) invoiceWindow.getController();
        invoiceController.setInvoice( invoice );
        invoiceWindow.getStage().setResizable( false );
        invoiceWindow.getStage().showAndWait();
        Util.tryPrintNode( invoiceController.getRoot() );
    }
}
