package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInSceneController implements Initializable
{
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button signInButton;
	
	@FXML
	private Button changeToSignUpButton;
	
	@FXML
	private Label usernameErrorLabel;
	
	@FXML
	private Label passwordErrorLabel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		signInButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event)
			{
				String username = usernameTextField.getText().trim();
				
				if (DatabaseUtil.doesUsernameExist(username))
				{
					usernameErrorLabel.setVisible(false);
					if (DatabaseUtil.isMatchingPassword(username, passwordField.getText()))
					{
						passwordErrorLabel.setVisible(false);
						DatabaseUtil.signInUser(event, usernameTextField.getText(), passwordField.getText());						
					}
					else
					{
						passwordErrorLabel.setVisible(true);
					}
				}
				else
				{
					usernameErrorLabel.setVisible(true);
				}
			}
		});
		
		changeToSignUpButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				DatabaseUtil.changeToSignUpScene(event);
			}
		});
	}
}
