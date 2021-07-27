package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Action_club_country_player.Player;
import sample.MyListener;

import java.net.URL;
import java.util.ResourceBundle;

public class grid_item_controller implements Initializable {
    @FXML
    private AnchorPane grid_item;

    @FXML
    private ImageView image;

    @FXML
    private Label grid_player_name;
    private Player player;
    private MyListener myListener;
    @FXML
    private  void  click_player(MouseEvent mouseEvent)
    {
        myListener.onClickListener(player);
    }
    public  void  setData(Player player,MyListener myListener) {

        this.player=player;
        this.myListener=myListener;
        grid_player_name.setText(player.getName());
        Image image= new Image(getClass().getResourceAsStream("../"+player.getImg()));
        this.image.setImage(image);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }



}
