package calculator.main;

import java.io.*;
import java.util.*;

/**
 * Subject
 * ���� ������ �����ϴ� Ŭ����
 */
public class Subject implements java.io.Serializable {							// Serializable�� ����ȭ�� File ������� ���� ���
	private int subjectNumber;																// �ĺ� �� ���� ���� ��ȣ
																					// ������ �б� ������ �����Ͽ���, �������� �ӽ÷� ������ �ξ����ϴ�.
	private int credit;																		// �̼� ����
	private int division;																	// ���� ����(0)/��Ʈ��ũ ����(1)/���� ����(2)/Ÿ����(3)/����(4) ����
	private int year;																		
	// �г� �б� ���� 1-1(0) 1-2(1) 2-1(2) 2-2(3) 3-1(1) 3-2(2) , ���� �� Ÿ������ ó���� -1, ���߿� �߰����ָ� �г� �бⰡ ����ȴ�.
	private String subjectName;																// ���� �̸�
	
	Subject(int subjectNumber, String subjectName, int credit, int division, int year) {
		this.subjectNumber = subjectNumber;
		this.subjectName = subjectName;
		this.credit = credit;
		this.division = division;
		this.year = year;
	}// end of Constructor
	
	Subject() {
		// Empty Constructor
	}

	/*
	 * Getter
	 */
	int getNumber() {								
		return subjectNumber;
	}// subjectNumber�� return�ϴ� getMethod
	
	int getCredit() {
		return credit;
	}// credit�� return�ϴ� getMethod
	
	String getName() {
		return subjectName;
	}// name�� return�ϴ� getMethod
	
	int getDivision() {
		return division;
	}// division�� return�ϴ� getMethod
	
	int getYear() {
		return year;
	}// year�� return�ϴ� getMethod
	
	String getDivisionName() {
		if(division == 0 || division == 1 || division == 2  || division == 3) return "����"; 
		else return "����";
	}// division�� ���� �̸��� ��ȯ���ִ� getMethod
	
	/*
	 * Setter
	 */
	void setNumber(int subjectNumber) {
		this.subjectNumber = subjectNumber;
	}// number�� �������ִ� setMethod 
	
	void setName(String subjectName) {
		this.subjectName = subjectName;
	}// name�� �������ִ� setMethod
	
	void setCredit(int credit) {
		this.credit = credit;
	}// credit�� �������ִ� setMethod
	
	void setDivision(int division) {
		this.division = division;
	}// division�� �������ִ� setMethod
	
	void setYear(int year) {
		this.year = year;
	}// year�� �������ִ� setMethod
}// end of class(Subject)

/**
 * SubjectList
 * ���� �߰��� ������ ������ ��Ƴ��� Ŭ����
 * Singleton ����
 */
class SubjectList {
	private static SubjectList subjectList = new SubjectList(); 			// Singleton ����. SubjectList�� �� �ϳ��� ��� ����
	ArrayList<Subject> subjectArr = new ArrayList<Subject>();				// ���� ���� ������ �����ϴ� ArrayList
	
	SubjectList() {
		// Empty Constructor
	}
	
	public static SubjectList getInstance() {
		return subjectList;
	}// Singleton ���Ͽ��� subjectList�� return���ش�.
	// ����� SubjectList.getInstance() �� ����Ѵ�.
	
	void addSubject(Subject tempSubject) {
		subjectArr.add(tempSubject);
	}// �޾ƿ� tempSubject�� subjectArr�� �߰��ϴ� Method
	
	void delSubject(Subject tempSubject) {
		subjectArr.remove(tempSubject);
	}// �޾ƿ� tempSubject�� subjectArr���� �����ϴ� Method
	
	Subject getSubject(int subjectNumber){
		// Subject�� ã�Ƽ� return���ִ� getter 
		Subject tempSubject;
		
		Iterator<Subject> subjectListItr = subjectArr.iterator();
		
		while(subjectListItr.hasNext()) {
			tempSubject = subjectListItr.next();
			if(tempSubject.getNumber() == subjectNumber) {
				// �Էµ� subjectNumber�� tempSubject�� subjectNumber�� ���� ���
				return tempSubject;
				// tempSubject�� return ���ش�.
			}
		}
		return null;
	}// end of Method(getSubject)
}// end of class(SubjectList)

/**
 * Curriculum
 * ���� �߰��� ������ ���� ����� �����ϴ� Ŭ����
 * Singleton ����
 * file(curriculum.dat)���� ������ ��� �´�.
 */
class Curriculum {
	private static Curriculum curriculum = new Curriculum(); 					// Singleton ����, Curriculum�� �� �ϳ��� ��� ����
	ArrayList<Subject> curriculumArr = new ArrayList<Subject>();				// ���� ������ ������ ����ǰ�, ���� ����¿� ���Ǵ� ArrayList
	
	Curriculum() {
		// Empty Constructor
	}
	
	public static Curriculum getInstance() {
		return curriculum;
	}// Singleton ���Ͽ��� �����ϱ� ���� ����ϴ� Method
	
	void addSubject(int subjectNumber, String subjectName, int credit, int division, int year) {
		Subject tempSubject =  new Subject(subjectNumber, subjectName, credit, division, year);
		curriculumArr.add(tempSubject);
	}// tempSubject�� ���� �Ķ���͸� ���� curriculumArr�� ���� �������ִ� Method  
	
	void modSubject(Subject subject,int subjectNumber, String subjectName, int credit, int division,int year) {
		subject.setNumber(subjectNumber);
		subject.setName(subjectName);
		subject.setCredit(credit);
		subject.setDivision(division);
		subject.setYear(year);
	}// setter�� ����� subject�� ������ �ٲ��ִ� Method
	
	void delSubject(Subject subject) {
		curriculumArr.remove(subject);
	}// curriculumArr �ȿ� �ִ� subject�� �����ϴ� Method
	
	Subject getSubject(int subjectNumber){
		// ���� subjectNumber�� curriculum�� �ִ� subject�� ã�Ƴ��� Method
		Subject tempSubject;
		Iterator<Subject> subjectListItr = curriculumArr.iterator();
		
		while(subjectListItr.hasNext()) {
			tempSubject = subjectListItr.next();
			if(tempSubject.getNumber() == subjectNumber) {
				// �Է��� subejctNumber�� tempSubject�� subjectNumber�� �������
				return tempSubject;
				// tempSubject�� return ���ش�.
			}
		}
		return null;
	}// end of Method(getSubject)
	
	void loadFile() {
		// ����(curriculum.dat)�� �о���ִ� Method
		FileInputStream fis = null;												// ������ �о���� ���� Stream
		ObjectInputStream ois = null;											// ���Ͽ��� ��ü(Object)�� �о���� ���� Stream
		try{
			fis = new FileInputStream("curriculum.dat");						// File�� 'curriculum.dat'�� �����Ѵ�.
			ois = new ObjectInputStream(fis);									// ois�� File�� object�� �о�´�.
			
			curriculumArr =(ArrayList<Subject>) ois.readObject();				// curriculumArr�� ois���� �о�� object�̴�.
			// curriculum.dat�� ����Ǵ� ���� ArrayList�̹Ƿ� �о�� ��ü(Object���̴�)�� ArrayList�� ����ȯ�Ѵ�.
			// Generics�� ����Ͽ��µ� ois.readObject()�� Object���̱⿡ warning�� �� ���� 
		}
		catch(Exception e){
			System.out.println("File Load Failure");
			// ���� load ���н� ���� Ŀ��ŧ������ ��ü
			// addSubject( subjectNumber , subjectName, credit, division, year)
			
			addSubject(1979133,"����Ż����", 3 ,0, 0);
			addSubject(1981026,"��������", 3, 0, 0);
			addSubject(1985052,"���ʽ���", 2, 0, 0);
			addSubject(1992020,"ȸ�νùķ��̼�", 2, 0, 0);
			addSubject(1993019,"ȸ���̷�", 3, 0, 0);
			addSubject(1997058,"������Ű���", 2, 0, 0);
			addSubject(2007005,"���α׷����Թ�", 3, 0, 0);
			// 1�г� 1�б�
			addSubject(1981035,"DATA���", 3, 0, 1);
			addSubject(1989038,"����Ż����", 2, 0, 1);
			addSubject(1990002,"CAD", 2, 0, 1);
			addSubject(1997072,"C-���α׷���", 3, 0, 1);
			addSubject(1998017,"����Ż��������", 2, 0, 1);
			addSubject(2002104,"ȸ�ν���", 2, 0, 1);
			addSubject(2002105,"ȸ���ؼ�", 3, 0, 1);
			addSubject(2006028,"�ý��ۿ", 2, 1, 1);
			addSubject(1983011,"�����ڱ���", 3, 2, 1);
			// 1�г� 2�б�
			addSubject(1979156,"����ȸ��(��)", 3, 0, 2);
			addSubject(1981038,"����ȸ�ν���", 2, 0, 2);
			addSubject(1981046,"����ũ�����μ���(��)", 3, 0, 2);
			addSubject(1982016,"����̷�", 3, 0, 2);
			addSubject(1998117,"TCP/IP", 3, 0, 2);
			addSubject(2002118,"��������", 3, 1, 2);
			addSubject(2003016,"CCNA1", 4, 1, 2);
			addSubject(2002107,"�������", 3, 2, 2);
			addSubject(1993017,"��ű�����", 2, 2, 2);
			// 2�г� 1�б�
			addSubject(1981047,"����ũ�����μ���(��)", 3, 0, 3);
			addSubject(2002109,"���������", 3, 0, 3);
			addSubject(2012002,"������ȸ�μ���", 3, 0, 3);
			addSubject(2003017,"CCNA2", 4, 1, 3);
			addSubject(2006027,"JAVA ���α׷���", 3, 1, 3);
			addSubject(2011051,"��������", 3, 1, 3);
			addSubject(1979157,"����ȸ��(��)", 3, 2, 3);
			addSubject(1996017,"����Ż��Ž���", 2, 2, 3);
			addSubject(2006023,"�����м���", 3, 2, 3);
			addSubject(2009018,"�ʰ����� ���� �� �ǽ�", 3, 2, 3);
			// 2�г� 2�б�
			addSubject(1998076,"���� ������Ʈ", 3, 0, 4);
			addSubject(1998023,"��Ƽ�̵�����", 3, 1, 4);
			addSubject(2003018,"CCNA3", 4, 1, 4);
			addSubject(2000036,"�����α׷���", 3, 1, 4);
			addSubject(2006029,"��������", 3, 1, 4);
			addSubject(2006031,"���� LAN", 3, 1, 4);
			addSubject(1999059,"������Žý���", 3, 2, 4);
			addSubject(2006024,"RF ȸ��", 3, 2, 4);
			addSubject(2006025,"���׳����� �� �ǽ�", 3, 2, 4);
			addSubject(2006026,"��Žùķ��̼�", 3, 2, 4);
			// 3�г� 1�б�
			addSubject(1984041,"���� �ǽ�", 3, 0, 5);
			addSubject(1994030,"�ý���������Ʈ", 3, 0, 5);
			addSubject(1989064,"���� ����", 2, 0, 5);
			addSubject(2006032,"������ ��Ÿ�", 3, 0, 5);
			addSubject(2012003,"�ǿ�۾���", 3, 0, 5);
			addSubject(2003019,"CCNA4", 4, 1, 5);
			addSubject(2002111,"�̵���Žý���", 3, 2, 5);
			addSubject(2002112,"������Žý���", 3, 2, 5);
			// 3�г� 2�б�
			addSubject(9000001,"Ÿ���� 2����", 2, 3, -1);
			addSubject(9000002,"Ÿ���� 3����", 3, 3, -1);
			addSubject(9000003,"Ÿ���� 4����", 4, 3, -1);
			// Ÿ����
			addSubject(8000001,"����Ȱ������", 2, 4, -1);
			addSubject(8000002,"��ȭ�Ǳ��", 2, 4, -1);
			addSubject(8000003,"���ͺ��������", 2, 4, -1);
			addSubject(8000004,"�μ���������", 2, 4, -1);
			addSubject(8000005,"�ΰ��ɸ�������", 2, 4, -1);
			addSubject(8000006,"����ȸȭ", 2, 4, -1);
			addSubject(8000007,"�ʱ޿��۹�", 2, 4, -1);
			addSubject(8000008,"������", 2, 4, -1);
			addSubject(8000009,"�������Ŭ����", 2, 4, -1);
			addSubject(8000010,"�������", 2, 4, -1);
			addSubject(8000011,"�߱޿���ȸȭ", 2, 4, -1);
			addSubject(8000012,"�����Ϻ���ȸȭ", 2, 4, -1);
			addSubject(8000013,"�����߱���ȸȭ", 2, 4, -1);
			addSubject(8000014,"����������", 2, 4, -1);
			addSubject(8000015,"21����Ʈ����Ͱ濵", 2, 4, -1);
			addSubject(8000016,"����������ڱ�濵", 2, 4, -1);
			addSubject(8000017,"â�ǿ�â�����", 2, 4, -1);
			addSubject(8000018,"����������", 2, 4, -1);
			addSubject(8000019,"��Ȱ���Ǽ���", 2, 4, -1);
			addSubject(8000020,"��ȭ������", 2, 4, -1);
			addSubject(8000021,"��������ȭ", 2, 4, -1);
			addSubject(8000022,"��ȭ�̾߱�Ϳ����б�", 2, 4, -1);
			addSubject(8000023,"�ϻ��Ȱ��", 2, 4, -1);
			addSubject(8000024,"�����ȸ���ΰ�", 2, 4, -1);
			addSubject(8000025,"��Ȱ������", 2, 4, -1);
			addSubject(8000026,"���������", 2, 4, -1);
			addSubject(8000027,"��������Ű�â��", 2, 4, -1);
			addSubject(8000028,"Ű����� �� IT�� ����͹̷�", 2, 4, -1);
			// �����δ� ���� ���� ����
			addSubject(7000001,"����濵�� ����Ͻ� ����", 2, 4, -1);
			addSubject(7000002,"����ģȭ�� ����濵", 2, 4, -1);
			addSubject(7000003,"���� â��", 2, 4, -1);
			addSubject(7000004,"��ȭ������ ���丮�ڸ� ����", 2, 4, -1);
			addSubject(7000005,"��ȭ����� ��ȸ����", 2, 4, -1);
			addSubject(7000006,"��ȭ������ ������ ������ ������ ����", 2, 4, -1);
			addSubject(7000007,"21���� ����� �����", 2, 4, -1);
			addSubject(7000008,"��ä�ɸ��� �����Ȱ", 3, 4, -1);
			addSubject(7000009,"�۷ι� �ô��� ������ ��ġ", 3, 4, -1);
			addSubject(7000010,"���İ� ���蹮ȭ", 3, 4, -1);
			addSubject(7000011,"���Ͻ��� ���� ��", 2, 4, -1);
			addSubject(7000012,"UCC ��ȹ, ����, ���� �� Ȱ��", 3, 4, -1);
			addSubject(7000013,"�׸� IT�� ����", 3, 4, -1);
			addSubject(7000014,"��������� ����", 3, 4, -1);
			addSubject(7000015,"�����ϴ� ��ȭ������ ���� ���� ����", 2, 4, -1);
			addSubject(7000016,"���а� ��ȭ", 2, 4, -1);
			addSubject(7000017,"��Ȱ ���� ���� ����", 3, 4, -1);
			addSubject(7000018,"������ �ǻ������ �����ذ�", 2, 4, -1);
			// ����
		}finally{						// Exception�� �߻��ϰ� ���ϰ� ó����.
			if(fis != null) {
				try{fis.close();}		// fis ����
				catch(IOException e){} // end of try
			}
			if(ois != null) {
				try{ois.close();} 		// ois ����
				catch(IOException e){} // end of try
			}
		}// end of try
	}// end of Method(loadFile)
}// end of class(Currciulum)