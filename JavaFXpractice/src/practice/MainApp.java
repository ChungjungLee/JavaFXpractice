package practice;

import practice.model.*;
import practice.view.PersonOverviewController;
import practice.view.RootLayoutController;
import practice.view.PersonEditDialogController;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
	 * 연락처에 대한 observable 리스트 
	 */
	private ObservableList<Person> personData = 
			FXCollections.observableArrayList();
	
	/**
	 * 생성자
	 */
	public MainApp() {
		// 샘플 데이터 추가
		personData.add(new Person("Hans", "Zimmer"));
		personData.add(new Person("CJ", "Lee"));
	}
	
	/**
	 * 연락처에 대한 observable 리스트를 반환한다.
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		
		// 애플리케이션 아이콘을 설정한다.
		this.primaryStage.getIcons().add(new Image("file:resources/images/address_book_icon.png"));
		
		initRootLayout();
		
		showPersonOverview();
		
	}

	/**
	 * 상위 레이아웃을 초기화한다.
	 * 그리고 마지막으로 열었던 연락처 파일을 가져온다.
	 */
	public void initRootLayout() {
		try {
			// fxml 파일에서 상위 레이아웃을 가져온다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			// 상위 레이아웃을 포함하는 scene을 보여준다.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			// 컨트롤러한테 MainApp 접근 권한을 준다.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 마지막으로 열었던 연락처 파일을 가져온다.
		File file = getPersonFilePath();
		if (file != null) {
			loadPersonDataFromFile(file);
		}
	}
	
	/**
	 * 상위 레이아웃 안에 연락처 요약을 보여준다. 
	 */
	public void showPersonOverview() {
		try {
			// 연락처 요약을 가져온다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			
			// 연락처 요약을 상위 레이아웃 가운데로 설정한다.
			rootLayout.setCenter(personOverview);
			
			// 메인 애플리케이션이 컨트롤러를 이용할 수 있게 한다.
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * person의 자세한 정보를 변경하기 위해 다이얼로그를 연다.
	 * 만일 사용자가 OK를 클릭하면 주어진 person에 내용 저장 후 true를 반환한다.
	 * 
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			// 다이얼로그 스테이지를 만든다.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.getIcons().add(new Image("file:resources/images/address_book_edit_icon.png"));
			
			// person을 컨트롤러에 설정한다.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			
			// 다이얼로그를 보여주고 사용자가 닫을 때까지 기다린다.
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 연락처 파일 환경설정을 반환한다.
	 * 즉 파일은 마지막으로 열린 것이고, 환경설정은 OS 특정 레지스트리로부터 읽는다.
	 * 만일 preference를 찾지 못하면 null을 반환한다.
	 * 
	 * @return
	 */
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	
	/**
	 * 현재 불러온 파일의 경로를 설정한다. 이 경로는 OS 특정 레지스트리에 저장된다.
	 * 
	 * @param file
	 */
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			
			// Stage 타이틀을 업데이트한다.
			primaryStage.setTitle("AddressApp - " + file.getName());
			
		} else {
			prefs.remove("filePath");
			
			// Stage 타이틀을 업데이트한다.
			primaryStage.setTitle("AddressApp");
		}
	}
	
	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			// 파일로부터 XML을 읽은 다음 역 마샬링한다.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
			
			personData.clear();
			personData.addAll(wrapper.getPerson());
			
			// 파일 경로를 레지스트리에 저장한다.
			setPersonFilePath(file);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText(
					"Could not load data from file:\n" + file.getPath());
			
			alert.showAndWait();
		}
	}
	
	/**
	 * 현재 연락처 데이터를 지정한 파일에 저장한다.
	 */
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.
					newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			// 연락처 데이터를 감싼다.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);
			
			// 마샬링 후 XML을 파일에 저장한다.
			m.marshal(wrapper, file);
			
			// 파일 경로를 레지스트리에 저장한다.
			setPersonFilePath(file);
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());
			
			alert.showAndWait();
		}
	}
	
	/**
	 * 메인 스테이지를 반환한다.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
