package manage;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeManage {
	Scanner scanner = new Scanner(System.in);
	StudentSearch search = new StudentSearch();
	Student temp = new Student();
	
	void register(){
		System.out.println("*****��������*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.print("���� ��ȣ : "+ subtemp.subNo + " ���� �̸� : " + subtemp.subName + " ���� : " + subtemp.credit);
			System.out.println(" ���� : " + subtemp.professor);
		}//end of for
		System.out.println("*******************");
		
	}
	public void inputGrade(ArrayList<Student> studentarr ) {
		
		int index;
		String quit;
		index = search.managerSearch(studentarr);
		temp = studentarr.get(index);
		
		/*System.out.println("*****��������*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.print("���� ��ȣ : "+ subtemp.subNo + " ���� �̸� : " + subtemp.subName + " ���� : " + subtemp.credit);
			System.out.println(" ���� : " + subtemp.professor);
		}//end of for
		System.out.println("*******************");*/
		register();
		do {
			System.out.print("������ �Է��� ���� ��ȣ> ");
			index = scanner.nextInt();
			//System.out.println(index);
			for(Subject subtemp : temp.subjectlist){
				if(index==subtemp.subNo){
					int grade;
					System.out.println("������ ���� : "+subtemp.subName );
					System.out.print("������ �Է��ϼ���> ");
					grade = scanner.nextInt();
					while(true) {
						if(grade<0 || grade>100) {
							System.out.print("0~100������ ������ �Է����ּ���>");
							grade = scanner.nextInt();
						}
						else
							break;
					}
					subtemp.grade = grade;
				}
				//System.out.println("-1");
			}
			System.out.print("�ٸ� ������ �Է��Ͻðڽ��ϱ�? (y/n)> ");
			quit = scanner.next();
		}while(quit.equals("y"));
	}
		
	void printGrade(ArrayList<Student> studentarr) {
		Student temp = new Student();
		int index;
		index = search.managerSearch(studentarr);
		temp = studentarr.get(index);
		
		System.out.println("*****��������*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.println(" ���� �̸� : " + subtemp.subName + " ���� : " + subtemp.grade);
		}
		System.out.println("*******************");
	}//end of printGrade
//		System.out.println(temp.SNo);
	
	
	public void delGrade(ArrayList<Student>studentarr){
		
		int subNo;
		GradeSearch search = new GradeSearch();
		
		int index = search.gradesearch(studentarr);
		temp = studentarr.get(index);
		
		register();		//�޴����
		System.out.println("������ ������ �����Ͻÿ� : ");
		subNo = scanner.nextInt();
		for(Subject subtemp : temp.subjectlist){
			if(subtemp.subNo == subNo){
												/*System.out.println(subNo);
													System.out.println(subtemp.subNo);*/
				index= temp.subjectlist.indexOf(subtemp);
				subtemp = temp.subjectlist.get(index);
				System.out.println("������ ���� : "+ subtemp.subName);
				subtemp.grade = 0;
				break;
			}
			else{
				System.out.println("�ش��ϴ� �����ȣ�� �����ϴ�.");
				break;
				
			}
		}//end of for
		
	}//end of delGrade
	
	public void modgrade(ArrayList<Student>studentarr){
		int index,inputSNo;
		int inputgrade;
		GradeSearch search = new GradeSearch();
		Subject sn = new Subject();
		
		index=search.gradesearch(studentarr);					 //�˻�
		register(); 										//����
		System.out.println("������ ������ ���� ��ȣ�� �Է��Ͻÿ� : ");
		inputSNo = scanner.nextInt();
		for(Subject subtemp : temp.subjectlist){
			if(subtemp.subNo == inputSNo){
				index = temp.subjectlist.indexOf(subtemp);
				sn=temp.subjectlist.get(index);
				System.out.println("������ ������ �Է��ϼ��� : ");
				inputgrade = scanner.nextInt();
				sn.grade = inputgrade;
				break;
			}//end of if
			else{
				System.out.println("�߸��� ���� ��ȣ�Դϴ�. �ٽ� �Է����ּ���");
				break;
			}
		}//end of for
	}//end of modgrade
	
	/*void OpenGrade(ArrayList<Student> studentarr) {
		Student temp = new Student();
		int index;
		index = search.openSearch(studentarr);
		temp = studentarr.get(index);
		
		System.out.println("*****��������*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.println(" ���� �̸� : " + subtemp.subName + " ���� : " + subtemp.grade);
		}
		System.out.println("*******************");
	}end of OpenGrade*/
	
	void openGrade(ArrayList<Student> studentarr) {
		int index;
		index = StudentProfessor.id;
		temp = studentarr.get(index);
		
		System.out.println("*****��������*****");
		for(Subject subtemp : temp.subjectlist) {
			System.out.print("���� ��ȣ : "+ subtemp.subNo + " ���� �̸� : " + subtemp.subName + " ���� : " + subtemp.credit);
			System.out.println(" ���� : " + subtemp.professor +" ���� : " + subtemp.grade);
		}
		System.out.println("*******************");
	}

}
