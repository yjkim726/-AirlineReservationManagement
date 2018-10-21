package manage;

import java.util.*;

/*
 * �л��� �����ϱ� ���� Ŭ����
 */
class StudentManage {
	StudentSearch search = new StudentSearch();
	Scanner scanner = new Scanner(System.in);
	int index;
	
	/*
	 * �л� ���� �޼ҵ� 
	 */
	void delStudent(ArrayList<Student> studentarr) {
		Student temp = new Student();
		
		index = search.search(studentarr);
		// System.out.println(index);
		if(index==-1){
			System.out.println("�˻��� �л��� �����ϴ�");
			return;
		}
		temp = studentarr.get(index);
		System.out.println("������ �л� : " + temp.name);
		studentarr.remove(temp);
		return;
	}
	
	/*
	 * �л� ���� �޼ҵ�
	 */
	void modStudent(ArrayList<Student> studentarr) {
		Student temp = new Student();
		
		index = search.search(studentarr);
		// System.out.println(index);
		if(index==-1){
			System.out.println("�˻��� �л��� �����ϴ�");
			return;
		}
		temp = studentarr.get(index);
		System.out.println("�˻��� �л� : " + temp.name);
		temp.inputSNo(studentarr);	
		temp.inputName();
		temp.inputdepart();
		return;
	}
	
	/*
	 * �л� �߰� �޼ҵ�
	 */
	void addStudent(ArrayList<Student> studentarr) {
			Student temp = new InforTechStudent();
			
			temp.inputSNo(studentarr);
			temp.inputName();
			temp.inputdepart();

			
			//System.out.println(stutmp.SNo + stutmp.name);
			studentarr.add(temp);
			temp = null;
	}
	
	/*
	 * �л� ��� �޼ҵ�
	 */
	void printStudent(ArrayList<Student> studentarr) {	
		for(Student temp : studentarr ) {
			System.out.print("�й� : " + temp.SNo);			
			System.out.print(" | �̸� : " + temp.name);
			System.out.println(" | �а� : " + temp.department);
		}
	}
}
