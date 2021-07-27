package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Action_club_country_player.Club;
import sample.Action_club_country_player.Player;
import sample.util.BuyReqDTO;
import sample.util.NetworkUtil;
import sample.util.SellReqDTO;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Confirmation implements Initializable {
    private  Player player;
    private  player_details player_details;
    private  Club club;
    String market_club;
    @FXML
    private JFXTextField sell_price;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private JFXButton sell_button;
    @FXML
    private Label text,price_label;
    @FXML
    private AnchorPane confirmation_anchorpane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String css=this.getClass().getClassLoader().getResource("sample/CSS/ViewPage.css").toExternalForm();
        snackbar.setPrefWidth(927);
        snackbar.getStylesheets().add(css);
        animation.startingAnimationConfirmation(this);
    }

    public void sell_onAction(ActionEvent actionEvent) {
        if(sell_price.getText().isEmpty())
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a price first.")));
            return;
        }
        String price=sell_price.getText();
        double Price;
        try{
            Price=Double.parseDouble(price);
        }
        catch (Exception e)
        {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Enter a valid price first.")));
            return;
        }
        if(sell_button.getText().equalsIgnoreCase("SELL"))
        {

            player.setSell_price(Price);
            player.setSold(false);
            SellReqDTO sellReqDTO=new SellReqDTO();
            sellReqDTO.setPlayer(player);
            sellReqDTO.setClub(club);
            try {
                player_details.main.getNetworkUtil().write(sellReqDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            if(Price<player.getSell_price())
            {
                snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("You have to pay equal or more than "+player.getSell_price()+" $")));
                return;
            }
            BuyReqDTO buyReqDTO=new BuyReqDTO();
            buyReqDTO.setPlayer(player);
            buyReqDTO.setClub_name(market_club);
            try {
                player_details.main.getNetworkUtil().write(buyReqDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage=(Stage) confirmation_anchorpane.getScene().getWindow();
        stage.close();
    }

    public void init(player_details player_details, String market_club) {
        this.player_details=player_details;
        this.market_club=market_club;

    }

    public void setplayer(Player player, String str, Club club) {
        this.player=player;
        this.club=club;
        price_label.setText("CURRENT PRICE: "+player.getSell_price()+" $");
        if(str.equalsIgnoreCase("BUY"))
        {
            text.setText("DO YOU WANT TO BUY "+player.getName()+" ?");
            sell_button.setText("BUY");
        }
        else
        {
            text.setText("DO YOU WANT TO SELL "+player.getName()+ " ?");
            sell_button.setText("SELL");
        }
    }

    public void go_back_onAction(ActionEvent actionEvent) {
        Stage stage=(Stage) confirmation_anchorpane.getScene().getWindow();
        stage.close();
    }

    public Node getRootNode() {
        return confirmation_anchorpane;
    }
}
