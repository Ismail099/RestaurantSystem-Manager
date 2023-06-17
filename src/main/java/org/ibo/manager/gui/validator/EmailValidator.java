package org.ibo.manager.gui.validator;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import org.ibo.manager.repositories.Database;
import org.ibo.manager.Util;

public class EmailValidator extends ValidatorBase {
    public EmailValidator() {
        setMessage( "Please ensure the email is in the correct format and is unique" );
    }

    protected void eval() {
        if (this.srcControl.get() instanceof TextInputControl ) {
            this.evalTextInputField();
        }

    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl)this.srcControl.get();
        String text = textField.getText();

        try {
            this.hasErrors.set(false);
            if (!text.isEmpty()) {
                if ( !Util.validateEmail( text ) || Database.getCustomerByEmail( text ) != null )
                    this.hasErrors.set( true );
            }
        } catch (Exception var4) {
            this.hasErrors.set(true);
        }

    }
}