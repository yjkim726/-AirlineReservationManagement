package manage;

// �ڹ� Ŭ����
import java.util.*;

/*
 * �� �������̽��� Menu �� ����ϴµ� �ʿ��� ��ü�� �л� ArrayList ����
 * �޼ҵ� Select() �� �޴��� �����ϴ� �޼ҵ�
 */
interface MenuSelect {
	ArrayList<Student> studentarr = new ArrayList<Student>();
	ArrayList<Subject> subject = new ArrayList<Subject>();	
	MenuPrint menuprint = new MenuPrint();
	AllMenuSelect allselect = new AllMenuSelect();
	StudentMenuSelect stuselect = new StudentMenuSelect();
	GradeMenuSelect gradeselect = new GradeMenuSelect();
	Scanner scanner = new Scanner(System.in);
	GradeManage grademanage = new GradeManage();
	StudentProfessor studentprofessor = new StudentProfessor();
	OpenGrade openselect = new OpenGrade();
	
	// SubjectList subjectlist = new SubjectList();
	
	void select();
}



/*
 * �ֻ��� �޴��� ���� �� ������ִ� Ŭ����
 */
class StudentProfessor implements MenuSelect{
	static int id = 0;
	@Override
	public void select() {
		StudentSearch OpenSearch = new StudentSearch();
		// TODO Auto-generated method stub
		int menu;
		int login;
		
		menuprint.studentprofessor();
		while(true) {
			try {
				menu = scanner.nextInt();
				
				if(menu<1||menu>2) {			
					System.out.print("�л���/������ �߿��� �����ּ���.> ");
				}	
				else {								
					switch(menu) {
					case 1 :
						login = OpenSearch.openSearch(studentarr);
						if(login == -1) {
							studentprofessor.select();
							
						}
						else {
							openselect.select();
							id = login;
						}
						break;
						
					case 2 :						
						allselect.select();				
						break;
					}
				}
			}
			catch (Exception e) {
				System.out.println("�߸��� �Է��Դϴ�.");
				return;
			}
		}
			
		
	}
	
}

/*������*/
class AllMenuSelect implements MenuSelect {
	int menu;
	
	/*
	 * �ֻ��� �޴��� ����
	 * 1. �л� ����  2. ���� ����  3. ����
	 * 1~3�� �ƴ� �� �ݺ�
	 */
	@Override
	public void select() {
		menuprint.allMenuPrint();		
		System.out.print("�޴� ����> ");
		while(true) {
			try {
				menu = scanner.nextInt();
				if(menu<1||menu>4) {			
					System.out.print("����ε� �޴��� �������ּ���> ");
				}	
				else {								
					switch(menu) {
					case 1 :
						stuselect.select();		
						break;
						
					case 2 :						
						menuprint.gradeMenuPrint();									
						gradeselect.select();	
						break;
						
					case 3 :						
						studentprofessor.select();
						break;
					case 4:
						System.out.print("�����մϴ�.");
						return;	
					}
				}
			}
			catch (Exception e) {
				System.out.println("�߸��� �Է��Դϴ�.");
				return;
			}
		}
	}	
}


/*
 * �л� �޴��� ���� �� ��� ���ִ� Ŭ����
 * 
 */

class GradeMenuSelect implements MenuSelect{
	int menu;
	@Override
	public void select() {
		System.out.print("�޴� ����> ");			
	while(true) {
		try {
			menu = scanner.nextInt();
			// System.out.println(menu);
			
			if(menu<1||menu>6) {					
														
				System.out.print("����ε� �޴��� �������ּ���> ");
			}	
		else {									
			switch(menu) {
			case 1 :							
								//�����߰�
				// subjectlist.addList(studentarr);
				grademanage.inputGrade(studentarr);
				gradeselect.select();
				break;
			
			case 2 :
				grademanage.modgrade(studentarr);
				gradeselect.select();
				break;
									//��������
				
			case 3 :							
									//��������
				grademanage.delGrade(studentarr);
				gradeselect.select();
				break;
				
			case 4 :
				grademanage.printGrade(studentarr);
				gradeselect.select();
				break;
				
			case 5 :							
				allselect.select();
				break;				//�����޴�
				
			}
		}
	}
	catch (Exception e) {
		System.out.println("�߸��� �Է��Դϴ�.");
		return;
	}
	
}

}
	
}
class StudentMenuSelect implements MenuSelect {			
	int menu;
	
	/*
	 * �л� �޴��� ����
	 * 1. �л� �߰�  2. �л� ����  3. �л� ����  4. �л� ���  5. ���� �޴�
	 * 1~5�� �ƴҽ� �ݺ�
	 * 
 	 */
	@Override
	public void select() {
		menuprint.studentMenuPrint();
		System.out.print("�޴� ����> ");	
		while(true) {
			try {
				StudentManage manage = new StudentManage();
				menu = scanner.nextInt();
				// System.out.println(menu);
				
				if(menu<1||menu>5) {					
															
					System.out.print("����ε� �޴��� �������ּ���> ");
				}
				else {									
					switch(menu) {
					case 1 :							
						manage.addStudent(studentarr);
						stuselect.select();
						
						break;
					case 2 :							
						manage.modStudent(studentarr);
						stuselect.select();
						
						break;
					case 3 :							
						manage.delStudent(studentarr);
						stuselect.select();
						
						break;
					case 4 :							
						manage.printStudent(studentarr);
						stuselect.select();
						
						break;
					case 5 :						
						allselect.select();
						break;
					}
				}
			}
			catch (Exception e) {
				System.out.println("�߸��� �Է��Դϴ�.");
				return;
			}
			
		}
		
	}
	
}

/*�α��� �����Ұ�� ���� ����,������û �� */
class OpenGrade implements MenuSelect{

	

	/*int store; public int stors(){
		this.store = store;
		return store;
	}*/
	@Override
	public void select() {
		int menu;
		// TODO Auto-generated method stub
		menuprint.gradeopen();
		System.out.println("�޴� ����> ");		
		while(true) {
			try {
				InforTechSubjectList InforTemp = new InforTechSubjectList();
				
				menu = scanner.nextInt();

				// System.out.println(menu);
				
				
				if(menu<1||menu>4) {					
															
					System.out.print("����ε� �޴��� �������ּ���> ");
				}
				else {									
					switch(menu) {
					case 1 :							
						grademanage.openGrade(studentarr);
						openselect.select();
						break;
					case 2 :			
						InforTemp.addSubject();
						InforTemp.printList();
						InforTemp.CourseRegister(studentarr);
						openselect.select();
						break;
					case 3 :							
							// ���հ˻�
						
						break;
					case 4 :							
						studentprofessor.select();
						break;
					}
				}
			}
			catch (Exception e) {
				System.out.println("�߸��� �Է��Դϴ�.");
				return;
			}
			
		}
	}
	
}//end of OpenGrade



