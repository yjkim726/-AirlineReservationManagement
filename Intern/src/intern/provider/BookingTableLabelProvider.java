package intern.provider;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.sinsiway.petra.commons.sql.dto.BookingDto;
import com.sinsiway.petra.commons.sql.dto.ConsumerBookingDto;
import com.sinsiway.petra.commons.sql.dto.SearchDto;
import com.sinsiway.petra.commons.ui.util.PetraFont;

public class BookingTableLabelProvider extends LabelProvider implements ITableLabelProvider,ITableFontProvider,ITableColorProvider{
	private Font font = null;
	Color color = new Color(null,0, 0, 0);
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
//			case 0:
//				return InternUIResources.getImage("sample.gif");
			/*case 2:
				return InternUIResources.getImage("sample1.gif");
			case 3:
				return InternUIResources.getImage("sample1.gif");*/
			default:	
				return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		// String[] bookingListColumnHeader = {"�����ȣ","�װ�������","�����","������","�����","�¼����","üũ�ο���"};�߿��ؿ�!
		ConsumerBookingDto data = (ConsumerBookingDto) element;
		String SEAT_TYPES,DEPARTURE_PLACE,ARRIVE_PLACE;
		switch (columnIndex) {
		case 0: //�����ȣ
			return String.valueOf(data.BOOKING_NUMBER);
		case 1: //�װ����̸�
			return	data.FLIGHT_NAME;
		case 2: //�����
			if(data.DEPARTURE_PLACE==0){
				DEPARTURE_PLACE="��õ";
				return DEPARTURE_PLACE;
			}else if(data.DEPARTURE_PLACE==1){
				DEPARTURE_PLACE="����";
				return DEPARTURE_PLACE;
			}else if(data.DEPARTURE_PLACE==2){
				DEPARTURE_PLACE="�뱸";
				return DEPARTURE_PLACE;
			}else if(data.DEPARTURE_PLACE==3){
				DEPARTURE_PLACE="����";
				return DEPARTURE_PLACE;
			}
			//return String.valueOf(data.DEPARTURE_PLACE);
		case 3: //������
			if(data.ARRIVE_PLACE==0){
				ARRIVE_PLACE="����";
				return ARRIVE_PLACE;
			}else if(data.ARRIVE_PLACE==1){
				ARRIVE_PLACE="����¡";
				return ARRIVE_PLACE;
			}else if(data.ARRIVE_PLACE==2){
				ARRIVE_PLACE="����";
				return ARRIVE_PLACE;
			}else if(data.ARRIVE_PLACE==3){
				ARRIVE_PLACE="�ĸ�";
				return ARRIVE_PLACE;
			}
			//return String.valueOf(data.ARRIVE_PLACE);
		case 4: //�����
			return String.valueOf(data.DEPARTURE_DATE);
		case 5: //�¼����
			if(data.SEAT_TYPE==0){
				SEAT_TYPES="�Ϲݼ�";
				return SEAT_TYPES;
			}else if(data.SEAT_TYPE==1){
				SEAT_TYPES="����Ͻ���";
				return SEAT_TYPES;
			}else if(data.SEAT_TYPE==2){
				SEAT_TYPES="�ϵ";
				return SEAT_TYPES;
			}
		case 6: //üũ�ο���
			if(data.CHECKIN==0){
				return "O";
			}else if(data.CHECKIN==1){
				return "X";
			}
		default:
			return "������";
		}
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		//PetraColor.RED_80;
		//���̹��� ġ�� �� ���ɴϴ�.
		getForeground();
		return color;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
//		case 2:
//			return PetraFont.S8_B_FONT;
		/*case 4:
			return PetraFont.S8_B_FONT;*/
		default:	
			return font;
		}
	}
	
	public Font getFont(){
		return font;
	}
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public Color getForeground(){
		return color;
	}
	
	public void setForeground(Color color){
		this.color = color;
	}
}