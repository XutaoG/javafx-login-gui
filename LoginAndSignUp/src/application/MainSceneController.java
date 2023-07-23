package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainSceneController implements Initializable
{
	@FXML
	private Button logoutButton;
	
	@FXML
	private Label usernameLabel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		logoutButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				DatabaseUtil.changeToSignInScene(event);
			}
		});
	}

	public void setUserInformation(String username)
	{
		usernameLabel.setText("Welcome " + username);
	}
}
