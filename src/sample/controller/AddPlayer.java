package sample.controller;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.util.AddPlayerDTO2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPlayer implements Initializable  {
    public  Main main;
    Club club;
    @FXML
    private AnchorPane add_player_anchorpane;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private Label club_name;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField country;

    @FXML
    private JFXTextField age;

    @FXML
    private JFXTextField number;

    @FXML
    private JFXTextField height;

    @FXML
    private JFXTextField position;

    @FXML
    private JFXTextField salary;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String css=this.getClass().getClassLoader().getResource("sample/CSS/ViewPage.css").toExternalForm();
        snackbar.setPrefWidth(512);
        snackbar.getStylesheets().add(css);
        RequiredFieldValidator validator=new RequiredFieldValidator();
        validator.setMessage("No Input Given");
        name.getValidators().add(validator);
        name.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                name.validate();
            }
        });
        age.getValidators().add(validator);
        age.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                age.validate();
            }
        });
        country.getValidators().add(validator);
        country.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                country.validate();
            }
        });
        number.getValidators().add(validator);
        number.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                number.validate();
            }
        });
        height.getValidators().add(validator);
        height.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                height.validate();
            }
        });
        position.getValidators().add(validator);
        position.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                position.validate();
            }
        });
        position.getValidators().add(validator);
        position.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(!t1)
            {
                position.validate();
            }
        });
        animation.startingAnimationControllerAddPLayer(this);
    }

    public void Add_PLayer_buttonAction(ActionEvent actionEvent) {
        String Club,Name,Country,Position,Img;
        int Age,Number;
        double Salary,Height,Price=0;
        Club=club_name.getText();
        Name=this.name.getText();
        Country=country.getText();
        Position=position.getText();
        Img="Background Image/player.png";
        try {
            Age=Integer.parseInt(age.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Age")));
            return;
        }
        try {
            Number=Integer.parseInt(number.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Jersey Number")));
            return;
        }
        try {
            Salary=Double.parseDouble(salary.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Salary")));
            return;
        }
        try {
            Height=Double.parseDouble(height.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Height")));
            return;
        }
        Player player=new Player(Name,Country,Age,Height,Club,Position,Number,Salary,Img,Price);
        AddPlayerDTO2 addPlayerDTO2=new AddPlayerDTO2();
        addPlayerDTO2.setPlayer(player);
        try {
            main.getNetworkUtil().write(addPlayerDTO2);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Node getRootNode() {
        return  add_player_anchorpane;
    }

    public void init(Main main, String club_name, Club club) {
        this.main=main;
        this.club_name.setText(club_name);
        this.club=club;
    }
}
