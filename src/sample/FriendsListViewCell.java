package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
public class FriendsListViewCell extends ListCell<FacebookUser> {
    @FXML
    private Label cellNameID;

    @FXML
    private ImageView cellPhotoID;

    @FXML
    private ImageView friendStatusID;


    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(FacebookUser user, boolean empty) {
        super.updateItem(user, empty);


        if (empty || user == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("res/ListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            cellNameID.setText(String.valueOf(user.getName()));

            Image avatar = new Image("sample/photos/"+ user.getName() + "_photo.jpg");
            cellPhotoID.setImage(avatar);
            friendStatusID.setImage(new Image("sample/photos/friend_accepted.png"));


            setText(null);
            setGraphic(gridPane);
        }

    }
}
