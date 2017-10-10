package practice.view;

import practice.MainApp;
import practice.model.Person;

import javafx.fxml.FXML;
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
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	}
	
	/**
	 * ������ �ٽ� �����ϱ� ���� ���� ���ø����̼��� ȣ���Ѵ�.
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		// ���̺� observable ����Ʈ �����͸� �߰��Ѵ�.
		personTable.setItems(mainApp.getPersonData());
	}
}
