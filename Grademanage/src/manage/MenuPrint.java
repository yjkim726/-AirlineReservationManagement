package manage;

/*
 * �޴� ����� ���� Ŭ����
 */
public class MenuPrint {
	/*
	 * �ֻ��� �޴� ��� �޼ҵ�
	 */

	void studentprofessor(){
		System.out.println("���� �л���/����� �������ֽÿ�.");
		System.out.println("�л��� (1)");
		System.out.println("������ (2)");	
	}
	 void departmentPrint(){
			System.out.println("�а��� �����Ͻÿ�.");
			System.out.println("1. ������Ű�");
			System.out.println("2. ����Ʈ���� ������");
			return;
		}
	
	void allMenuPrint() {
		System.out.println("----------MENU----------");
		System.out.println("1. �л� ����");
		System.out.println("2. ���� ����");
		System.out.println("3. ���� �޴�");
		System.out.println("4. ����");
		System.out.println("------------------------");
		return;
	}
	
	/*
	 * �л� �޴� ��� �޼ҵ�
	 */
	void studentMenuPrint() {
		System.out.println("=========STUDENT=========");
		System.out.println("1. �л� �߰�");
		System.out.println("2. �л� ����");
		System.out.println("3. �л� ����");
		System.out.println("4. �л� ���");
		System.out.println("5. ���� �޴�");
		System.out.println("=========================");
		return;
	}
	
	/*
	 * ���� �޴� ��� �޼ҵ�
	 */
	void gradeMenuPrint() {
		System.out.println("==========GRADE==========");
		System.out.println("1. ���� �߰�");
		System.out.println("2. ���� ����");
		System.out.println("3. ���� ����");
		System.out.println("4. ���� ����");
		System.out.println("5. ���� �޴�");
		System.out.println("=========================");
		return;
	}
	
	void yearMenuPrint(){
		System.out.println("==========�г⼱��==========");
		System.out.println("1�г��ϰ��  : (1)");
		System.out.println("2�г��ϰ��  : (2)");
		System.out.println("3�г��ϰ��  : (3)");
		System.out.println("=========================");
	}
	
	void gradeopen(){
		System.out.println("==========OPEN==========");
		System.out.println("1. ���� ����");
		System.out.println("2. ���� ��û");
		System.out.println("3. ���� ����");
		System.out.println("4. ���� �޴�");
		System.out.println("=========================");
	}
	
}