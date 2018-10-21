package calculator.main;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * SubjectWindow
 * ���� ���� �߰��� ���ִ� �����츦 �����ϴ� Ŭ����
 */
public class SubjectWindow extends JFrame implements ActionListener {
	private JPanel newPanel;
	private JLabel newLabel;
	private JComboBox newComboBox;
	private JButton newButton;
	private JTable table;
	private DefaultTableModel model;
	private int division;
	// �����츦 �����ϴ� ��. Ŭ���� ������ ����
	
	SubjectWindow(String title, int division) {
		//division�� ���� �߰��Ǵ� ������� ������ �޶���
		super(title);
		this.division = division;
		
		getContentPane().setLayout(new BorderLayout());								// ��ü�� BorderLayout���� ����
		
		newPanel = new JPanel(new FlowLayout(0,10,10));								// ���� �г��� FlowLayout���� ����
		newLabel = new JLabel("�б� ���� : ");										// Label ����
		newPanel.add(newLabel);														// ���� �гο� Label �߰�
		String[] comboBoxItem = { "1�г� 1�б�", "1�г� 2�б�", "2�г� 1�б�", "2�г� 2�б�", "3�г� 1�б�","3�г� 2�б�" };
		// comboBox�� Item
		newComboBox = new JComboBox(comboBoxItem);									// ComboBox�� ����
		newPanel.add(newComboBox);													// ���� �гο� ComboBox�� �߰�
		newButton = new JButton("Ȯ��");											// Button ����. ������ 'Ȯ��'
		newButton.addActionListener(this);											// Button�� ActionListener �߰�. �ڼ��� ������ actionPerformed Ȯ��
		newPanel.add(newButton);													// ���� �гο� Button �߰�
		add(newPanel,"North");														// ���� �г��� North(��)���ٰ� �߰�
		
		newPanel = new JPanel(new FlowLayout(1,10,10));								// ���� �г��� FlowLayout ���� ����
		newLabel = new JLabel("���� ���� ����");									// Label ����
		newPanel.add(newLabel);														// ���� �гο� Label �߰�
		String[] subjectColName = { "���� ��ȣ", "���� �̸�", "����" , "����" };	// Table�� ��(Column) ����
		
		model = new DefaultTableModel(subjectColName, 0){							// Table Model ����. ���� subjectColName�̰� ���� ����.
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};// Table ���� �Ұ�
		table = new JTable(model);													// Model�� �����ϰ� Table �߰�
		
		
		JScrollPane tableScroll = new JScrollPane(table);							// Table�� ScrollPane �߰�
		table.setPreferredScrollableViewportSize(new Dimension(300,180));			// Table�� ũ�⸦ 300, 180���� ����
		table.getColumnModel().getColumn(0).setPreferredWidth(80);					// 1��(���� ��ȣ)�� ���� 80���� ����
		table.getColumnModel().getColumn(1).setPreferredWidth(120);					// 2��(���� �̸�)�� ���� 120���� ����
		table.getColumnModel().getColumn(2).setPreferredWidth(50);					// 3��(����)�� ���� 50���� ����
		table.getColumnModel().getColumn(3).setPreferredWidth(50);					// 4��(����)�� ���� 50���� ����
		table.getTableHeader().setReorderingAllowed(false);							// �� ���� ���� �Ұ�
		table.getTableHeader().setResizingAllowed(false);							// �� ũ�� ���� �Ұ�
		newPanel.add(tableScroll);													// ���� �гο� Table �߰�
		add(newPanel,"Center");														// ���� �г��� Center(�߾�)�� �߰�
		
		newPanel = new JPanel(new FlowLayout(1,10,10));								// ���� �г��� FlowLayout ���� ����
		newButton = new JButton("����");											// Button ����. ������ '����'
		newButton.addActionListener(this);											// Button�� ActionListener �߰�
		newPanel.add(newButton);													// ���� �гο� Button �߰�
		add(newPanel,"South");														// ���� �г��� South(��)�� �߰�
		
		this.setSize(350,400);														// ������ ũ�⸦ 350, 400���� ����
		this.setVisible(true);														// ������ ���� ����
		this.setResizable(false);													// ������ ũ�� ���� �Ұ�
		
	}// end of Constructor
	
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		// arg0.getActionCommand()�� ��ư�� ���� �� ��ư�� ������ �����´�.
			case "Ȯ��" :															// ��ư�� ������ 'Ȯ��'�� ���
				tableChange(newComboBox.getSelectedIndex());
				// tableChange �޼ҵ带 ȣ���Ѵ�. 
				// �׸��� ComoboBox�� Item�� index�� �޾ƿͼ� �Ķ���ͷ� �Ѱ��ش�.
				// index�� 0(1�г� 1�б�), 1(1�г� 2�б�), 2(2�г� 1�б�), 3(2�г� 2�б�), 4(3�г� 1�б�), 5(3�г� 2�б�)�̴�.
				break;
			case "����" :															// ��ư�� ������ '����'�� ���
				courseRegister();													// coursRegister �޼ҵ带 ȣ���Ѵ�.
				break;
		}
	}
	
	void tableChange(int index) {
		// table�� �ٲ��ִ� Method
		Subject tempSubject, tempCurriculum;										
		// tempSubject�� SubjectList�� �߰��� �����̸� tempCurriculum�� Curriculum�� ������ �޾ƿ��� �����̴�.
		boolean addCheck = true;													
		// SubjectList�� ������ addCheck�� false�� �Ǿ ���̺� �߰����� �ʴ´�.
		
		model = (DefaultTableModel) table.getModel();						// table�� Model�� �޾ƿ�
		model.setNumRows(0);												// �𵨿� �ִ� ��(row)�� 0���� ����������. -> ��� ���� ����
			
		Iterator<Subject> curriculumItr = Curriculum.getInstance().curriculumArr.iterator();
		// Curriculum�� ArrayList�� curriculumArr�� iterator�� ������ 
		
		if(division == 3) {
			// division �� 3�� ���(Ÿ������ ���)
			while(curriculumItr.hasNext()) {								
				// curriculum�� ���� element�� ������ true���� �ݺ��ϰ� ���� element�� ������ false���� �ݺ��� ������.
				tempCurriculum = curriculumItr.next();			// tempCurriculum�� curriculum�� ���� element�� �޾ƿ�.
				if(tempCurriculum.getDivision() == 3) {			// tempCurriculum�� division�� 3�� ���
				String[] addTable = { 							// Table�� �߰��� �迭
						String.valueOf(tempCurriculum.getNumber()), // tempCurriculum�� subjectNumber�� �޾ƿ� int���̹Ƿ� Stirng������ ��ȯ
						tempCurriculum.getName(),					// tempCurriculum�� subjectName�� �޾ƿ�
						tempCurriculum.getDivisionName(),			// tempCurriculum�� divisionName�� �޾ƿ�
						String.valueOf(tempCurriculum.getCredit())	// tempCurriculum�� credit�� �޾ƿ� int���̹Ƿ� String������ ��ȯ
						};
				tempCurriculum.setYear(index);					// -1�� Ÿ������ year�� �޾ƿ� index�� �ٲ���
				model.addRow(addTable);							// table�� �߰���.
				}
			}// end of while(curriculumItr)
		}
		else {
			// division �� Ÿ���� �ܿ� �ٸ����ϰ��
			while(curriculumItr.hasNext()) {
				// curriculum�� ���� element�� ������ true�� �ݺ��ϰ� ���� element�� ������ false�� �ݺ��� ������.
				tempCurriculum = curriculumItr.next();			// tempCurriculum�� curriculum�� ���� element�� �޾ƿ�.
				Iterator<Subject> subjectItr = SubjectList.getInstance().subjectArr.iterator();
				// SubjectList�� ArrayList�� subjectArr�� iterator�� ������ 
				
				while(subjectItr.hasNext()) {
					//subjectArr�� ���� element�� ������ true���� �ݺ��ϰ� ���� element�� ������ false���� �ݺ��� ������.
					// �� �ݺ��� �̹� ��û�Ǿ� �ִ� subject�� ���̻� �߰��� �ȵǰ� �ϱ� ���ؼ��̴�.
					tempSubject = subjectItr.next();		// tempSubject�� subjectArr�� ���� element�� �޾ƿ�
					if(tempCurriculum.getNumber() == tempSubject.getNumber()) {
						// tempCurriculum�� subjectNumber�� tempSubject�� subjectNumber�� ���� ���
						addCheck = false;
						// �̹� subjectArr�� �ִ� subject�̹Ƿ� ���̺� �߰����� �ʴ´�.
						break;
					}
					else {
						addCheck = true;
						// ���� ������� true�� �Ѵ�.
					}
				}// end of while(subjectItr)
				
				if(addCheck == true) {
					// ������ addCheck�� true�� ��� 
					if(division == 4) {
						// division�� 4�� ���(������ ���)
						if(tempCurriculum.getDivision() == 4) {		// tempCurriculum�� division�� 4(����)�� ���
							String[] addTable = { String.valueOf(tempCurriculum.getNumber()),		//���̺� ���� ��(row) ������
									// tempCurriculum�� subjectNumber�� �޾ƿ� int���̹Ƿ� String������ ����ȯ
									tempCurriculum.getName(),				// tempCurriculum�� subjectName�� �޾ƿ�
									tempCurriculum.getDivisionName(),		// tempCurriculum�� divisionName�� �޾ƿ�
									String.valueOf(tempCurriculum.getCredit())	// tempCurriculum�� credit�� �޾ƿ� int���̹Ƿ� String������ ����ȯ
									};
							tempCurriculum.setYear(index); 					// Ÿ������ year�� -1�̹Ƿ� year�� index�� �ٲپ��ش�.
							model.addRow(addTable);							// ���̺� �߰��Ѵ�.
						}
					}
					else if(division == tempCurriculum.getDivision() || tempCurriculum.getDivision() == 0) {
						// division�� ������ �ƴϰ� tempCurriclum�� division�� ���� ��쳪, tempCurriculum�� division�� 0(���� ����)�� ��� 
						if(index == tempCurriculum.getYear()){				// index(������ �޾ƿ� 
							String[] addTable = { String.valueOf(tempCurriculum.getNumber()),
									tempCurriculum.getName(),
									tempCurriculum.getDivisionName(),
									String.valueOf(tempCurriculum.getCredit())
									};
							// addTable�� ���� �Ȱ���.
							model.addRow(addTable);							// ���̺� �߰��Ѵ�.
						}
					}
				}// end of if(addcheck)
			} // end of while(curriculum)	
		}// end of while(curriculum)
	}// end of Method(tableChange)	
	
	void courseRegister() {
		// ������ ������ SubjectList�� subject�� �߰����ִ� Method 
		Subject tempSubject;
		int row = table.getSelectedRow();		// Ŭ���� ���� ��(row)�� ��ȣ�� �޾ƿ´�.
		int tempSubjectNumber;
		
		if(row == -1) {							// getSelectedRow�� -1�� return�ϴ� ���� �������� �ʾ��� ����̴�.
			return;
			// �������� �ʾ��� ��� Method�� ������.
		}
		
		model = (DefaultTableModel) table.getModel();	// table�� model�� ���´�.
		
		tempSubjectNumber = Integer.parseInt(String.valueOf(model.getValueAt(row,0)));
		// getValueAt�� ���ؼ� ������ row�� 0��° column ��, subjectNumber�� �޾ƿ´�.
		// ������ getValueAt�� Object������ ��ȯ�ǹǷ� String������ �ٲپ��ٰ� Integer.parseInt�� �ٽ� int������ �ٲپ���.
		
		Iterator<Subject> curriculumItr = Curriculum.getInstance().curriculumArr.iterator();
		// Curriculum�� curriculumArr �� iterator
		
		while(curriculumItr.hasNext()) {
			// curriculumItr�� ������ element���� ���� false�� ���ö� ���� �ݺ�
			tempSubject = curriculumItr.next();				// tempSubject�� ���� curriculum�� element�� �޾ƿ�
			if(tempSubjectNumber == tempSubject.getNumber()) {
				// geteValueAt���� �޾ƿ� subjectNumber�� tempSubject�� ���� ���
				SubjectList.getInstance().addSubject(tempSubject);
				// SubjectList�� subjectArr�� tempSubject�� �߰��Ѵ�.
				model.removeRow(row);
				// ���̺��� row���� �����Ѵ�.
			}
		}
	}
}

/**
 * PrintSubjectListWindow
 * ���� ���� �߰��� ������ �����ִ� ������� �����ִ� Ŭ����
 */
class PrintSubjectListWindow extends JFrame implements ActionListener {
	private JPanel newPanel;
	private JLabel newLabel;
	private JComboBox newComboBox;
	private JButton newButton;
	private JTable table;
	private DefaultTableModel model;
	// ������ ������ Ŭ���� ����
	
	PrintSubjectListWindow(String title) {
		super(title);
		getContentPane().setLayout(new BorderLayout());				// �� ������ ��ü�� BorderLayout���� ����
		
		newPanel = new JPanel(new FlowLayout(0,10,10));				// ���� �г� ���� FlowLayout
		newLabel = new JLabel("�б� ���� : ");						// Label ����	
		newPanel.add(newLabel);										// ���� �гο� Label �߰�
		String[] comboBoxItem = { "1�г� 1�б�", "1�г� 2�б�", "2�г� 1�б�", "2�г� 2�б�", "3�г� 1�б�","3�г� 2�б�" };
		// ComboBox�� item �迭
		newComboBox = new JComboBox(comboBoxItem);					// ComboBox Item�� �̿��Ͽ� comboBox ����
		newPanel.add(newComboBox);									// ���� �гο� ComboBox �߰�
		newButton = new JButton("Ȯ��");							// Button ����. ������ 'Ȯ��'
		newButton.addActionListener(this);							// Button�� ActionListener �߰�
		newPanel.add(newButton);									// ���� �гο� Button �߰�
		add(newPanel,"North");										// ��ü �гο� ���� �г��� North�� �߰�
		
		newPanel = new JPanel(new FlowLayout(1,10,10));				// ���� �г� ���� FlowLayout
		newLabel = new JLabel("���� ����");							// Label ����
		newPanel.add(newLabel);										// ���� �гο� Label �߰�
		String[] subjectColName = { "���� ��ȣ", "���� �̸�", "����" , "����" };
		// table�� column�� �����̴�.
		
		model = new DefaultTableModel(subjectColName, 0){			// subjectColName�� ��(Column)���� ���� ���� Table model ����
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};// Table ���� �Ұ���
		
		table = new JTable(model);											// model�� ���� table����
		
		
		JScrollPane tableScroll = new JScrollPane(table);					// table�� ScrollPane �߰�
		table.setPreferredScrollableViewportSize(new Dimension(300,180));	// table�� ũ�⸦ 300, 180���� ����
		table.getColumnModel().getColumn(0).setPreferredWidth(80);			// 1��(���� ��ȣ)�� ���� 80���� ����
		table.getColumnModel().getColumn(1).setPreferredWidth(120);			// 2��(���� �̸�)�� ���� 120���� ����
		table.getColumnModel().getColumn(2).setPreferredWidth(50);			// 3��(����)�� ���� 50���� ����
		table.getColumnModel().getColumn(3).setPreferredWidth(50);			// 4��(����)�� ���� 50���� ����
		table.getTableHeader().setReorderingAllowed(false);					// ��(Column)�� ���� �̵� �Ұ�
		table.getTableHeader().setResizingAllowed(false);					// ���� ũ�� ���� �Ұ�
		newPanel.add(tableScroll);											// ���� �гο� table �߰�
		add(newPanel,"Center");												// ��ü �г� Center�� ���� �г��� �߰�
		
		newPanel = new JPanel(new FlowLayout(1,10,10));						// ���� �г� FlowLayout���� ����
		newButton = new JButton("���� ���");								// Button ����. ������ '���� ���'
		newButton.addActionListener(this);									// ActionListener �߰�
		newPanel.add(newButton);											// Button �߰�
		add(newPanel,"South");												// ��ü �г� South�� ���� �г��� �߰�
		
		this.setSize(350,400);												// ������ ũ�⸦ 350, 400���� ��
		this.setVisible(true);												// ������ ���� ����
		this.setResizable(false);											// ������ ũ�� ���� �Ұ�
	}
	
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		// arg0.getActionCommand()�� ��ư�� ���� �� ��ư�� ������ �����´�.
		case "Ȯ��" :														// ��ư�� ������ 'Ȯ��'�� ���
			tableChange(newComboBox.getSelectedIndex());
			// tableChange �޼ҵ带 ȣ���Ѵ�. 
			// �׸��� ComoboBox�� Item�� index�� �޾ƿͼ� �Ķ���ͷ� �Ѱ��ش�.
			// index�� 0(1�г� 1�б�), 1(1�г� 2�б�), 2(2�г� 1�б�), 3(2�г� 2�б�), 4(3�г� 1�б�), 5(3�г� 2�б�)�̴�.
			break;
		case "���� ���" :													// ��ư�� ������ '���� ���'�� ���
			cancelCourse();
			// cancelCourse �޼ҵ带 ȣ���Ѵ�.
			break;
		}
	}
	
	void tableChange(int index) {
		// �б⺰ index�� �޾� ������ ���� ����� �߰����ִ� �޼ҵ�
		Subject tempSubject;									// subjectArr�� subject�� �޴� �ӽ� ����
		Iterator<Subject> subjectItr = SubjectList.getInstance().subjectArr.iterator();
		// SubjectList�� subejctArr Iterator
		model = (DefaultTableModel) table.getModel();			// table model�� ���´�.
		model.setNumRows(0);									// table�� row�� ������ 0���� ����� -> table�� ����.
		
		while(subjectItr.hasNext()) {
			// subjectItr�� ���������� Ȯ���� ��� hasNext()�� false�� �Ǿ� �ݺ��� ������.
			tempSubject = subjectItr.next();					// tempSubject�� subjectArr�� ������ �ִ´�.
			
			if(index == tempSubject.getYear() || index == -1) {
				// index�� ���� �б�� tempSubject�� year�� ������� , Ȥ�� index�� -1�ΰ��(���� �� Ÿ����)
				String[] addTable = {
						String.valueOf(tempSubject.getNumber()),
						tempSubject.getName(),
						tempSubject.getDivisionName(),
						String.valueOf(tempSubject.getCredit())
				};
				model.addRow(addTable);
				// ���� tempSubject�� �����͸� ���� table�� �߰��Ѵ�.
			}
		}
	}
	
	void cancelCourse() {
		// ���� ��Ҹ� ������ �����ִ� Method
		Subject tempSubject;
		int row = table.getSelectedRow();				// Ŭ���� ���� row�� �޾ƿ´�.
		int tempSubjectNumber;
		
		if(row == -1) {
			// row�� -1�� ���� Ŭ���� ���� ���°��̴�. Ŭ���� ���� ������ Method�� �����Ѵ�.
			return;
		}
		
		model = (DefaultTableModel) table.getModel();	// table�� ���� �޾ƿ´�.
		
		tempSubjectNumber = Integer.parseInt(String.valueOf(model.getValueAt(row,0)));
		// ����
		
		Iterator<Subject> subjectItr = SubjectList.getInstance().subjectArr.iterator();
		// getValueAt�� ���ؼ� ������ row�� 0��° column ��, subjectNumber�� �޾ƿ´�.
		// ������ getValueAt�� Object������ ��ȯ�ǹǷ� String������ �ٲپ��ٰ� Integer.parseInt�� �ٽ� int������ �ٲپ���.
		
		while(subjectItr.hasNext()) {
			// subjectItr�� ������ ���� �ݺ�
			tempSubject = subjectItr.next();			// tempSubject�� subjectArr�� element�� �ִ´�.
			
			if(tempSubjectNumber == tempSubject.getNumber()) {
				// getValueAt �� subjectNumber�� tempSubject�� subjectNumber�� �������
				SubjectList.getInstance().delSubject(tempSubject);
				// subjectArr���� tempSubject�� �����Ѵ�.
				model.removeRow(row);
				// table���� row�� �����Ѵ�.
				break;
			}
		}
	}
}
