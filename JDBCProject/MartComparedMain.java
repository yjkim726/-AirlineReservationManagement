package JDBCProject;
import java.util.*;
public class MartComparedMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		MainMenu mainMenu = new MainMenu();
		MartJoinSVC martJoinSVC = new MartJoinSVC();
		while(true){
			mainMenu.managerMemberJoinMenu();
			//1. ������ ��� 2. ȸ�� ��� 3.ȸ�� ���� 4.ȸ�� Ż��
			int mainMenuSelected = sc.nextInt();
			if(mainMenuSelected==ManagerMemberJoin.manager){
				//1. ����������
				//-> ���⼭ ��з��ڵ��߰�/ �Һз��ڵ��߰�
				//Ű�����ڵ� �߰�
				//��ǰ���� �����̷�,�����̷�, �����̷� Ȯ��
				// �Ǹ��� ���� ���, �Ǹ��� �����̷� Ȯ��
				// ������(ȸ����嵵 ���)

				// 1. �з��ڵ�
				// 2. Ű���� �ڵ�
				// 3. ��ǰ���� �̷�
				// 4. �Ǹ��� ����
				// 5. ������ ����
			}
			else if(mainMenuSelected==ManagerMemberJoin.member){
				//2. ȸ�����
				//�Ϲ�ȸ�� �� ��Ʈ��ǥ ������ ����
				// if �Ϲ�ȸ��
				// ��ǰ���� �� �ּҺ��� �� ���
				// �Ǹ��� ���� ��û!
				// else ��Ʈ��ǥ�� ���
				// ��ǰ�߰� ���� ���� 
				mainMenu.generalMartRepresentSelected();
				int memberSelected = sc.nextInt();
				if(memberSelected==GeneralMartRepresentSelect.generalMember){
					//�Ϲ�ȸ�� ���ý�
					// 1. ��ǰ ���� -> 
					// 2. ��ٱ���
					// 3. �Ǹ��� ���� ��û
					// 4. ������
				}else if(memberSelected==GeneralMartRepresentSelect.martRepresentMember){
					//��Ʈ ��ǥ ���ý�
					//�Ǹ��� ������ �Ǿ����� Ȯ��
					//1. ��ǰ�߰�
					//2. ��ǰ����
					//3. ��ǰ����
					//4. ������
					mainMenu.goodsAddUpdateDeleteMenu();
				}
			}//end of 2.ȸ�����
			else if(mainMenuSelected==ManagerMemberJoin.join){
				//3. ȸ������
				// ȸ������ - ������ / �Ϲ�ȸ�� ����
				mainMenu.managerGeneralMemberSelected();
				int managerMemberSelected = sc.nextInt();
				
				if(managerMemberSelected==ManagerMemberSelect.managerMember){
					//������ ȸ�������� �������
					martJoinSVC.joinMember(Integer.toString(managerMemberSelected));
				}//end of ������ ȸ������
				else if(managerMemberSelected==ManagerMemberSelect.generalMember){
					//�Ϲݸ�� ȸ�� ������ ���� ���
					//managerMemberSelected�� int���̹Ƿ� martJoinSVC.joinMember(String)���̿��� ����ȯ
					martJoinSVC.joinMember(Integer.toString(managerMemberSelected));
				}//end of �Ϲݸ�� ȸ������
			}//end of 3.ȸ������ 
			else if(mainMenuSelected==ManagerMemberJoin.secession){
				//4. ȸ��Ż��
				//������Ż���... �ϴܺ���
				martJoinSVC.secessionMember();
			}//end of 4.ȸ�� Ż��
			else{
				System.out.println("1~4���߿��� ���ϼ���");
			}//end of 1~4���߿� ���ϼ���
		}//end of while
	}//end of main

}//end of MartComparedMain

class MainMenu{
	public void managerMemberJoinMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ������ ���                   |");
		System.out.println("| 2. ȸ��    ���                   |");
		System.out.println("| 3. ȸ��    ����                   |");
		System.out.println("| 4. ȸ��    Ż��                   |");
		System.out.println("��====================��");
	}
	public void martGoodsDiscountMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��Ʈ    ����                   |");
		System.out.println("| 2. ��ǰ    ����                   |");
		System.out.println("| 3. ����    ����                   |");
		System.out.println("| 4. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}//end of managerMemberJoin()
	
	public void martAddUpdateDeleteMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��Ʈ    �߰�                   |");
		System.out.println("| 2. ��Ʈ    ����                   |");
		System.out.println("| 3. ��Ʈ    ����                   |");
		System.out.println("| 4. ��Ʈ    ���                   |");
		System.out.println("| 5. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}//end of martAddUpdateDelete()
	public void goodsAddUpdateDeleteMenu(){
		System.out.println("��====================��");
		System.out.println("| 1. ��ǰ    �߰�                   |");
		System.out.println("| 2. ��ǰ    ����                   |");
		System.out.println("| 3. ��ǰ    ����                   |");
		System.out.println("| 4. ��ǰ    �˻�                   |");
		System.out.println("| 5. ��    ��   ��                   |");
		System.out.println("| 6. �ڷ�    ����                   |");
		System.out.println("��====================��");
	}//end of goodsAddUpdateDeleteMenu
	
	public void managerGeneralMemberSelected(){
		System.out.println("��====================��");
		System.out.println("| 1. ������ ����                   |");
		System.out.println("| 2. �Ϲ�ȸ�� ����                |");
		System.out.println("��====================��");
	}//end of managerGeneralMemberSelected
	
	public void generalMartRepresentSelected(){
		System.out.println("��====================��");
		System.out.println("| 1. �Ϲ�ȭ�� ���                |");
		System.out.println("| 2. ��Ʈ��ǥ�� ���             |");
		System.out.println("��====================��");
	}//end of generalMartRepresentSelected
	
	
}//end of MainMenu

class MartAddUpdateDelete{
	static int martAdd = 1;
	static int martUpdate = 2;
	static int martDelete = 3;
	static int martList = 4;
	static int previous = 5;
}

class ManagerAdmin{
	static int mart = 1;
	static int goods = 2;
	static int discount = 3;
	static int previous = 4;
}

class ManagerMemberJoin{
	static int manager = 1;
	static int member = 2;
	static int join =3;
	static int secession = 4;
}
class GoodsAddUpdateDelete{
	static int goodsAdd = 1;
	static int goodsUpdate = 2;
	static int goodsDelete = 3;
	static int goodsSearch = 4;
	static int previous = 5;
}
class ManagerMemberSelect{
	static int managerMember = 1;
	static int generalMember = 2;
}

class GeneralMartRepresentSelect{
	static int generalMember = 1;
	static int martRepresentMember = 2;
}