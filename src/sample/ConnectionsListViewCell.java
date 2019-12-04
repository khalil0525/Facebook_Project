package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ConnectionsListViewCell extends ListCell<FacebookUser> {
    @FXML
    private Label cellNameID2;

    @FXML
    private ImageView cellPhotoID2;

    @FXML
    private ImageView friendStatusID2;


    @FXML
    private GridPane gridPane2;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(FacebookUser user, boolean empty) {
        super.updateItem(user, empty);


        if (empty || user == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("res/PeopleCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            cellNameID2.setText(String.valueOf(user.getName()));

            Image avatar = new Image("sample/photos/"+ user.getName() + "_photo.jpg");
            cellPhotoID2.setImage(avatar);
            friendStatusID2.setImage(new Image("sample/photos/add_friend_photo.png"));


            setText(null);
            setGraphic(gridPane2);
        }

    }
}
