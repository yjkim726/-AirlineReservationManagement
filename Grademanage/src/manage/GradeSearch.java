package manage;

import java.util.*;

public class GradeSearch {
	Scanner scanner = new Scanner(System.in);
	int index,subNo,inputNo;
	Subject sn;

	
	public int gradesearch(ArrayList<Student>studentarr){

			System.out.println("�л� ���� �α��� : (hint : �й�) ");
			inputNo = scanner.nextInt();
			for(Student temp : studentarr){
				if(temp.SNo == inputNo){
					index=studentarr.indexOf(temp);
					return index;
			}//end of if
			else{
				System.out.println("�α��ο� �����Ͽ����ϴ�.");
			}//end of else
		}//end of for
			return -1;
	}//end of gradesearch
	
	public int CourseRegisterSearch(InforTechSubjectList listTemp){
		int courseNo;
		//Student temp = new Student();
		
			System.out.println("������û�� �����ȣ�� �Է��ϼ���.");
			courseNo = scanner.nextInt();
			for(Subject subtemp : listTemp.inforSubjectList){
				// System.out.println("for���ȿ� index : "+index);
				if(courseNo == subtemp.subNo){
				// System.out.println("if���ȿ� subtemp : "+subtemp.subNo);
				int index = listTemp.inforSubjectList.indexOf(subtemp);
				// System.out.println("if���ȿ� subNo : "+subtemp.subNo);
				return index;
				}
			}
			System.out.println("���� ��ȣ�� �߸��Է� �Ǿ����ϴ�. �ٽ� �Է����ּ���.");
			return -1;
	}// end of CourseRegisterSearch
}
