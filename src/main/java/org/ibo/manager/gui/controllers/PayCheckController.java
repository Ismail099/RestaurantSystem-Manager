package org.ibo.manager.gui.controllers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.ibo.manager.payroll.models.PayCheck;
import org.springframework.stereotype.Component;

@Component
@FxmlView("paycheck.fxml")
public class PayCheckController extends Controller {
    public Label restaurantName;
    public Label paymentDate;
    public Label employeeId;
    public Label employeeName;
    public Label employeeType;
    public Label employeeSalary;
    public Label employeePayment;
    public GridPane root;

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {

    }

    public void setPayCheck( PayCheck selectedPayCheck ) {
        paymentDate.setText( selectedPayCheck.getPayDate().toString() );
        employeeId.setText( String.valueOf( selectedPayCheck.getEmployee().getId() ) );
        employeeName.setText( selectedPayCheck.getEmployee().getName() );
        employeeType.setText( selectedPayCheck.getEmployee().getPaymentClassification().getType() );
        employeeSalary.setText( selectedPayCheck.getEmployee().getPaymentClassification().getBaseSalary() );
        employeePayment.setText( String.valueOf( selectedPayCheck.getAmount() ) );
    }
}
