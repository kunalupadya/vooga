package Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LogInGrid extends GridPane {
    public static final int LOGIN_PADDING=25;
    public static final int LOGIN_GAP=10;
    public static final int TEXT_FIELD_POS=1;
    private TextField userTextField;
    private PasswordField password;
    public LogInGrid(){
        logIn();
    }
    private void logIn(){
        setId("login");
        setPadding(new Insets(LOGIN_PADDING, LOGIN_PADDING, LOGIN_PADDING, LOGIN_PADDING));
        setHgap(LOGIN_GAP);
        setVgap(LOGIN_GAP);
        Label userName = new Label("User Name:");
        add(userName, 0, 1);
        userTextField = new TextField();
        add(userTextField, TEXT_FIELD_POS, TEXT_FIELD_POS);
        Label pw = new Label("Password:");
        add(pw, 0, 2);
        password = new PasswordField();
        add(password, 1, 2);
        setAlignment(Pos.CENTER);
    }

    public String getUserName(){
        return userTextField.getText();
    }

    public String getPassword(){
        return password.getText();
    }

}
