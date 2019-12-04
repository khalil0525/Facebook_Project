package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Graph g = new Graph();
    private FacebookUser target;
    private HashMap<Integer, FacebookUser> people;
    private ObservableList<FacebookUser> friendsList;
    private ObservableList<FacebookUser> connections;
    private ObservableList<String> name_list;
    private LinkedList<ObservableList<FacebookUser>> masterFriendHolder;
    private LinkedList<ObservableList<FacebookUser>> masterConHolder;

    @FXML
    private ListView<FacebookUser> list1ID;
    @FXML
    private ListView<FacebookUser> list2ID;
    @FXML
    private ChoiceBox choiceID;
    @FXML
    private Label nameID;
    @FXML
    private ImageView avatarID;
    @FXML
    private Label locationID;
    @FXML
    private Label view1ID;
    @FXML
    private Label view2ID;
    @FXML
    private ImageView fblogoID;

    public Controller()  {
        FacebookUser user1 = new FacebookUser(1, "Doug", "Chino","California"); //Friends with Cedric(2), cole(3), jamal(4), laura(6)
        FacebookUser user2 = new FacebookUser(2, "Cedric", "Dover","Delaware");//Doug
        FacebookUser user3 = new FacebookUser(3, "Cole", "Dover","Delaware");//Doug(1), Mark(7), Neil(8), Jeff(5)

        FacebookUser user4 = new FacebookUser(4, "Jamal", "Smyrna","Delaware");//Doug
        FacebookUser user5 = new FacebookUser(5, "Jeff", "Jacksonville","Florida");//Cole
        FacebookUser user6 = new FacebookUser(6, "Laura", "Orlando","Florida");//Doug

        FacebookUser user7 = new FacebookUser(7, "Mark", "Georgetown","Delaware");//Cole
        FacebookUser user8 = new FacebookUser(8, "Neil", "Chino","California");//Cole
        FacebookUser user9 = new FacebookUser(9, "Phil", "Hollywood","California");
        people = new HashMap<>();
        name_list = FXCollections.observableArrayList();
        friendsList = FXCollections.observableArrayList();
        connections = FXCollections.observableArrayList();
        //Doug adds friends and they accept
        user1.addFriend(2);
        user1.addFriend(3);
        user1.addFriend(4);
        user1.addFriend(6);

        user2.addFriend(1);
        user3.addFriend(1);
        user4.addFriend(1);
        user6.addFriend(1);

        //Cole adds friends and they accept
        user3.addFriend(5);
        user3.addFriend(7);
        user3.addFriend(8);

        user5.addFriend(3);
        user7.addFriend(3);
        user8.addFriend(3);

        people.put(1, user1);
        people.put(2, user2);
        people.put(3, user3);
        people.put(4, user4);
        people.put(5, user5);
        people.put(6, user6);
        people.put(7, user7);
        people.put(8, user8);
        people.put(9, user9);






    }

    public LinkedList<ObservableList<FacebookUser>> genConnectionList(HashMap<Integer, FacebookUser> map) {
        LinkedList<ObservableList<FacebookUser>> list = new LinkedList<>();
        for(Map.Entry<Integer, FacebookUser> t: map.entrySet()) {
            ObservableList<FacebookUser> l = FXCollections.observableArrayList();
            for (Map.Entry<Integer, FacebookUser> u : map.entrySet()) {

                if (g.findPathBiBFS(people, t.getValue().getID(), u.getValue().getID()) != null && t.getValue().getID() != u.getValue().getID()) {
                    l.add(u.getValue());

                }
            }
            list.add(l);
        }
        return list;
    }

    public LinkedList<ObservableList<FacebookUser>> genFriendsLists(HashMap<Integer, FacebookUser> map) {
        LinkedList<ObservableList<FacebookUser>> list = new LinkedList<>();
        for(Map.Entry<Integer, FacebookUser> t: map.entrySet()) {
            ObservableList<FacebookUser> l = FXCollections.observableArrayList();
            FacebookUser tar = t.getValue();
            for (Integer u : tar.getFriends()) {

                    l.add(map.get(u));

            }
            list.add(l);
        }
        return list;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fblogoID.setImage(new Image("sample/photos/fb.png"));
        masterFriendHolder = genFriendsLists(people);
        masterConHolder = genConnectionList(people);

        friendsList = masterFriendHolder.get(0);
        connections = masterConHolder.get(0);

        target = people.get(1);
        nameID.setText (target.getName());
        avatarID.setImage(new Image("sample/photos/"+ target.getName() + "_photo.jpg"));
        locationID.setText(target.getLocation());

        for(Map.Entry<Integer, FacebookUser> t: people.entrySet()) {
            String s = t.getValue().getName();
            name_list.add(s);


        }
        choiceID.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {

                // set the text for the label to the selected item
                target = people.get(new_value.intValue()+1);
                friendsList = masterFriendHolder.get(new_value.intValue());
                connections = masterConHolder.get(new_value.intValue());
                nameID.setText (target.getName());
                avatarID.setImage(new Image("sample/photos/"+ target.getName() + "_photo.jpg"));
                locationID.setText(target.getLocation());
                //choiceID.setValue(name_list.get(new_value.intValue()) + " selected");
                list1ID.setItems(friendsList);
                list1ID.setCellFactory(friendsListView -> new FriendsListViewCell());
                list2ID.setItems(connections);
                list2ID.setCellFactory(friendsListView -> new ConnectionsListViewCell());
                view1ID.setText("Viewing As: " + target.getName());
                view2ID.setText("Viewing As: " + target.getName());

            }
        });



        view1ID.setText("Viewing As: " + name_list.get(0) );
        view2ID.setText("Viewing As: " + name_list.get(0) );
        choiceID.setItems(name_list);
        choiceID.setValue(name_list.get(0));

        list1ID.setItems(friendsList);
        list1ID.setCellFactory(friendsListView -> new FriendsListViewCell());
        list2ID.setItems(connections);
        list2ID.setCellFactory(friendsListView -> new ConnectionsListViewCell());

    }


}
