package manage;

// �ڹ� Ŭ����
import java.util.*;

/*
 * �л� ������ �Է��ϴ� Ŭ����
 */
class Student {
	Scanner scanner = new Scanner(System.in);
	StudentSearch search = new StudentSearch();
	ArrayList<Subject> subjectlist = new ArrayList<Subject>();
	
	String name , department;
	int SNo,year;
	// int subject
	
	
	/*
	 * �й��� �Է��ϴ� �޼ҵ�
	 */
	public void inputSNo(ArrayList<Student> studentarr) {
		int inputSNo;
		System.out.println("�й��� �Է��ϼ��� : ");
		inputSNo = scanner.nextInt();
		// System.out.println(SNo);
		while(true) {
			if(search.search(studentarr, inputSNo)==-1) {
				this.SNo = inputSNo;
				return;
			}
			else {
				// System.out.println(search.Search(studentarr, SNo));
				System.out.print("�̹� �ִ� �й��Դϴ�. �ٽ� �Է����ּ��� : ");
				inputSNo = scanner.nextInt();
			}
		}
	}
	
	/*
	 * �̸��� �Է��ϴ� �޼ҵ�
	 */
	public void inputName() {
		System.out.println("�̸��� �Է��ϼ��� : ");
		this.name = scanner.next();
	}
	
	public void inputdepart(){
		
		int menu;
		System.out.println("�а� ��ȣ�� �������ּ���.");
		System.out.println("1. ������Ű�");
		System.out.println("2. ����Ʈ����������");
		menu = scanner.nextInt();
		switch(menu){
		case 1 :
			this.department = "������Ű�";
			break;
		case 2 :
			this.department = "����Ʈ����������";
			break;
		}
	
	}
}

/*
 * ������Ű��� �Է��ϴ� Ŭ����
 */
class InforTechStudent extends Student {
			/*�迭�� �����ؼ� �ش� index�� ������ ���� ��ȣ�� ����*/
		
	int index,all;
		// TODO Auto-generated method stub
}


/*
 * ����Ʈ������������ �Է��ϴ� Ŭ����
 */
class SoftwareInforStudent extends Student {
	int number[];
	SoftwareInforStudent() {
		this.department = "����Ʈ����������";
	}
}