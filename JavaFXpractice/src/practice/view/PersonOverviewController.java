package practice.view;

import practice.MainApp;
import practice.model.Person;
import practice.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
	// @FXML annotation은 fxml파일이 private을 이용하기 위해 필요함
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label birthdayLabel;
	
	// 메인 어플리케이션 참조
	private MainApp mainApp;
	
	/**
	 * 생성자
	 * initialize() 메소드 이전에 호출된다.
	 */
	public PersonOverviewController() {
		
	}
	
	/**
	 * 컨트롤러 클래스를 초기화한다.
	 * fxml 파일이 로드되고 나서 자동으로 호출된다.
	 */
	@FXML
	private void initialize() {
		// 연락처 테이블의 두 열을 초기화한다.
		firstNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().lastNameProperty());
		
		// 연락처 정보를 지운다.
		showPersonDetails(null);
		
		// 선택을 감지하고 그 때마다 연락처의 자세한 정보를 보여준다.
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue));
	}
	
	/**
	 * 참조를 다시 유지하기 위해 메인 애플리케이션이 호출한다.
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		// 테이블에 observable 리스트 데이터를 추가한다.
		personTable.setItems(mainApp.getPersonData());
	}
	
	/**
	 * 연락처의 자세한 정보를 오른쪽 tableview에 보여주기 위해 모든 텍스트 필드를 채운다
	 * 만일 person이 null이면 모든 텍스트 필드가 지워진다. 
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			// person 객체로 label에 정보를 채운다.
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			cityLabel.setText(person.getCity());
			
			// TODO: 생일을 String으로 변환해야 한다!
			// birthdayLabel.setText();
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
		} else {
			// person이 null이면 모든 텍스트를 지운다.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
		}
	}
	
	
}
