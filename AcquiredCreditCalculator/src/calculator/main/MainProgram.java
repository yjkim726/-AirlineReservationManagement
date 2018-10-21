package calculator.main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * MainProgrm
 * main �޼ҵ带 ������ Ŭ����
 */
public class MainProgram {
	static Dimension dim =  Toolkit.getDefaultToolkit().getScreenSize();									// ��ũ���� ũ�⸦ �޾ƿ��� ���� Dimension
	static final int SCREEN_WIDTH =  (dim.width);														// ��ũ���� Ⱦ
	static final int SCREEN_HEIGHT =  (dim.height);														// ��ũ���� ����
	public static void main(String args[]) {
		Curriculum.getInstance().loadFile();																
		// curriculum.dat ������ �ҷ��´�. �ڼ��� ���� Curriculum -> loadFile() ��
		Connection conn = null;																				// DB���� ������ �ϴ� Connection Ÿ���� ����
		try {
			Class.forName("com.mysql.jdbc.Driver");															// mysql driver�� �����Ѵ�.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquiredCreditDB","root","");	
			// localhost(�ڱ� �ڽ� 127.0.0.1)�� 3306��Ʈ(mysql ��ġ�� ������)�� ����ϴ� mysql�� �ִ� acquiredCreditDB���� ������ �õ�.
			// "root", "" �� ID�� PASSWORD. ������ PASSWORD�� ��� ""�̴�.
			// DB����� ���ؼ��� MySQL�� acquiredCreditDB�� �����ؾ� �ȴ�.
			System.out.println("�����ͺ��̽� ���� �Ϸ�");
			new DBControlWindow("DBControl");
			// ������ ���̽� ������ �Ϸ�Ǹ� DB�� ���� ���Ϸ� �ű� �� �ִ� â�� ��Ÿ����.
			conn.close();																					// DB���� ������ ���´�.
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�."+ cnfe.getMessage());
		}
		catch (SQLException se) {
			System.out.println(se.getMessage());
		}// end of try
		new MainWindow("��� ���� ����");																	// MainWindow�� �����Ѵ�.
	}// end of Method(main)
}// end of class(MainProgram)

/**
 * MainWindow
 * ù ȭ��
 */
class MainWindow extends JFrame {
	private JPanel newPanel;
	private JLabel newLabel;
	private JButton newButton;
	private Font newFont;
	// JComponent�� ����ϱ� ���� Ŭ���� ������
	private CardLayout cardLayout = new CardLayout();												// CardLayout�� ����ϱ� ���� Ŭ���� ����
	
	MainWindow(String title) {
		super(title);
		getContentPane().setLayout(cardLayout);														// ContentPane �� Layout�� CardLayout���� �����Ѵ�.

		getContentPane().add("��� ���� ����" , new MainWindow_MainPanel(this));
		// '��� ���� ����'��� �̸��� ���� 1��° �г��� ContentPane�� �߰��Ѵ�. 
		getContentPane().add("���� ���� �߰��ϱ�" , new MainWindow_SubjectAddMenu(this));
		// '���� ���� �߰��ϱ�'��� �̸��� ���� 2��° �г��� ContentPane�� �߰��Ѵ�.
		
	}// end of Constructor
	
	public CardLayout getCardLayout() {																// CardLayout�� ������� get
		return cardLayout;
	}// end of Method(getCardLayout)
	
	/*
	 * MainWindow_MainPanel
	 * MainWindow�� 1��° �г�
	 */
	class MainWindow_MainPanel extends JPanel implements ActionListener  {
		private JFrame frame;																		
		// MainWindow�� frame�� �޾ƿ��� ���� JFrame
		
		MainWindow_MainPanel(JFrame f) {
			frame = f;
			setLayout(new GridLayout(3,1));											// �� �г��� GridLayout(3�� 1��)���� ����
			
			// ���⼭ ���Ǵ� newFont, newPanel ���..�� MainWindow�� Ŭ���� �����̴�.
			
			newFont = new Font("gulim", Font.PLAIN, 20);							// ����ü�� ũ�� 20���� ��Ʈ�� �����Ѵ�.
			newPanel = new JPanel(new FlowLayout(1,30,30));							
			// ���� �г�(GridLayout�� 1�� 1��)�� FlowLayout(�������,���� 30,30)���� ����
			newLabel = new JLabel("������Ű� ��� ���� ����");					// Label ����
			newLabel.setFont(newFont);												// Label �� ������ ��Ʈ�� �����Ѵ�.
			newPanel.add(newLabel);													// ���� �гο� Label�� �߰��Ѵ�.
			add(newPanel);															// ��ü �гο� ���� �г��� �߰��Ѵ�.
			
			newPanel = new JPanel(new FlowLayout(1,10,10));
			// ���� �г�(GridLayout�� 2�� 1��)�� FlowLayout(�������,���� 10,10)���� ����
			newButton = new JButton("���� ���� �߰��ϱ�");							// Button ����. ������ '���� ���� �߰��ϱ�'
			newButton.setPreferredSize(new Dimension(150,50));						// Button �� ũ�⸦ 150,50���� �����Ѵ�.
			newButton.addActionListener(this);										
			// Button�� ActionListener�� �߰��Ѵ�. ActionListener�� MainWindow_MainPanel ��ü�� ActionListener �̹Ƿ� this�̴�.
			// ActionListener�� ������ actionPerformed �� ����
			newPanel.add(newButton);												// ���� �гο� Button�� �߰��Ѵ�.
			add(newPanel);															// ��ü �гο� ���� �г��� �߰��Ѵ�.
			
			newPanel = new JPanel(new FlowLayout(1,10,10));	
			// ���� �г�(GridLayout�� 3�� 1��)�� FlowLayout(�������,���� 10,10)���� ����
			newButton = new JButton("��� ���� Ȯ���ϱ�");							// Button ����. ������ '��� ���� Ȯ���ϱ�'
			newButton.setPreferredSize(new Dimension(150,50));						// Button �� ũ�⸦ 150,50���� �����Ѵ�.
			newButton.addActionListener(this);
			// Button�� ActionListener�� �߰��Ѵ�. ActionListener�� MainWindow_MainPanel ��ü�� ActionListener �̹Ƿ� this�̴�.
			// ActionListener�� ������ actionPerformed �� ����
			newPanel.add(newButton);												// ���� �гο� Button�� �߰��Ѵ�.
			add(newPanel);															// ��ü �гο� ���� �г��� �߰��Ѵ�.
			
			frame.setSize(300,300);													// ������ ũ�⸦ 300,300���� �����Ѵ�.
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					// �����츦 �� �� java�� ����ȴ�.
			frame.setLocation(((MainProgram.SCREEN_WIDTH/2)-frame.getWidth()/2),((MainProgram.SCREEN_HEIGHT/2)-frame.getHeight()/2));
			// �����찡 ���߾ӿ� ��ġ�ϵ��� �̵��Ѵ�.
			frame.setVisible(true);													// ���û��·� �Ѵ�.
			frame.setResizable(false);												// ������ ũ�� ���� �Ұ� ����
		}//end of Constructor
		
		public void actionPerformed(ActionEvent arg0) {
			// MainWindow_MainPanel�� ActionListener
			switch(arg0.getActionCommand()) {
			// arg0.getActionCommand()�� ���� ��ư�� ������ �������� ������ ���̴�.
				case "���� ���� �߰��ϱ�" :													
					// ���� ��ư�� ������ '���� ���� �߰��ϱ�' �� ���
					frame.setSize(300,350);													// ������ ũ�⸦ 300,350���� ����
					getCardLayout().show(frame.getContentPane(), "���� ���� �߰��ϱ�");		// CardLayout�� �޾ƿ� 2��° �гη� �ٲ۴�.
					break;
				case "��� ���� Ȯ���ϱ�" :
					// ���� ��ư�� ������ '��� ���� Ȯ���ϱ�' �� ���
					new MainWindow_AcquiredCredit("��� ���� Ȯ���ϱ�");					// MainWindow_AcquiredCredit�� �����Ѵ�.
					dispose();																// �� ������� ����
					break;
			}// end of switch
		}
	}
	
	/*
	 * MainWindow_SubjectAddMenu
	 * MainWindow�� 2��° �г�
	 */
	class MainWindow_SubjectAddMenu extends JPanel implements ActionListener {
		private JFrame frame;
		// MainWindow�� frame�� �޾ƿ��� ���� JFrame
		
		MainWindow_SubjectAddMenu(JFrame f) {
			frame = f;
			setLayout(new GridLayout(7,1));								// �� �г��� GridLayout(7�� 1��)���� ����
			
			// ���⼭ ���Ǵ� newFont, newPanel ���..�� MainWindow�� Ŭ���� �����̴�.
			
			newFont = new Font("gulim", Font.PLAIN, 15);				// ��Ʈ�� ����ü, ũ�� 15�� ���� PLAIN�� �ƹ����¾ƴ�(����X,�����X)
			newPanel = new JPanel(new FlowLayout(1,10,10));				// ���� �г�(1�� 1��) FlowLayout���� ����
			newLabel = new JLabel("�޴��� �������ּ���");				// Label ����
			newLabel.setFont(newFont);									// Label�� ��Ʈ�� ������ ��Ʈ�� ����
			newPanel.add(newLabel);										// ���� �гο� Label �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// ���� �г�(2�� 1��) FlowLayout���� ����
			newButton = new JButton("��Ʈ��ũ ������Ű�");				// Button ����. ������ '��Ʈ��ũ ������Ű�'
			newButton.addActionListener(this);							// ActionListener �߰�. �ڼ��� ������ actionPerformed ����
			newButton.setPreferredSize(new Dimension(200,30));			// Button �� ũ�⸦ 200, 30���� ����
			newPanel.add(newButton);									// ���� �гο� Button �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// ���� �г�(3�� 1��) FlowLayout���� ����
			newButton = new JButton("���� ������Ű�");					// Button ����. ������ '���� ������Ű�'
			newButton.addActionListener(this);							// ActionListener �߰�. �ڼ��� ������ actionPerformed ����
			newButton.setPreferredSize(new Dimension(200,30));			// Button �� ũ�⸦ 200, 30���� ����
			newPanel.add(newButton);									// ���� �гο� Button �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// ���� �г�(4�� 1��) FlowLayout���� ����
			newButton = new JButton("Ÿ����");							// Button ����. ������ 'Ÿ����'
			newButton.addActionListener(this);							// ActionListener �߰�. �ڼ��� ������ actionPerformed ����
			newButton.setPreferredSize(new Dimension(200,30));			// Button �� ũ�⸦ 200, 30���� ����
			newPanel.add(newButton);									// ���� �гο� Button �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// ���� �г�(5�� 1��) FlowLayout���� ����
			newButton = new JButton("����");							// Button ����. ������ '����'
			newButton.addActionListener(this);							// ActionListener �߰�. �ڼ��� ������ actionPerformed ����
			newButton.setPreferredSize(new Dimension(200,30));			// Button �� ũ�⸦ 200, 30���� ����
			newPanel.add(newButton);									// ���� �гο� Button �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// ���� �г�(6�� 1��) FlowLayout���� ����
			newButton = new JButton("���� ����");						// Button ����. ������ '���� ����'
			newButton.addActionListener(this);							// ActionListener �߰�. �ڼ��� ������ actionPerformed ����
			newButton.setPreferredSize(new Dimension(200,30));			// Button �� ũ�⸦ 200, 30���� ����
			newPanel.add(newButton);									// ���� �гο� Button �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
			
			newPanel = new JPanel(new FlowLayout(1,10,5));				// ���� �г�(7�� 1��) FlowLayout���� ����
			newButton = new JButton("���� �޴�");						// Button ����. ������ '���� �޴�'
			newButton.addActionListener(this);							// ActionListener �߰�. �ڼ��� ������ actionPerformed ����
			newButton.setPreferredSize(new Dimension(200,30));			// Button�� ũ�⸦ 200,30���� ����
			newPanel.add(newButton);									// ���� �гο� Button �߰�
			add(newPanel);												// ��ü �гο� ���� �г� �߰�
		}// end of constructor
		
		public void actionPerformed(ActionEvent arg0) {
			// MainWindow_SubjectAddMenu �� ActionListener
			switch(arg0.getActionCommand()) {
			// arg0.getActionCommand()�� ��ư�� ������ �� ��ư�� ������ �����´�.
				case "��Ʈ��ũ ������Ű�" :								// ��ư�� ������ '��Ʈ��ũ ������Ű�'�� ���
					new SubjectWindow("��Ʈ��ũ ������Ű� ���� ����",1);	
					// SubjectWindow�� �����ϰ� Division�� 1(��Ʈ��ũ ����)�� �Ѵ�.
					break;
				case "���� ������Ű�" :									// ��ư�� ������ '���� ������Ű�'�� ���
					new SubjectWindow("���� ������Ű� ���� ����",2);
					// SubjectWindow�� �����ϰ� Division�� 2(���� ����)�� �Ѵ�.
					break;
				case "Ÿ����" :												// ��ư�� ������ 'Ÿ����' �� ���
					new SubjectWindow("Ÿ���� ���� ����",3);
					// SubjectWindow�� �����ϰ� Division�� 3(Ÿ����)���� �Ѵ�.
					break;
				case "����" :												// ��ư�� ������ '����' �� ���
					new SubjectWindow("���� ���� ����",4);
					// SubjectWindow�� �����ϰ� Division�� 4(����)���� �Ѵ�.
					break;
				case "���� ����" :											// ��ư�� ������ '���� ����'�� ���
					new PrintSubjectListWindow("���� ����");
					// PringSubjectListWindow�� �����Ѵ�.
					break;
				case "���� �޴�" :											// ��ư�� ������ '���� �޴�'�� ���
					frame.setSize(300,300);									// MainWindow�� frame size�� 300, 300���� �Ѵ�.
					getCardLayout().show(frame.getContentPane(), "��� ���� ����");
					// CardLayout�� �ҷ��� MainWindow�� 1��° �гη� �ٲ۴�.
					break;
			}// end of switch
		}// end of method(actioinPerformed)
	}
	
	/*
	 * MainWindow_AcquiredCredit
	 * MainWindow -> ��� ���� Ȯ���ϱ⸦ ���� �� �����Ǵ� ������
	 */
	class MainWindow_AcquiredCredit extends JFrame implements ActionListener {
		private JTable table;
		private DefaultTableModel model;
		// Table �� �� ����� ���� Ŭ���� ����
		
		MainWindow_AcquiredCredit(String title) {
			super(title);
			setLayout(new GridLayout(2,1));													// ��ü �г��� GridLayout(2�� 1��)�� ������.
			
			// ���⼭ ���Ǵ� newFont, newPanel ���..�� MainWindow�� Ŭ���� �����̴�.
			
			newFont = new Font("gulim", Font.PLAIN, 15);									// ��Ʈ�� ����, ũ�� 15, �ƹ����¾������� �Ѵ�.
			newPanel = new JPanel(new FlowLayout(1,10,10));									// FlowLayout���� ���� �г� ����
			newLabel = new JLabel("�б� �� ��� ����");										// Label�� �����Ѵ�.
			newLabel.setFont(newFont);														// Label�� �� ��Ʈ(newFont)�� �����Ѵ�.
			newPanel.add(newLabel);															// ���� �гο� Label�� �߰��Ѵ�.
			add(newPanel);																	// ��ü �гο� ���� �г� �߰�
			
			String colName[] = { "�б�", "����" , "����" , "�� ��� ����" };				// ���̺��� ��(Column)�� ���ϴ� �ٸ� �������ش�.
			
			model = new DefaultTableModel(colName, 0){										// Table ���� �����Ѵ�. ���� colName�̰� ���� ����.
				public boolean isCellEditable(int rowIndex,int mColIndex) {				
					return false;
					// Table�� ������ ����.
				}
			};
			
			table = new JTable(model);														// Table�� Table���� ������ �����Ѵ�.
			
			for(int i=0 ; i<6 ; i++) {
				// i�� year�� ���Ѵ�. 0(1�г� 1�б�), 1(1�г� 2�б�), 2(2�г� 1�б�), 3(2�г� 2�б�), 4(3�г� 1�б�), 5(3�г� 2�б�)
				addTableComponent(i);
				//	�б� ���� ���̺� �߰��Ѵ�
			}
			
			JScrollPane tableScroll = new JScrollPane(table);								// ScrollPane�� ���̺� �߰��Ѵ�.
			table.setPreferredScrollableViewportSize(new Dimension(320,95));				// ���̺��� ũ�⸦ 320, 95�� �����Ѵ�.
			table.getColumnModel().getColumn(0).setPreferredWidth(130);						// ���̺� 1��(�б�)�� ���� 130���� �����Ѵ�.
			table.getColumnModel().getColumn(1).setPreferredWidth(50);						// ���̺� 2��(����)�� ���� 50���� �����Ѵ�.
			table.getColumnModel().getColumn(2).setPreferredWidth(50);						// ���̺� 3��(����)�� ���� 50���� �����Ѵ�.
			table.getColumnModel().getColumn(3).setPreferredWidth(70);						// ���̺� 4��(�� ��� ����)�� ���� 70���� �����Ѵ�.
			table.getTableHeader().setReorderingAllowed(false);								// ��(Column)�� ���� ������ ���Ѵ�.
			table.getTableHeader().setResizingAllowed(false);								// ��(Column)�� �� ������ ���Ѵ�.
			newPanel.add(tableScroll);														// ���� �гο� ScrollPane�� �߰��Ѵ�.
			add(newPanel);																	// ��ü �гο� ���� �г��� �߰��Ѵ�.
			
			newFont = new Font("gulim", Font.PLAIN, 15);									// ��Ʈ�� ����, ũ�� 15, �ƹ����¾������� �Ѵ�.
			newPanel = new JPanel(new FlowLayout(1,10,10));									// FlowLayout���� ���� �г� ����
			newLabel = new JLabel("�� ��� ����");											// Label�� �����Ѵ�.
			newLabel.setFont(newFont);														// Label�� ��Ʈ�� �����Ѵ�.
			newPanel.add(newLabel);															// ���� �гο� Label�� �߰��Ѵ�.
			add(newPanel);																	// ��ü �гο� ���� �г��� �߰��Ѵ�.
			
			model = new DefaultTableModel(colName, 0){										// Table ���� �����Ѵ�. ��� ���� ���� ��������
				public boolean isCellEditable(int rowIndex,int mColIndex) {
					return false;
					// Table�� ������ ����.
				}
			};
			
			table = new JTable(model);														// Table�� Table���� ������ �����Ѵ�.
			
			addTableComponent(-1);
			// -1�� ��ü �б⸦ �ǹ��Ѵ�. ��ü �б��� ������ ����Ͽ� ���̺� �߰��Ѵ�.
			// �ڼ��� ������ addTableComponent�� ����
			
			tableScroll = new JScrollPane(table);											// ScrollPane�� ���̺� �߰��Ѵ�.
			table.setPreferredScrollableViewportSize(new Dimension(320,15));				// ���̺��� ũ�⸦ 320, 15�� �����Ѵ�.
			table.getColumnModel().getColumn(0).setPreferredWidth(130);						// ���̺� 1��(�б�)�� ���� 130���� �����Ѵ�.
			table.getColumnModel().getColumn(1).setPreferredWidth(50);						// ���̺� 2��(����)�� ���� 50���� �����Ѵ�.
			table.getColumnModel().getColumn(2).setPreferredWidth(50);						// ���̺� 3��(����)�� ���� 50���� �����Ѵ�.
			table.getColumnModel().getColumn(3).setPreferredWidth(70);						// ���̺� 4��(�� ��� ����)�� ���� 70���� �����Ѵ�.
			table.getTableHeader().setReorderingAllowed(false);								// ���� ���� ������ ���Ѵ�.
			table.getTableHeader().setResizingAllowed(false);								// ���� �� ������ ���Ѵ�.
			newPanel.add(tableScroll);														// ���� �гο� ScrollPane�� �߰�.
			
			newFont = new Font("gulim", Font.PLAIN, 15);									// ��Ʈ�� ����, ũ�� 15, �ƹ����� ����
			newLabel = new JLabel("���� �ּ� ���� : 90��      ���� �ּ� ���� : 12��");		// Label ����
			newLabel.setFont(newFont);														// Label�� ��Ʈ�� ����
			newPanel.add(newLabel);															// ���� �гο� Label �߰�
			newLabel = new JLabel("���� �ּ� ���� : 120����");								// Label ����
			newLabel.setFont(newFont);														// Label�� ��Ʈ�� ����
			newPanel.add(newLabel);															// ���� �гο� Label �߰�
			newButton = new JButton("���� �޴�");											// Button ����. ������ '���� �޴�'
			newButton.setPreferredSize(new Dimension(180,30));								// Button �� ũ�⸦ 180, 30���� ����
			newButton.addActionListener(this);												// Button �� ActionListener ����. �ڼ��� ������ actionPerformed ����
			newPanel.add(newButton);														// ���� �гο� Button �߰�
			add(newPanel);																	// ��ü �гο� ���� �г� �߰�
			
			this.setSize(350,400);															// ������ ũ�� 350, 400���� ����
			this.setResizable(false);														// ������ ũ�� ���� �Ұ�
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);							// ������ �� �� java�� ����
			this.setLocation(((MainProgram.SCREEN_WIDTH/2)-this.getWidth()/2),((MainProgram.SCREEN_HEIGHT/2)-this.getHeight()/2));
			// �����츦 �߾ӿ� ��ġ�ϰ� ��
			this.setVisible(true);															// ������ ���� ����
		}// end of Constructor
		
		public void actionPerformed(ActionEvent arg0) {
			// '���� �޴�' ��ư�� ������ MainWindow�� �����Ѵ�.
			new MainWindow("��� ���� ����");												// MainWindow ����
			dispose();																		// �� ������� ����
		}// end of Method(actionPerformed)
		
		void addTableComponent(int year) {
			// '��� ���� Ȯ���ϱ�'�� ���̺��� �߰����ִ� �޼ҵ�
			Subject tempSubject;															// Subject ������ �޾Ƴ��� �ӽ� ����
			int majorCredit = 0;															// ���� ����
			int culturalCredit = 0;															// ���� ����
			int totalCredit = 0;															// �� ��� ����
			String yearName = null;															// �б��� �̸��� �����ϴ� String ����
			
			if(year == 0) yearName = "1�г� 1�б�";											// year�� 0�� �� yearName�� 1�г� 1�б� 
			else if(year == 1) yearName = "1�г� 2�б�";									// year�� 1�� �� yearName�� 1�г� 2�б�
			else if(year == 2) yearName = "2�г� 1�б�";									// year�� 2�� �� yearName�� 2�г� 1�б�
			else if(year == 3) yearName = "2�г� 2�б�";									// year�� 3�� �� yearName�� 2�г� 2�б�
			else if(year == 4) yearName = "3�г� 1�б�";									// year�� 4�� �� yearName�� 3�г� 1�б�
			else if(year == 5) yearName = "3�г� 2�б�";									// year�� 5�� �� yearName�� 3�г� 2�б�
			else if(year == -1) yearName = "��ü �б�";										// year�� -1�� �� yearName�� ��ü �б�
			
			Iterator<Subject> Itr = SubjectList.getInstance().subjectArr.iterator();		// SubjectList�� subjectArr�� Iterator
			
			while(Itr.hasNext()) {															
				// subjectArr�� ���� element�� ���� �� true�� �ݺ��Ѵ�,������ ���� element�� ���� �� false�� �ݺ��� ��ģ��.
				tempSubject = Itr.next();													// tempSubject�� subjectArr�� ���� element�� �����´�.
				if(year == -1) {
					// year�� ���� -1(��ü �б� �� ��)
					if(tempSubject.getDivision() == 0 || tempSubject.getDivision() == 1 || tempSubject.getDivision() == 2 || tempSubject.getDivision() == 3) {
						// tempSubject�� division�� 0(���� ����), 1(��Ʈ��ũ ����), 2(���� ����), 3(Ÿ����) �� �� 
						majorCredit += tempSubject.getCredit();								// ���� ������ tempSubject�� ������ ���Ѵ�.
					}
					else if(tempSubject.getDivision() == 4) {
						// tempSubject�� division�� 4(����) �� ��
						culturalCredit += tempSubject.getCredit();							// ���� ������ tempSubject�� ������ ���Ѵ�.
					}
					totalCredit += tempSubject.getCredit();									
					// ����/���翡 ������� �� ���� ����� ���� tempSubject�� ������ ���Ѵ�.
				}
				else if(year == tempSubject.getYear()) {
					// year�� ��ü �бⰡ �ƴ� �� �б⸶�� ����� ���� ����� ���ϴ� �б�� tempSubject�� �бⰡ ������ �����Ѵ�.
					// �� year�� ���� 2�� ��� 2�г� 1�б��� ������ ����Ѵ�.
					if(tempSubject.getDivision() == 0 || tempSubject.getDivision() == 1 || tempSubject.getDivision() == 2 || tempSubject.getDivision() == 3) {
						// tempSubject�� division�� 0(���� ����), 1(��Ʈ��ũ ����), 2(���� ����), 3(Ÿ����) �� �� 
						majorCredit += tempSubject.getCredit();								// ���� ������ tempSubject�� ������ ���Ѵ�.
					}
					else if(tempSubject.getDivision() == 4) {
						// tempSubject�� division�� 4(����) �� ��
						culturalCredit += tempSubject.getCredit();							// ���� ������ tempSubject�� ������ ���Ѵ�.
					}
					totalCredit += tempSubject.getCredit();
					// ����/���翡 ������� �� ���� ����� ���� tempSubject�� ������ ���Ѵ�.
				}
			}// end of while
			
			String[] component = { yearName, String.valueOf(majorCredit), String.valueOf(culturalCredit), String.valueOf(totalCredit) };
			// ����� �������� ���ؼ� ���̺� �߰��� component�� �����Ѵ�. 1��(�б�), 2��(����), 3��(����), 4��(�� ��� ����)�̴�.
			// String �迭�̹Ƿ� ��� String���� �ٲ��ش�.
			model = (DefaultTableModel) table.getModel();			// model�� ����ϱ� ���� table�� ���� �޾ƿ´�.
			
			model.addRow(component);								// model(table)�� component�� �߰��Ѵ�.
		}// end of Method(addTableComponent)
	}// end of class(MainWindow_AcquiredCredit)
}// end of class(MainWindow)