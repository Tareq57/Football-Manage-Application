package sample.controller;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.util.EditDTO;
import sample.util.Player_DetailsDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPage implements Initializable {

    @FXML
    private AnchorPane anchorpane_edit;
    @FXML
    private Label club_name;

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

    @FXML
    private JFXSnackbar snackbar;
    public player_details player_details;
    private Player player;
    Club club;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String css=this.getClass().getClassLoader().getResource("sample/CSS/ViewPage.css").toExternalForm();
        snackbar.setPrefWidth(512);
        snackbar.getStylesheets().add(css);
        animation.startingAnimationControllerEditPlayer(this);

    }

    public void Edit_PLayer_buttonAction(ActionEvent actionEvent) {
        int Age,Number;
        double Salary,Height;
        String Position;
        if(position.getText().equalsIgnoreCase(""))
            Position=player.getPosition();
        else
        Position=position.getText();
        try {
            if(age.getText().equalsIgnoreCase(""))
                Age=player.getAge();
            else
            Age=Integer.parseInt(age.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Age")));
            return;
        }
        try {
            if(number.getText().equalsIgnoreCase(""))
                Number=player.getNumber();
            else
            Number=Integer.parseInt(number.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Jersey Number")));
            return;
        }
        try {
            if(salary.getText().equalsIgnoreCase(""))
                Salary=player.getWeeklySalary();
            else
            Salary=Double.parseDouble(salary.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Salary")));
            return;
        }
        try {
            if(height.getText().equalsIgnoreCase(""))
                Height=player.getHeight();
            else
            Height=Double.parseDouble(height.getText());
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid Height")));
            return;
        }
        player.setAge(Age);player.setNumber(Number);player.setHeight(Height);player.setPosition(Position);player.setWeeklySalary(Salary);
        EditDTO editDTO=new EditDTO();
        editDTO.setPlayer(player);
        editDTO.setClub(club);
        try {
            player_details.main.getNetworkUtil().write(editDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void init(player_details player_details, Player player, Club club) {
        this.player_details=player_details;
        this.player=player;
        this.club=club;
        club_name.setText(club.getName());
    }

    public Node getRootNode() {
        return anchorpane_edit;
    }
}
