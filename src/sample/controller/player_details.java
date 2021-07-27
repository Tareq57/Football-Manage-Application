package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.util.MarketDTO;
import sample.util.SellReqDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class player_details implements Initializable {
    private ViewPage viewPage;
    public   ClubPage clubPage;
    public MarketPage marketPage;
    public Main main;
    public  Club club;
    private  Player player;
    String market_club;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private JFXButton sell_button;
    @FXML
    private Label player_name;

    @FXML
    private Label player_club;

    @FXML
    private Label player_country;

    @FXML
    private Label player_age;

    @FXML
    private Label player_position;

    @FXML
    private Label player_number;

    @FXML
    private Label player_salary,player_height;

    @FXML
    private ImageView player_image;

    @FXML
    private AnchorPane player_detail_anchorpane;
    Stage stage;
    public void setMain(ViewPage viewPage) {
        this.viewPage=viewPage;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String css=this.getClass().getClassLoader().getResource("sample/CSS/ViewPage.css").toExternalForm();
        snackbar.setPrefWidth(927);
        snackbar.getStylesheets().add(css);
        animation.startingAnimationPlayer_Details(this);


    }

    public void setplayer(Player player) {

        this.player=player;
        player_name.setText(":  "+player.getName());
        player_age.setText(":  "+Integer.toString(player.getAge()));
        player_club.setText(":  "+player.getClub());
        player_country.setText(":  "+player.getCountry());
        player_position.setText(":  "+player.getPosition());
        player_height.setText(":  "+Double.toString(player.getHeight()));
        player_salary.setText(":  "+Double.toString(player.getWeeklySalary()));
        player_number.setText(":  "+Integer.toString(player.getNumber()));
        Image image=new Image(getClass().getResourceAsStream("../"+player.getImg()));
        player_image.setImage(image);
    }

    public void back_on_action(ActionEvent actionEvent) {
        stage=(Stage) player_detail_anchorpane.getScene().getWindow();
        stage.close();
    }

    public void sell_on_action(ActionEvent actionEvent) {
        if(viewPage!=null)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("You have not log in.")));
            return;
        }
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/Confirmation.fxml"));
        try {
            Parent root=loader.load();
            Confirmation controller=loader.getController();
            controller.init(this,market_club);
            controller.setplayer(player,sell_button.getText(),club);
            stage.setTitle("Confirmation Page");
            Scene scene=new Scene(root);
            scene.getStylesheets().add("sample/CSS/ViewPage.css");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Node getRootNode() {
        return  player_detail_anchorpane;
    }

    public void init(Main main, Stage stage, String str, Club club, String market_club) {
        this.main=main;
        this.stage=stage;
        sell_button.setText(str);
        this.club=club;
        this.market_club=market_club;
    }

    public void edit_on_action(ActionEvent actionEvent) throws IOException {
        if(sell_button.getText().equalsIgnoreCase("SELL"))
        {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("../FXML/EditPage.fxml"));
            Parent root= loader.load();
            EditPage controller=loader.getController();
            controller.init(this,player,club);
            stage.setTitle("Edit Page");
            Scene scene=new Scene(root);
            scene.getStylesheets().add("sample/CSS/ViewPage.css");
            stage.setScene(scene);
            stage.show();
        }
        else {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("You are in market page.For editing, you should go home page")));
        }

    }
}
