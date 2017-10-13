package practice.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ����ó ����Ʈ�� ���δ� ���� Ŭ�����̴�.
 * XML�� �����ϴ� �� ���ȴ�.
 * @author user
 *
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper {
	private List<Person> persons;
	
	@XmlElement(name = "person")
	public List<Person> getPerson() {
		return persons;
	}
	
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
