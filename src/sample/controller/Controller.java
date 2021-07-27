package sample.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import sample.util.LoginDTO;
import sample.util.ViewDTO;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable {


    @FXML
    private AnchorPane anchor_left;
    @FXML
    private HBox hbox;
    @FXML
    private AnchorPane anchor_right;
    @FXML
    private JFXTextField club_name;
    @FXML
    private Label label;

    @FXML
    private JFXPasswordField Password;

    @FXML
    private Label loginlabel;
    private Main main;
    private  boolean club=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RequiredFieldValidator validator=new RequiredFieldValidator();
        validator.setMessage("No Input Given");
        club_name.getValidators().add(validator);
        club_name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!t1)
                {
                    club_name.validate();
                }
            }
        });
        animation.startingAnimationController(this);

    }

    public void setMain(Main main) {
        this.main = main;
    }


    public void club_on_action(ActionEvent actionEvent) {
        label.setText("LOGIN AS CLUB");
        club_name.setPromptText("Club Name");
        club_name.clear();
        Password.clear();
        club=true;


    }

    public void viewer_on_action(ActionEvent actionEvent) {
        club=false;
        ViewDTO viewDTO=new ViewDTO();
        animation.closingAnimation(this);
        try {
            main.getNetworkUtil().write(viewDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Log_in_action(ActionEvent actionEvent) {
        if (club) {
            String name = club_name.getText().toUpperCase();
            String password = Password.getText();
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(name);
            loginDTO.setPassword(password);
            try {
                main.getNetworkUtil().write(loginDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            main.showAlert("Incorrect Credentials","Button Unclicked","Please select a button from ADMIN,CLUB,VIEWER");
        }
    }


    public Node getRootNode() {
        return  hbox;
    }
}
