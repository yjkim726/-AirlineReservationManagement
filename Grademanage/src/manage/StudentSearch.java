package manage;

// �ڹ� Ŭ����
import java.util.*;

/*
 * �л��� �й��� �޾� ������ ã���ִ� Ŭ����
 */

/*
 * �л��� �й��� �޾Ƽ� �л� List�� �ε����� ��ȯ��
 * List�� �ε��� ��ȯ
 */
class StudentSearch {
	int input_SNo, index;
	Scanner scanner = new Scanner(System.in);

	public int openSearch(ArrayList <Student> studentarr){
		int input_SNo,index;
		System.out.println("�л� ���� �α��� : (hint : �й�) ");
		input_SNo = scanner.nextInt();
		for(Student temp : studentarr){
			if(input_SNo == temp.SNo){
				System.out.println("���ӿ� �����Ͽ����ϴ�.");
				index = studentarr.indexOf(temp);
				//����ٰ� ���� ������ �ö� �޼ҵ�
		
				/*�迭��ȣ = �����ȣ return*/
				return index;
			}
		}
		System.out.println("�α��ο� �����Ͽ����ϴ�.");
		return -1;
	}
	
	public int managerSearch(ArrayList <Student> studentarr){
		int input_SNo,index;
		System.out.println("�л� ���� �α��� : (hint : �й�) ");
		input_SNo = scanner.nextInt();
		for(Student temp : studentarr){
			if(input_SNo == temp.SNo){
				System.out.println("���ӿ� �����Ͽ����ϴ�.");
				index = studentarr.indexOf(temp);
				//����ٰ� ���� ������ �ö� �޼ҵ�
				return index;
				/*�迭��ȣ = �����ȣ return*/
			}
			else
				System.out.println("�α��ο� �����Ͽ����ϴ�.");
				/*�����ȣã��*/ 
				
		}
		return -1;
	}
	
	/*
	 * �л��� �й��� �޾Ƽ� �л� List�� �ε����� ��ȯ��
	 * List�� �ε��� ��ȯ
	 */
	int search(ArrayList<Student> studentarr) {
		System.out.print("�˻��� �л��� �й��� �Է��Ͻÿ� (�α���)> ");
		input_SNo = scanner.nextInt();
		for(Student temp : studentarr ) {
			if(input_SNo == temp.SNo){
				index = studentarr.indexOf(temp);
				// System.out.println(index);
				return index;
			}
		}
		return -1;
		
	}
	
	/*
	 * �л��� �й��� �޾Ƽ� �ߺ��Ǵ� ���� �ִ��� Ȯ��
	 * �ߺ��Ǵ� ���� ���� ��� 1�� ��ȯ
	 * �ߺ����� ���� ��� -1�� ��ȯ
	 */
	int search(ArrayList<Student> studentarr, int input_SNo) {
		// System.out.println(input_SNo);
		for(Student temp : studentarr ) {
			if(input_SNo == temp.SNo){
				// System.out.println(index);
				return 1;
			}
		}
		return -1;
		
	}
}
