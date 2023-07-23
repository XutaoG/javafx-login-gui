package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DatabaseUtil
{
	private static String mainSceneFxml = "mainScene.fxml";
	private static String signInSceneFxml = "signInScene.fxml";
	private static String signUpSceneFxml = "signUpScene.fxml";
	
	public static void changeToSignInScene(ActionEvent event) 
	{
		Parent root = null;
		try
		{
			root = FXMLLoader.load(DatabaseUtil.class.getResource(signInSceneFxml));
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Sign In");
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void changeToSignUpScene(ActionEvent event) 
	{
		Parent root = null;
		try
		{
			root = FXMLLoader.load(DatabaseUtil.class.getResource(signUpSceneFxml));
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Sign Up");
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void changeToMainScene(ActionEvent event, String username)
	{
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(DatabaseUtil.class.getResource(mainSceneFxml));
		try
		{
			root = loader.load();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		MainSceneController mainSceneController = loader.getController();
		mainSceneController.setUserInformation(username);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Welcome");
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void signUpUser(ActionEvent event, String username, String password)
	{
		Connection connection = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Sql050204!");
			psCheckUserExists = connection.prepareStatement("SELECT * FROM userinformation WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst())
			{
				System.out.println("USER ALREADY EXIST");
			}
			else
			{
				psInsert = connection.prepareStatement("INSERT INTO userinformation (username, password) VALUES (?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.executeUpdate();
				
				changeToMainScene(event, username);
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		finally
		{
			if (resultSet != null)
			{
				try
				{
					resultSet.close();					
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (psInsert != null)
			{
				try
				{
					psInsert.close();					
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (psCheckUserExists != null)
			{
				try
				{
					psCheckUserExists.close();					
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (connection != null)
			{
				try
				{
					connection.close();					
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
		}
	}
	
	public static void signInUser(ActionEvent event, String username, String password)
	{
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Sql050204!");
			ps = connection.prepareStatement("SELECT password FROM userinformation WHERE username = ?");
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			
			if (!resultSet.isBeforeFirst())
			{
				System.out.println("User not found");
			}
			else
			{
				String retrievedPassword = null;
				while (resultSet.next())
				{
					retrievedPassword = resultSet.getString("password");
				}
				
				if (password.compareTo(retrievedPassword) == 0)
				{
					changeToMainScene(event, username);
				}
				else
				{
					System.out.println("Password is incorrect");
				}
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		finally
		{
			if (resultSet != null)
			{
				try
				{
					resultSet.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
		}
	}

	public static boolean doesUsernameExist(String username)
	{
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		boolean retval = false;
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Sql050204!");
			ps = connection.prepareStatement("SELECT * FROM userinformation WHERE username = ?");
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			
			if (resultSet.isBeforeFirst())
			{
				retval = true;
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		finally
		{
			if (resultSet != null)
			{
				try
				{
					resultSet.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
		}
		return retval;
	}
	
	public static boolean isMatchingPassword(String username, String password)
	{
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		boolean retval = false;
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Sql050204!");
			ps = connection.prepareStatement("SELECT password FROM userinformation WHERE username = ?");
			ps.setString(1, username);
			resultSet = ps.executeQuery();
		
			if (resultSet.isBeforeFirst())
			{
				String retrievedPassword = null;
				while (resultSet.next())
				{
					retrievedPassword = resultSet.getString("password");
				}
				
				if (password.compareTo(retrievedPassword) == 0)
				{
					retval = true;
				}
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		finally
		{
			if (resultSet != null)
			{
				try
				{
					resultSet.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
			
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}
		}
		return retval;
	}
}


























