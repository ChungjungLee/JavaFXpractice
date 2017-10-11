package practice.view;

import practice.MainApp;
import practice.model.Person;
import practice.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
	// @FXML annotation�� fxml������ private�� �̿��ϱ� ���� �ʿ���
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
	
	// ���� ���ø����̼� ����
	private MainApp mainApp;
	
	/**
	 * ������
	 * initialize() �޼ҵ� ������ ȣ��ȴ�.
	 */
	public PersonOverviewController() {
		
	}
	
	/**
	 * ��Ʈ�ѷ� Ŭ������ �ʱ�ȭ�Ѵ�.
	 * fxml ������ �ε�ǰ� ���� �ڵ����� ȣ��ȴ�.
	 */
	@FXML
	private void initialize() {
		// ����ó ���̺��� �� ���� �ʱ�ȭ�Ѵ�.
		firstNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().lastNameProperty());
		
		// ����ó ������ �����.
		showPersonDetails(null);
		
		// ������ �����ϰ� �� ������ ����ó�� �ڼ��� ������ �����ش�.
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue));
	}
	
	/**
	 * ������ �ٽ� �����ϱ� ���� ���� ���ø����̼��� ȣ���Ѵ�.
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		// ���̺� observable ����Ʈ �����͸� �߰��Ѵ�.
		personTable.setItems(mainApp.getPersonData());
	}
	
	/**
	 * ����ó�� �ڼ��� ������ ������ tableview�� �����ֱ� ���� ��� �ؽ�Ʈ �ʵ带 ä���
	 * ���� person�� null�̸� ��� �ؽ�Ʈ �ʵ尡 ��������. 
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			// person ��ü�� label�� ������ ä���.
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			cityLabel.setText(person.getCity());
			
			// TODO: ������ String���� ��ȯ�ؾ� �Ѵ�!
			// birthdayLabel.setText();
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
		} else {
			// person�� null�̸� ��� �ؽ�Ʈ�� �����.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
		}
	}
	
	/**
	 * ����ڰ� new ��ư�� Ŭ���� �� ȣ��ȴ�.
	 * ���ο� ����ó ������ �ֱ� ���� ���̾�α׸� ����.
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		
		if (okClicked) {
			mainApp.getPersonData().add(tempPerson);
		}
	}
	
	/**
	 * ����ڰ� edit ��ư�� Ŭ���� �� ȣ��ȴ�.
	 * ������ ����ó ������ �����ϱ� ���� ���̾�α׸� ����.
	 */
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}
		} else {
			// �ƹ� cell�� �������� �ʾҴ�.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");
			
			alert.showAndWait();
		}
	}
	
	/**
	 * ����ڰ� ���� ��ư�� Ŭ���ϸ� ȣ��ȴ�.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			// table�� ��� ���� �������� �ʾ��� ��
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");
			
			alert.showAndWait();
		}
		
	}
}
