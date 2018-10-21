package manage;

import java.util.*;

class InforTechSubjectList {
	ArrayList<Subject> inforSubjectList = new ArrayList<Subject>();
	
	public void addSubject() {
		Subject temp = new Subject(2002118, 3, 2, "��������", "���߿�");
		inforSubjectList.add(temp);
		temp = new Subject(1981046, 3, 2,"����ũ�����μ���( I )", "��â��");
		inforSubjectList.add(temp);
		temp = new Subject(1982016, 3, 2,"����̷�", "��ä��");
		inforSubjectList.add(temp);
		temp = new Subject(2003016, 4, 1,"CCNA1", "���缳");
		inforSubjectList.add(temp);
		temp = new Subject(1979156, 3, 2,"����ȸ��( I )", "���ű�");
		inforSubjectList.add(temp);
		temp = new Subject(1998117, 3, 2,"TCP/IP", "�豳��");
		inforSubjectList.add(temp);
		temp = new Subject(1981038, 3, 1,"����ȸ�ν���", "���ű�");
		inforSubjectList.add(temp);
	}
	
	void printList() {
		System.out.println("*****���� ��û ���� ����*****");
		for(Subject temp : inforSubjectList) {
			System.out.print("���� ��ȣ : "+ temp.subNo + " ���� �̸� : " + temp.subName + " ���� : " + temp.credit);
			System.out.println("���� : " + temp.professor);
		}
		System.out.println("******************************");
	}
	
	void CourseRegister(ArrayList<Student> studentarr){
		GradeSearch gradesearch = new GradeSearch();
		Student stuTemp = new Student();
		Subject subTemp = new Subject();
	    InforTechSubjectList inforTemp = new  InforTechSubjectList();
	    inforTemp.addSubject();
	    
	    int index;
	    
	    do {
		index = gradesearch.CourseRegisterSearch(inforTemp);	 //�˻�
	    }while(index == -1);
		subTemp = inforTemp.inforSubjectList.get(index);
		stuTemp = studentarr.get(StudentProfessor.id);

		System.out.print("������ ���� ��ȣ : "+ subTemp.subNo + " ���� �̸� : " + subTemp.subName + " ���� : " + subTemp.credit);
		System.out.println(" ���� : " + subTemp.professor);
		stuTemp.subjectlist.add(subTemp);
	}
}