package Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CreateAccount extends GridPane {
    private static final int PADDING = 25;
    private static final String USERNAME = "Username: ";
    private static final String PASSWORD = "Password: ";
    private static final String CONFIRM_PASSWORD = "Confirm Password: ";
    private static final String LOG_IN_STYLE = "login";
    private TextField userTextField;
    private PasswordField passwordField;
    private PasswordField passwordCheck;

    public CreateAccount(){
        logInWithPasswordCheck();
    }
    private void logInWithPasswordCheck(){
        setId(LOG_IN_STYLE);
        setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        final Label userName = new Label(USERNAME);
        add(userName, 1, 1);
        userTextField = new TextField();
        add(userTextField, 2, 1);

        Label pw = new Label(PASSWORD);
        add(pw, 1, 2);
        passwordField = new PasswordField();
        add(passwordField , 2, 2);

        Label pwConfirm = new Label(CONFIRM_PASSWORD);
        add(pwConfirm, 1, 3);
        passwordCheck = new PasswordField();
        add(passwordCheck, 2, 3);

        setAlignment(Pos.CENTER);
    }
    public String getUserName(){
        return userTextField.getText();
    }

    public String getPasswordField(){
        return passwordField.getText();
    }

    public String getPasswordCheck(){
        return passwordCheck.getText();
    }
}
