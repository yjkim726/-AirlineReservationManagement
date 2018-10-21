package calculator.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
/**
 * DBControl
 * DB�� ��Ʈ�� �ϱ����� Ŭ����
 */
public class DBControl {
	Connection conn = null;												// DB Connection�� ���� ����
	Statement stmt = null;												// Statement�� ����ϱ� ���� ����
	
	DBControl() {
	}
	
	void curriculumToDB() {
		// Curriculum �� curriculumArr�� �ִ� ������ DB�� ��������ִ� Method
		try {
			Subject tempSubject;
			try {
				Class.forName("com.mysql.jdbc.Driver");					// mysql driver�� ����
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquiredCreditDB","root","");
				// connection�� MySQL�� acquiredCreditDB�� ã�ƿ���
				// "root"�� ID, "" �� PASSWORD�̴�.
				System.out.println("�����ͺ��̽� ���� �Ϸ�");
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�."+ cnfe.getMessage());
			}
			catch (SQLException se) {
				System.out.println(se.getMessage());
			}// end of try
			
			stmt = conn.createStatement();								// stmt�� ����� DB�� Statement�� �����Ѵ�.
			
			Iterator<Subject> curriculumItr = Curriculum.getInstance().curriculumArr.iterator();
			// curriculumArr�� Iterator
			stmt.executeUpdate("delete from curriculum;");				// curriculum�̶�� table�� ������ �����Ѵ�.
			
			while(curriculumItr.hasNext()) {
				// curriculumArr�� ������ element���� ���� �ݺ��� �׸��Ѵ�.
				tempSubject = curriculumItr.next();						// tempSubject�� curriculumArr�� element�� �ִ´�.
				String[] subject = { String.valueOf(tempSubject.getNumber()),
						tempSubject.getName(),
						String.valueOf(tempSubject.getCredit()),
						String.valueOf(tempSubject.getDivision()),
						String.valueOf(tempSubject.getYear())
				};
				// db�� �߰��� String�� �迭�� �����Ѵ�.
				stmt.executeUpdate("insert curriculum (subjectNumber, subjectName, credit, division, year) values('"+
						subject[0] + "','" +
						subject[1] + "','" +
						subject[2] + "','" +
						subject[3] + "','" +
						subject[4] + "');");
				// curriculum ���̺� �߰��Ѵ�.
				System.out.println(tempSubject.getNumber() +  " " + tempSubject.getName()  + "��(��) �߰��Ǿ����ϴ�.");
			}
		}
		
		catch(SQLException se) {
			System.out.println(se.getMessage());
			// ���н�
		}
		finally {							// ����ó���� �ϵ� ���ϵ� ������ ó����
			try{
				stmt.close();				// stmt�� �ݴ´�.
			}
			catch(Exception ignored) {
				
			}// end of try
			try{
				conn.close();				// conn�� �ݴ´�.
			}
			catch(Exception ignored) {
				
			}// end of try
		}// end of finally
	}// end of try
	
	void dbToFile(){
		// DB�� �ִ� ������ File�� �Űܼ� �����ϴ� Method
		ArrayList<Subject> dbList = new ArrayList<Subject>();		// DB�� ���̺� �ִ� ������ �޾ƿ��� ArrayList. ���Ͽ� ����ȴ�.
		FileOutputStream fos = null;								// File�� �����ϱ� ���� Stream
		ObjectOutputStream oos = null;								// ��ü ������ �����ϱ� ���� Stream
		try {
			Subject tempSubject;
			try {
				Class.forName("com.mysql.jdbc.Driver");				// MySQL DB Driver�� �����Ѵ�.
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquiredCreditDB","root","");
				// localhost(�ڱ� �ڽ�)�� ��Ʈ 3306������ �� acquiredCreditDB�� �����Ѵ�.
				// "root"�� ID, ""�� PASSWORD�̴�.
				System.out.println("�����ͺ��̽� ���� �Ϸ�");
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�."+ cnfe.getMessage());
			}
			catch (SQLException se) {
				System.out.println(se.getMessage());
			}// end of try
			
			stmt = conn.createStatement();							// stmt�� ����� DB�� Statement�� �����Ѵ�.
			
			ResultSet rs = stmt.executeQuery("select subjectNumber, subjectName, credit, division, year from curriculum;");
			// curriculum ���̺� �ִ� ���� �� subjectNumber, subjectName, credit, division, year�� �о�´�.
			fos = new FileOutputStream("curriculum.dat");			// ArrayList�� ������ ������ �����Ѵ�. ������ curriculum.dat�̴�.
			oos = new ObjectOutputStream(fos);						// ��ü ������ ������ �� �ְԲ� �����Ѵ�.
			
			while(rs.next()) {
				// rs�� ���� �� ���� �ݺ��Ѵ�.
				tempSubject = new Subject(					// Subject�� ���� ����
						rs.getInt("subjectNumber"),			// int������ subjectNumber�� ������ �޾ƿ´�.
						rs.getString("subjectName"),		// Stirng������ subjectName�� ������ �޾ƿ´�.
						rs.getInt("credit"),				// int������ credit�� ������ �޾ƿ´�.
						rs.getInt("division"),				// int������ division�� ������ �޾ƿ´�.
						rs.getInt("year")					// int������ year�� ������ �޾ƿ´�.
						);
				
				dbList.add(tempSubject);					// ���� ������ tempSubject�� ������ ArrayList�� �߰�.
			}// end of while
			
			try {
				oos.writeObject(dbList);					// dbList�� curriculum.dat�� �����Ѵ�.
			}
			catch(Exception e){
				e.printStackTrace();
			}// end of try
		}
		catch(SQLException | IOException se) {
			System.out.println(se.getMessage());
		}
		finally {											// ����ó���� �ϵ� ���ϵ� ������ ������
			try{
				fos.close();								// fos �� �ݴ´�.
				oos.close();								// oos �� �ݴ´�.
			}
			catch(Exception ignored) {
				
			}// end of try
			try{
				stmt.close();								// stmt �� �ݴ´�.
			}
			catch(Exception ignored) {
				
			}// end of try
			try{
				conn.close();								// conn �� �ݴ´�.
			}
			catch(Exception ignored) {
				
			}// end of try
		}// end of finally
	}// end of Method(dbToFile)
}//end of class(DBControl)

class DBControlWindow extends JFrame implements ActionListener {
	// DB Connection�� �������� �� �����Ǵ� ������
	private JPanel newPanel;
	private JLabel newLabel;
	private JButton newButton;
	private Font newFont;
	DBControl db = new DBControl();
	
	DBControlWindow(String title) {
		super(title);
		JPanel tempPanel;
		setLayout(new GridLayout(3,1));

		
		newFont = new Font("gulim", Font.PLAIN, 20);
		newPanel = new JPanel(new GridLayout(2,1));
		tempPanel = new JPanel(new FlowLayout(1,10,10));
		newLabel = new JLabel("DB Control Menu");
		newLabel.setFont(newFont);
		tempPanel.add(newLabel);
		newPanel.add(tempPanel);
		newFont = new Font("gulim", Font.PLAIN, 11);
		tempPanel = new JPanel(new FlowLayout(1,10,10));
		newLabel = new JLabel("DB �� Connection�� �������� �� �����Ǵ� ������");
		newLabel.setFont(newFont);
		tempPanel.add(newLabel);
		newPanel.add(tempPanel);
		add(newPanel);
		
		newPanel = new JPanel(new FlowLayout(1,10,10));
		newButton = new JButton("Curriculum To DB");
		newButton.setPreferredSize(new Dimension(150,30));
		newButton.addActionListener(this);
		newPanel.add(newButton);
		add(newPanel);
		
		newPanel = new JPanel(new FlowLayout(1,10,10));
		newButton = new JButton("DB To File");
		newButton.setPreferredSize(new Dimension(150,30));
		newButton.addActionListener(this);
		newPanel.add(newButton);
		add(newPanel);
		
		this.setSize(300,350);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "Curriculum To DB" :						// ��ư�� ������ Curriculum To DB �� ���
			db.curriculumToDB();
			// db Ŭ����(DBControl)�� curriculumToDB Method�� ȣ���Ѵ�.
			break;
		case "DB To File" :								// ��ư�� ������ DB To File �� ���
			db.dbToFile();
			// db Ŭ����(DBControl)�� dbToFile Method�� ȣ���Ѵ�.
			break;
		}
		
	}
}