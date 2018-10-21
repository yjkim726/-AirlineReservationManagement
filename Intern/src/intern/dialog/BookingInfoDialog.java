package intern.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sinsiway.petra.commons.sql.dto.ConsumerBookingDto;
import com.sinsiway.petra.commons.sql.dto.CoordinateDto;
import com.sinsiway.petra.commons.ui.util.DialogLocationUtill;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;
import com.sinsiway.petra.commons.util.DialogReturn;

public class BookingInfoDialog extends Dialog {
	private ConsumerBookingDto consumerBookingData;
	private Object returnData;
	private int[] array;
	
	private Text bookingText,conSumerText,departuerPlaceText,
	arrivePlaceText,seatTypeText,seatNumberText;

	public BookingInfoDialog(Shell parentShell, ConsumerBookingDto consumerBookingData, int array[]) {
		super(parentShell);
		this.consumerBookingData = consumerBookingData;
		this.array = array;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configureShell(Shell newShell){
		//Shell�� ���� title, size, 
		super.configureShell(newShell);
		
		newShell.setText("���� ���� Ȯ��");
		
		newShell.setSize(340, 450);
		
		DialogLocationUtill.MonitorCenter(newShell);
			//MonitorCenter�� ���� �� ����
		
	}
	
	@Override
	protected Control createDialogArea(Composite parent){
		//�⺻ �����̽�,������ ������� �ְڴ�. ȸ�翡�� ���� ������ �޼ҵ� ������
		PetraGridLayout.defaults().setDialog().applyTo(parent);
		PetraGridData.defaults().applyTo(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		PetraGridLayout.defaults().setDialog().applyTo(composite);
		PetraGridData.defaults().applyTo(composite);
		
		Group group = new Group(composite, SWT.NONE);
		//���� �߰��� ��   �̸�, ����, �޴���ȭ��ȣ�� ���� �� 	 �ɼ��� ���� �ʾƵ� �׵θ� ����
		PetraGridLayout.defaults().setDialog().columns(2).applyTo(group);
												//����   3 �̿���
		PetraGridData.defaults().grab(true, false).span(2, 1).applyTo(group);
		
		Label label = new Label(group, SWT.NONE);
		label.setText("�����ȣ ");
		
		bookingText = new Text(group, SWT.BORDER);
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).span(1, 1).applyTo(bookingText);
																		//�̸� : [                ] �̷��� �����
																		//span(2,1)�� �����ص׾���  2ĭ�� ��ƸԴ´ٴ� ��
																		//�ٷ� �� new lebel�� ������
	//	new Label(group, SWT.NONE);
		
		label = new Label(group, SWT.NONE);
		label.setText("����� ");
		
		conSumerText = new Text(group, SWT.BORDER);
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).span(1, 1).applyTo(conSumerText);
		
		
		label = new Label(group, SWT.NONE);
		label.setText("����� ");
		
		departuerPlaceText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(departuerPlaceText);
		
		label = new Label(group, SWT.NONE);
		label.setText("������ ");
		
		arrivePlaceText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(arrivePlaceText);
		
		label = new Label(group, SWT.NONE);
		label.setText("�¼� ���� ");
		
		seatTypeText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(seatTypeText);
		
		label = new Label(group, SWT.NONE);
		label.setText("�¼� ���� ");
		
		seatNumberText = new Text(group, SWT.BORDER);  
		PetraGridData.defaults().grab(false, false).hint(160, SWT.DEFAULT).applyTo(seatNumberText);
		
		//�ʱ�ȭ
		initialize();
		return parent;
	}
	
		private void initialize() {
			//���úκ�
			if((array != null) | (consumerBookingData!=null)){ //�����϶�
				
				bookingText.setText(String.valueOf(consumerBookingData.BOOKING_NUMBER)); //�����ȣ
				bookingText.setEditable(false);
				
				conSumerText.setText(String.valueOf(consumerBookingData.CONSUMER_ID));
				conSumerText.setEditable(false);
				
				System.out.print("��..!"+consumerBookingData.DEPARTURE_PLACE);
				
				if(consumerBookingData.DEPARTURE_PLACE==0)
					departuerPlaceText.setText("��õ");
				else if(consumerBookingData.DEPARTURE_PLACE==1)
					departuerPlaceText.setText("����");
				else if(consumerBookingData.DEPARTURE_PLACE==2)	
					departuerPlaceText.setText("�뱸");
				else if(consumerBookingData.DEPARTURE_PLACE==3)
					departuerPlaceText.setText("����");
				departuerPlaceText.setEditable(false);	
				
				
				if(consumerBookingData.ARRIVE_PLACE==0)
					arrivePlaceText.setText("����");
				else if(consumerBookingData.ARRIVE_PLACE==1)
					arrivePlaceText.setText("����¡");
				else if(consumerBookingData.ARRIVE_PLACE==2)	
					arrivePlaceText.setText("����");
				else if(consumerBookingData.ARRIVE_PLACE==3)
					arrivePlaceText.setText("�ĸ�");
				arrivePlaceText.setEditable(false);	
				
				if(consumerBookingData.SEAT_TYPE==0)
					seatTypeText.setText("�Ϲݼ�");
				else if(consumerBookingData.SEAT_TYPE==1)
					seatTypeText.setText("����Ͻ���");
				else if(consumerBookingData.SEAT_TYPE==2)	
					seatTypeText.setText("�ϵ");
				seatTypeText.setEditable(false);	
				
				seatNumberText.setText(String.valueOf(consumerBookingData.SEATNUMBER));
				seatNumberText.setEditable(false);
				
			}
		}
		public DialogReturn<Object> openWithResult() {
			int code = open();
						//open()
			return new DialogReturn<Object>(returnData, code);
		}			

}
