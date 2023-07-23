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

public class SignUpSceneController implements Initializable
{
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private PasswordField comfirmPasswordField;
	
	@FXML
	private Button signUpButton;
	
	@FXML
	private Button changeToSignInButton;
	
	@FXML
	private Label usernameErrorLabel;
	
	@FXML 
	private Label passwordErrorLabel;
	
	@FXML
	private Label passwordLengthErrorLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		signUpButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				passwordLengthErrorLabel.setVisible(false);
				usernameErrorLabel.setVisible(false);
				passwordErrorLabel.setVisible(false);
				
				boolean canCreateUser = true;
				
				if (passwordField.getText().length() <= 6 || passwordField.getText().length() >= 20)
				{
					canCreateUser = false;
					passwordLengthErrorLabel.setVisible(true);
				}
				if (passwordField.getText().compareTo(comfirmPasswordField.getText()) != 0)
				{
					canCreateUser = false;
					passwordErrorLabel.setVisible(true);
				}
				if (DatabaseUtil.doesUsernameExist(usernameTextField.getText().trim()))
				{
					canCreateUser = false;
					usernameErrorLabel.setVisible(true);
				}
				
				if (canCreateUser)
				{
					DatabaseUtil.signUpUser(event, usernameTextField.getText().trim(), passwordField.getText().trim());
				}
			}
		});
		
		changeToSignInButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				DatabaseUtil.changeToSignInScene(event);
			}
		});
	}
	
	public void usernameError(String error)
	{
		usernameErrorLabel.setText(error);
		usernameErrorLabel.setVisible(true);
	}

	public void passwordError(String error)
	{
		passwordErrorLabel.setText(error);
		passwordErrorLabel.setVisible(true);
	}
}
