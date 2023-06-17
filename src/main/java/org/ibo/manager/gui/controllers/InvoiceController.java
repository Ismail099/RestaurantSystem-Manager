package org.ibo.manager.gui.controllers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import net.rgielen.fxweaver.core.FxmlView;
import org.ibo.manager.models.Customer;
import org.ibo.manager.models.Invoice;
import org.ibo.manager.models.Item;
import org.ibo.manager.repositories.Database;
import org.springframework.stereotype.Component;
import org.ibo.manager.Util;

import java.time.format.DateTimeFormatter;

@Component
@FxmlView("invoice.fxml")
public class InvoiceController extends Controller {
    public VBox root;
    public Label customerNameLabel;
    public Label tableIdLabel;
    public Label reservationDateTimeLabel;
    public Label totalLabel;
    public Label tableFeeLabel;
    public Label reservationFeeLabel;
    public Label discountLabel;
    public Label netAmountLabel;
    public VBox detailsBox;
    public GridPane ordersGridPane = new GridPane();

    public void initialize() {
        detailsBox.getChildren().add( ordersGridPane );
    }
    // TODO use TableView like order?
    // TODO rename and refactor the controls for the delivery/reservation change
    public void setInvoice( Invoice invoice ) {
        Customer customer = Database.getCustomerById( invoice.getCustomerId() );

        customerNameLabel.setText( "Customer Id: " + customer.getId() + "\tCustomer Name: " + customer.getName() );
        if ( isInvoiceForDelivery( invoice ) ) {
            tableIdLabel.setText( "Address: " + invoice.getDeliveryAddress() );
            tableFeeLabel.setText( "Delivery Fee: " + invoice.getDeliveryFee() );
        }
        else {
            tableIdLabel.setText( "Table Id: " + invoice.getTableId() );
            tableFeeLabel.setText( "Table Fee: " + invoice.getTableFee() );
            reservationFeeLabel.setText( "Reservation Fee: " + invoice.getReservationFee() );
        }

        String time = invoice.getTime().format( DateTimeFormatter.ofPattern( "hh:mm a" ) );
        reservationDateTimeLabel.setText( "Date: " + invoice.getDate() + " At: " + time );
        netAmountLabel.setText( "Net: " + invoice.getNetAmount() );
        discountLabel.setText( "Discount: " + invoice.getDiscount() );
        totalLabel.setText( "Total: " + invoice.getTotal() );

        ordersGridPane.setVgap( 5 );
        ordersGridPane.setHgap( 30 );
        ordersGridPane.getChildren().clear();
        ordersGridPane.add( createLabel( "Item Name" ), 0, 0 );
        ordersGridPane.add( createLabel( "Quantity" ), 1, 0 );
        ordersGridPane.add( createLabel( "Unit Price" ), 2, 0 );

        int row = 1;
        for ( Item item : invoice.getItems().keySet() ) {
            ordersGridPane.add( createLabel( item.getName() ), 0, row );
            ordersGridPane.add( createLabel( String.valueOf( invoice.getItems().get( item ) ) ), 1, row );
            ordersGridPane.add( createLabel( String.valueOf( item.getPrice() ) ), 2, row );
            ++row;
        }
    }

    private boolean isInvoiceForDelivery( Invoice invoice ) {
        return invoice.getTableId().equals( Util.INVALID_ID );
    }

    private Label createLabel( String text ) {
        Label label = new Label( text );
        label.setStyle( "-fx-text-fill: black" );
        label.setFont( Font.font( "Times New Roman", FontWeight.BOLD, 12 ) );
        return label;
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {

    }
}
