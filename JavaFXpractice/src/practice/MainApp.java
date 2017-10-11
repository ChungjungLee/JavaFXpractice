package practice;

import practice.model.*;
import practice.view.PersonOverviewController;
import practice.view.PersonEditDialogController;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
	 * ����ó�� ���� observable ����Ʈ 
	 */
	private ObservableList<Person> personData = 
			FXCollections.observableArrayList();
	
	/**
	 * ������
	 */
	public MainApp() {
		// ���� ������ �߰�
		personData.add(new Person("Hans", "Zimmer"));
		personData.add(new Person("CJ", "Lee"));
	}
	
	/**
	 * ����ó�� ���� observable ����Ʈ�� ��ȯ�Ѵ�.
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		
		// ���ø����̼� �������� �����Ѵ�.
		this.primaryStage.getIcons().add(new Image("file:resources/images/address_book_icon.png"));
		
		initRootLayout();
		
		showPersonOverview();
		
	}

	/**
	 * ���� ���̾ƿ��� �ʱ�ȭ�Ѵ�.
	 */
	public void initRootLayout() {
		try {
			// fxml ���Ͽ��� ���� ���̾ƿ��� �����´�.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			// ���� ���̾ƿ��� �����ϴ� scene�� �����ش�.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���� ���̾ƿ� �ȿ� ����ó ����� �����ش�. 
	 */
	public void showPersonOverview() {
		try {
			// ����ó ����� �����´�.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			
			// ����ó ����� ���� ���̾ƿ� ����� �����Ѵ�.
			rootLayout.setCenter(personOverview);
			
			// ���� ���ø����̼��� ��Ʈ�ѷ��� �̿��� �� �ְ� �Ѵ�.
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * person�� �ڼ��� ������ �����ϱ� ���� ���̾�α׸� ����.
	 * ���� ����ڰ� OK�� Ŭ���ϸ� �־��� person�� ���� ���� �� true�� ��ȯ�Ѵ�.
	 * 
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// fxml ������ �ε��ϰ� ���� ���ο� ���������� �����.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			// ���̾�α� ���������� �����.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.getIcons().add(new Image("file:resources/images/address_book_edit_icon.png"));
			
			// person�� ��Ʈ�ѷ��� �����Ѵ�.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			
			// ���̾�α׸� �����ְ� ����ڰ� ���� ������ ��ٸ���.
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * ���� ���������� ��ȯ�Ѵ�.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
