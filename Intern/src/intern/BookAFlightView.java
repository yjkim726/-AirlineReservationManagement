package intern;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import intern.dialog.BookingInfoDialog;
import intern.dialog.CalendarDialog;
import intern.dialog.InternDialog;
import intern.provider.BookingTableLabelProvider;
import intern.provider.SearchTableLabelProvider;
import intern.util.FrequentlyUsedMethodsUtil;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.mihalis.opal.brushedMetalComposite.BrushedMetalComposite;

import com.sinsiway.petra.commons.log.PetraLogger;
import com.sinsiway.petra.commons.sql.dto.BookingDto;
import com.sinsiway.petra.commons.sql.dto.ConsumerBookingDto;
import com.sinsiway.petra.commons.sql.dto.ConsumerDto;
import com.sinsiway.petra.commons.sql.dto.CoordinateDto;
import com.sinsiway.petra.commons.sql.dto.DepartureDto;
import com.sinsiway.petra.commons.sql.dto.FlightDto;
import com.sinsiway.petra.commons.sql.dto.InternDto;
import com.sinsiway.petra.commons.sql.dto.PlaneDto;
import com.sinsiway.petra.commons.sql.dto.SearchDto;
import com.sinsiway.petra.commons.sql.util.IBatisAdapter;
import com.sinsiway.petra.commons.ui.control.TableFilterComposite;
import com.sinsiway.petra.commons.ui.util.PetraFont;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;
import com.sinsiway.petra.commons.ui.util.TableUtil;
import com.sinsiway.petra.commons.util.DialogReturn;

public class BookAFlightView extends ViewPart{

	public static final String ID = "Intern.bookAFlightView";
	private Label separatorLabel;
	private Label vacuumLabel;
	private Logger logger = PetraLogger.getLogger(View.class);
	private LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
	//private Text messageText;
	private IPreferenceStore iPreferenceStore = PlatformUI.getPreferenceStore();
	private Button oneWay,roundTrip, generalSeat, businessClass, firstClass ;
	private StackLayout stackLayout, placeStackLayout, checkInStackLayout;  
	//ȭ��󿡴� �ΰ��� �׿��ִµ�, � â�� �������� ���ϴ� ��
	private Composite stackComposite, stackComposite1, stackComposite2;
	private Composite placeStackComposite, airlineTicketBook,bookingListCheckIn,allCheckList,placeStackComposite2;
	//���߿� � �̺�Ʈ�� �߻����� ��, ������𺸴� ��ü������ ���ؼ�
	private DateTime departureDate, arriveDate;
	private SashForm placeSashForm, checkInSashForm, allCheckSashForm, bookingListSashForm, checkInSeatConfirmationSashForm,  reservationConfirmationSashForm;
	private Combo departureCombo , destinationCombo;
	private Integer dataValidation = 0; //���� �ʱ�ȭ
	private Integer seatType = 0;
	private Calendar departureCalendar,arriveCalendar;
	private Spinner adultSpinner, youngChildSpinner, kidSpinner;
	private ConsumerDto modifyData ;
	private String departureStringDate, arriveStringDate;
	private Date date = new Date();
	private SimpleDateFormat smpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 
	//////////////////////////////////SearchView
	private TableViewer searchTableViewer, bookingTableViewer, allBookingTableViewer;
	private Table searchTable,bookingTable,allBookingTable;
	//�װ�
	private FlightDto flightDto1 = new FlightDto();
	private FlightDto flightDto2 = new FlightDto();
	private PlaneDto planeDto1 = new PlaneDto();
	private PlaneDto planeDto2 = new PlaneDto();
	private DepartureDto departureDto1 = new DepartureDto();
	private DepartureDto departureDto2 = new DepartureDto();
	/*private List<FlightDto> flightList = new ArrayList<FlightDto>();
	private List<PlaneDto> planeList = new ArrayList<PlaneDto>();
	private List<DepartureDto> departureList = new ArrayList<DepartureDto>();*/
	private List<SearchDto> searchList;
	private ConsumerDto data = new ConsumerDto();
	private Integer bookingListTableIndex,allBookingListTableIndex, searchPlaneTableIndex;
	private ConsumerBookingDto consumerBookingDto = new ConsumerBookingDto(); 
	private ConsumerBookingDto consumerBookingData = new  ConsumerBookingDto(); 
	
	/////������ üũ��
	private List <ConsumerBookingDto> bookingList, allBookingList;
	private CLabel clbel;
	
	//private Composite seatConfirmationGroupComposite2;
	private int[] count = new int[66]; 
	private int allCount= 1 ;
	private int cancle = 0;
	private int again = 0;
	
	//������/üũ�� > ���̺�Ŭ����> ������ __________ �����ȣ____�ߴ� �� 
	private Label insertCheckInLabel2,insertCheckInLabel4,
	//��üȮ�ν� �ڿ� �������� ��
	insertReservationConfirmationLabel2,insertReservationConfirmationLabel4,
	insertReservationConfirmationLabel6,insertReservationConfirmationLabel8,
	insertReservationConfirmationLabel10,insertReservationConfirmationLabel12,
	insertReservationConfirmationLabel14,insertReservationConfirmationLabel16;
	
	 List<CLabel> listLabel = new ArrayList<CLabel>();
	 List<CLabel> selectLabel = new ArrayList<CLabel>();
	
	private Font font = null;
	private Color color = new Color(null,0, 0, 0);
	BookingTableLabelProvider bookingTableLabelProvider = new BookingTableLabelProvider();  
	
	
	//NavigationView ���� ���õǰ��մϴ�.
	NavigationView navigationView = (NavigationView) FrequentlyUsedMethodsUtil.getView(NavigationView.ID);
	
	private List<CoordinateDto> selectedCoordinateList;
	private Label informationLabel;
	
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
				PetraGridLayout.defaults().applyTo(parent);
				PetraGridData.defaults().applyTo(parent);
				
				///�װ��ǿ��� stackLayout
				placeStackLayout = new StackLayout();
				placeStackComposite = new Composite(parent, SWT.BORDER);
				placeStackComposite.setLayout(placeStackLayout);
				PetraGridData.defaults().applyTo(placeStackComposite);

				///////////////���ó�����
				//airlineTicketBook
				//�װ��ǿ���
				airlineTicketBook = new Composite(placeStackComposite, SWT.None);
				PetraGridLayout.defaults().margin(0,0).spacing(0, 5).columns(4).applyTo(airlineTicketBook);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(airlineTicketBook);
				
				//bookingListCheckIn
				//������&üũ��
				bookingListCheckIn = new Composite(placeStackComposite, SWT.None);
				PetraGridLayout.defaults().margin(0,0).spacing(0, 5).columns(4).applyTo(bookingListCheckIn);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(bookingListCheckIn);
				
				//��üȮ��
				//allCheckList
				allCheckList = new Composite(placeStackComposite, SWT.None);
				PetraGridLayout.defaults().margin(0,0).spacing(0, 5).columns(4).applyTo(allCheckList);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(allCheckList);
				
				/////////////���� ���ÿ��� SashForm ���
				//����� ���Žý��� SashForm
				placeSashForm = new SashForm(airlineTicketBook, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(placeSashForm);
				PetraGridData.defaults().applyTo(placeSashForm);
				placeSashForm.setSashWidth(0);
				
				
				/*///����� ������ �޺��� ���� ��� �ߴ� �̹��� sashForm
				departureArriveComboSashForm = new SashForm(placeSashForm, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(departureArriveComboSashForm);
				PetraGridData.defaults().applyTo(departureArriveComboSashForm);
				departureArriveComboSashForm.setSashWidth(0);*/
				
				//����Ȯ��&üũ�� SashForm
				checkInSashForm = new SashForm(bookingListCheckIn, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(checkInSashForm);
				PetraGridData.defaults().applyTo(checkInSashForm);
				checkInSashForm.setSashWidth(0);
				
				
				//��üȮ��
				allCheckSashForm = new SashForm(allCheckList, SWT.HORIZONTAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(allCheckSashForm);
				PetraGridData.defaults().applyTo(allCheckSashForm);
				allCheckSashForm.setSashWidth(0);
				
				
				//////////////////////
				//airlineTicketBook ���� topComposite�� ��
				final Composite topComposite = new Composite(placeSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 20).applyTo(topComposite);
				PetraGridData.defaults().grab(true, false).applyTo(topComposite);
				
				///����� ������ �޺� ���ý� �̹���
			/*	final Composite comboImageComposite = new Composite(topComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).applyTo(comboImageComposite);
				PetraGridData.defaults().grab(true, true).hint(20, 30).applyTo(comboImageComposite);
				
				final Composite comboImageUpComposite = new Composite(comboImageComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).applyTo(comboImageUpComposite);
				PetraGridData.defaults().grab(true, false).hint(100, 500).applyTo(comboImageUpComposite);
				
				final Composite comboImageUnderComposite = new Composite(comboImageComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).applyTo(comboImageUnderComposite);
				PetraGridData.defaults().grab(true, false).hint(100, 100).applyTo(comboImageUnderComposite);*/
				
				final Composite groupComposite = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(30,10).spacing(0, 10).columns(4).applyTo(groupComposite);
				PetraGridData.defaults().grab(false, false).applyTo(groupComposite);			
				
				oneWay = new Button(groupComposite, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(65, 20).applyTo(oneWay);
				oneWay.setText("��");
				//oneWay.setSelection(true); 
				
				vacuumLabel  = new Label(groupComposite, SWT.None);
				/*
				//�����ڽ�_�׸�_��
				ToolBar oneWaytoolBar = new ToolBar (groupComposite, SWT.NONE);
				//���̺� �ٷ� ���� + ���� X
				//���ٸ� ����ϰ� �Ǹ� ���콺�� ������ ��� �ڿ������� ȿ���� �ش�.
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(oneWaytoolBar);
				PetraGridData.defaults().grab(false, false).applyTo(oneWaytoolBar);
										//���⼭ �ִ�� �ؾ��� <��ü > �̺κ��� ũ�� �������� �������� �̻ڰ� ���´�.
				final ToolItem oneWaycalendarToolItem = new ToolItem(oneWaytoolBar, SWT.NONE);
													//�θ� ���ٷ� �д�.
				//oneWaycalendarToolItem.setToolTipText("��");
				oneWaycalendarToolItem.setImage(InternUIResources.getImage("oneWay.png")); */
				
				
				roundTrip = new Button(groupComposite, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(65, 20).applyTo(roundTrip);
				roundTrip.setText("�պ�");
				
				oneWay.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						oneWay.setFont(PetraFont.S9_B_FONT);
						roundTrip.setFont(PetraFont.S9_FONT);
						
						dataValidation = 0; //���� ������ ���
					
						stackLayout.topControl = stackComposite1;
						stackComposite.layout();
						
					}});
				
				roundTrip.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						roundTrip.setFont(PetraFont.S9_B_FONT);
						oneWay.setFont(PetraFont.S9_FONT);
						
						dataValidation = 1; //�պ��� ������ ���
						
						stackLayout.topControl = stackComposite2;
						stackComposite.layout();
					}});
			
				
				vacuumLabel  = new Label(groupComposite, SWT.None);
				/*
				//�����ڽ�_�׸�_��
				ToolBar roundTriptoolBar = new ToolBar (groupComposite, SWT.NONE);
				//���̺� �ٷ� ���� + ���� X
				//���ٸ� ����ϰ� �Ǹ� ���콺�� ������ ��� �ڿ������� ȿ���� �ش�.
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(roundTriptoolBar);
				PetraGridData.defaults().grab(false, false).applyTo(roundTriptoolBar);
										//���⼭ �ִ�� �ؾ��� <��ü > �̺κ��� ũ�� �������� �������� �̻ڰ� ���´�.
				final ToolItem roundTripcalendarToolItem = new ToolItem(roundTriptoolBar, SWT.NONE);
													//�θ� ���ٷ� �д�.
				//roundTripcalendarToolItem.setToolTipText("�պ�");
				roundTripcalendarToolItem.setImage(InternUIResources.getImage("roundTrip.png"));
				
				*/
				
				final Composite groupComposite2 = new Composite(topComposite, SWT.NONE);
				//PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(6).applyTo(groupComposite2);
				PetraGridLayout.defaults().margin(30,10).spacing(0, 10).columns(4).applyTo(groupComposite2);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite2);
				
				Label departureLabel = new Label(groupComposite2, SWT.NONE);
				departureLabel.setText("����� ");
				departureLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(departureLabel);
					
				
				//�����
				departureCombo = new Combo(groupComposite2, SWT.READ_ONLY);
				departureCombo.setText("list");
				String[] departureBundle = {"��õ", "����","�뱸","����"};
				departureCombo.setItems(departureBundle);
				//PetraGridData.defaults().grab(false, false).hint(60, 10).applyTo(departureCombo);
				PetraGridData.defaults().grab(false, false).hint(100, 10).applyTo(departureCombo);
				departureCombo.select(0);
				departureCombo.addSelectionListener(new SelectionAdapter(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				
				//������
				Label destinationLabel = new Label(groupComposite2, SWT.NONE);
				destinationLabel.setText("������ ");
				destinationLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(destinationLabel);		
				
				destinationCombo = new Combo(groupComposite2, SWT.READ_ONLY);
				String[] destinationBundle = {"����", "����¡","����","�ĸ�"};
				destinationCombo.setItems(destinationBundle);
				destinationCombo.select(0);
				//PetraGridData.defaults().grab(false, false).hint(60, 10).applyTo(destinationCombo);
				PetraGridData.defaults().grab(false, false).hint(100, 10).applyTo(destinationCombo);
				
				
				destinationCombo.addSelectionListener(new SelectionAdapter(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				////���Ӱ� �߰� ����
				final Composite groupComposite2_1 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite2_1);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite2_1);
				
				////////////////���	
				stackLayout = new StackLayout();
				stackComposite = new Composite(groupComposite2_1, SWT.None);
				stackComposite.setLayout(stackLayout);
				
				//stackComposite1
				stackComposite1 = new Composite(stackComposite, SWT.None);
				PetraGridLayout.defaults().margin(20,10).spacing(0, 10).columns(4).applyTo(stackComposite1);
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(stackComposite1);
				
				///�߰�
				
				Label departureDateLabel = new Label(stackComposite1, SWT.NONE);
				departureDateLabel.setText("����� ");
				departureDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(departureDateLabel);
				
				
				departureCalendar = Calendar.getInstance();
				departureCalendar.add(Calendar.DATE, 0);
				departureDate = new DateTime(stackComposite1, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130,30).applyTo(departureDate);
				departureDate.setDate(departureCalendar.get(Calendar.YEAR),0,1);
				departureStringDate = smpleDateFormat.format(date);  //���糯¥���
				departureDate.addSelectionListener(new SelectionAdapter(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
						String allDate = e.widget.toString();
						int startCut = allDate.indexOf("{");
						int endCut = allDate.indexOf("}");
						
						
						String dateCutting = allDate.substring(startCut+1, endCut);
						String arrayCut[] = dateCutting.split("/");
						
						if(departureStringDate!=null){
							departureStringDate = arrayCut[2]+"-"+arrayCut[0]+"-"+arrayCut[1];
							//departureStringDate = departureDate.getYear()+ (departureDate.getMonth()+1) + departureDate.getDay() 
						}else{
							
						}
					
					}
				});
				
				
				Label arriveDateLabel = new Label(stackComposite1, SWT.NONE);
				arriveDateLabel.setText("������ ");
				arriveDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(arriveDateLabel);
				
				arriveCalendar = Calendar.getInstance();
				arriveCalendar.add(Calendar.DATE, 0);
				arriveDate = new DateTime(stackComposite1, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130, 30).applyTo(arriveDate);
				arriveDate.setDate(arriveCalendar.get(Calendar.YEAR),0,1);
				arriveDate.setEnabled(false);
				
				
				//stackComposite2
				stackComposite2 = new Composite(stackComposite, SWT.None);
				PetraGridLayout.defaults().margin(20,10).spacing(0, 10).columns(4).applyTo(stackComposite2);
												//0,0              5
				PetraGridData.defaults().grab(true,false).hint(40, 10).applyTo(stackComposite2);
				
				
				
				//��ư_�׸�_�պ�
				departureDateLabel = new Label(stackComposite2, SWT.NONE);
				departureDateLabel.setText("����� ");
				departureDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(departureDateLabel);
				
				departureCalendar = Calendar.getInstance();
				departureCalendar.add(Calendar.DATE, 0);
				departureDate = new DateTime(stackComposite2, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN | SWT.READ_ONLY);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130, 30).applyTo(departureDate);
				departureDate.setDate(departureCalendar.get(Calendar.YEAR),0,1);		
				departureStringDate = smpleDateFormat.format(date);  //���糯¥��� //���糯¥���
				departureDate.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						String allDate = e.widget.toString();	//DateTime {��/��/�⵵} ��� ���
						int startCut = allDate.indexOf("{"); 
						int endCut = allDate.indexOf("}");  // ��/��/�⵵ �� ©���
						String dateCutting = allDate.substring(startCut+1, endCut);
						String arrayCut[] = dateCutting.split("/");
						if(departureStringDate!=null){
							departureStringDate = arrayCut[2]+"-"+arrayCut[0]+"-"+arrayCut[1];
						}else{
							
						}
					}
				});
				
				//������ �޷�/Ķ����
				arriveDateLabel = new Label(stackComposite2, SWT.NONE);
				arriveDateLabel.setText("������ ");
				arriveDateLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(55, 10).applyTo(arriveDateLabel);
				
				arriveCalendar = Calendar.getInstance();
				arriveCalendar.add(Calendar.DATE, 0);
				arriveDate = new DateTime(stackComposite2, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN | SWT.READ_ONLY);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).hint(130, 30).applyTo(arriveDate);
				arriveDate.setDate(arriveCalendar.get(Calendar.YEAR),0,1);
				arriveStringDate =smpleDateFormat.format(date);  //���糯¥��� //���糯¥���
				arriveDate.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						String allDate = e.widget.toString();	//DateTime {��/��/�⵵} ��� ���
						int startCut = allDate.indexOf("{"); 
						int endCut = allDate.indexOf("}");  // ��/��/�⵵ �� ©���
						String dateCutting = allDate.substring(startCut+1, endCut);
						String arrayCut[] = dateCutting.split("/");
						if(arriveStringDate!=null){
							arriveStringDate = arrayCut[2]+"-"+arrayCut[0]+"-"+arrayCut[1];
						}else{
							
						}
					}
				});

				
				
				// �Ǽ�
				final Composite groupComposite3 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite3);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite3);
				
				separatorLabel = new Label(groupComposite3, SWT.SEPARATOR | SWT.HORIZONTAL);
				PetraGridData.defaults().grab(true, false).applyTo(separatorLabel);		
				
				
				//�Ϲݼ� ����Ͻ� ��.. ���
				final Composite groupComposite4 = new Composite(topComposite, SWT.NONE);
												//10,10
				PetraGridLayout.defaults().margin(30,10).spacing(0, 0).columns(4).applyTo(groupComposite4);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite4);
				
				Label flightClass = new Label(groupComposite4, SWT.NONE);
				flightClass.setText("�¼� ��� ");
				flightClass.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(100, 30).applyTo(flightClass);
				
				generalSeat = new Button(groupComposite4, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(90, 30).applyTo(generalSeat);
				generalSeat.setText("�Ϲݼ�");
				generalSeat.setFont(PetraFont.S8_FONT);
				//oneWay.setSelection(true); 
				
				generalSeat.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						//�Ϲݼ� ������ �޾ƿ��� ��
						seatType = 0; 
						generalSeat.setFont(PetraFont.S8_B_FONT);
						businessClass.setFont(PetraFont.S8_FONT);
						firstClass.setFont(PetraFont.S8_FONT);
					}});
				
				businessClass = new Button(groupComposite4, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(130, 30).applyTo(businessClass);
				businessClass.setText("����Ͻ���");
				businessClass.setFont(PetraFont.S8_FONT);
				businessClass.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						//����Ͻ��� ������ �޾ƿ��� ��
						seatType = 1; 
						generalSeat.setFont(PetraFont.S8_FONT);
						businessClass.setFont(PetraFont.S8_B_FONT);
						firstClass.setFont(PetraFont.S8_FONT);
					}});
				
				firstClass = new Button(groupComposite4, SWT.RADIO);
				PetraGridData.defaults().grab(false, false).hint(100, 30).applyTo(firstClass);
				firstClass.setText("�ϵ");
				firstClass.setFont(PetraFont.S8_FONT);
				firstClass.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e){
						//�ϵ ������ �޾ƿ��� ��
						seatType = 2;
						generalSeat.setFont(PetraFont.S8_FONT);
						businessClass.setFont(PetraFont.S8_FONT);
						firstClass.setFont(PetraFont.S8_B_FONT);
					}});
				
				// �Ǽ�
				final Composite groupComposite5 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite5);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite5);
				
				separatorLabel = new Label(groupComposite5, SWT.SEPARATOR | SWT.HORIZONTAL);
				PetraGridData.defaults().grab(true, false).applyTo(separatorLabel);		
				
				
				//���� �Ҿ� ���
				
				final Composite groupComposite6 = new Composite(topComposite, SWT.None);
				PetraGridLayout.defaults().margin(20,0).spacing(0, 20).columns(3).applyTo(groupComposite6);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite6);
				
				///////����
				final Composite adultComposite = new Composite(groupComposite6, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(2).applyTo(adultComposite);
				
				Label adultLabel = new Label(adultComposite, SWT.NONE);
				adultLabel.setText("����");
				adultLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).applyTo(adultLabel);	
				
				//���� �ΰ� ����
				adultLabel = new Label(adultComposite, SWT.NONE);
				adultLabel.setText("��12���̻�");
				adultLabel.setFont(PetraFont.S8_FONT);
				PetraGridData.defaults().grab(false, true)./*hint(85, 20).*/applyTo(adultLabel);
				
				
				/////////�Ҿ�
				final Composite youngChildComposite = new Composite(groupComposite6, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(3).applyTo(youngChildComposite);
				
				Label youngChildLabel = new Label(youngChildComposite, SWT.NONE);
				youngChildLabel.setText("�Ҿ�");
				youngChildLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).applyTo(youngChildLabel);	
				
				//�Ҿ� �ΰ� ����
				youngChildLabel = new Label(youngChildComposite, SWT.NONE);
				youngChildLabel.setText("��12���̸�");
				youngChildLabel.setFont(PetraFont.S8_FONT);
				PetraGridData.defaults().grab(false, true).hint(85, 20).applyTo(youngChildLabel);
				
				//�Ҿ� ���� �׸�
				youngChildLabel = new Label(youngChildComposite, SWT.NONE);
				youngChildLabel.setImage(InternUIResources.getImage("information.png"));
				final ToolTip youngChildTip = new ToolTip(getSite().getShell(), SWT.BALLOON);
				youngChildTip.setMessage("�°������� ������� ���� �������� ����˴ϴ�.\n"+
		        		"�� 12�� �̸�(�������� �� 13��)�� �Ҿ� �°��� �� 18�� �̻��� ��ȣ�ڿ� ���� Ŭ������\n"+
		        		"���� ž���Ͽ��� �ϸ�, �Ұ� �� �񵿹� �Ҿ� ���񽺸� ��û�ؾ� �մϴ�.\n"+
		        		"�ڼ��� ������ FAQ �Ǵ� ���񽺼��ͷ� �����Ͽ� �ֽñ� �ٶ��ϴ�.");
		        youngChildTip.setAutoHide(true);
		        youngChildLabel.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						youngChildTip.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						youngChildTip.setVisible(false);
					}
				});
//				ToolBar youngChildtoolBar = new ToolBar (youngChildComposite, SWT.NONE);
//				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).applyTo(youngChildtoolBar);											
//				PetraGridData.defaults().grab(true, false).hint(10, 10).applyTo(youngChildtoolBar);
//				final ToolItem youngChildToolItem = new ToolItem(youngChildtoolBar, SWT.NONE);
//				youngChildToolItem.setToolTipText("�� ������ ǥ�� �� ����");
//				youngChildToolItem.setImage(InternUIResources.getImage("information.png"));
				
				///////���
				final Composite kidComposite = new Composite(groupComposite6, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(3).applyTo(kidComposite);
			
				Label kidLabel = new Label(kidComposite, SWT.NONE);
				kidLabel.setText("���");
				kidLabel.setFont(PetraFont.S9_B_FONT);
				PetraGridData.defaults().grab(false, false).applyTo(kidLabel);	
				
				//��� �ΰ� ����
				kidLabel = new Label(kidComposite, SWT.NONE);
				kidLabel.setText("24�����̸�");
				kidLabel.setFont(PetraFont.S8_FONT);
				PetraGridData.defaults().grab(false, true).hint(85, 20).applyTo(kidLabel);
				
				//��� ���� �׸�
				kidLabel = new Label(kidComposite, SWT.NONE);
				kidLabel.setImage(InternUIResources.getImage("information2.png"));
				final ToolTip kidTip = new ToolTip(getSite().getShell(), SWT.BALLOON);
				kidTip.setMessage("�°������� ������� ���� �������� ����˴ϴ�.\n"+
		        		"���ƴ� �ݵ�� ���ΰ� �Բ� �����ϼž� �մϴ�. �� 2�� �̸� ���� �°��� �¼��� �������� �ʴ�"+
		        		"��� ���� ������� ���ͳ� ���� �� ���Ű� �����մϴ�.\n"+
		        		"���� �°��� �¼� ������ ���Ͻô� ��� ���� ���͸� ���Ͽ� �Ҿƿ������ ���� �� �����Ͻñ� �ٶ��ϴ�.");
				kidTip.setAutoHide(true);
				kidLabel.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						kidTip.setVisible(true);
					}
					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						kidTip.setVisible(false);
					}
				});
		        
		        //���� �Ҿ� ��� ���ǳ�
		        
		        final Composite groupComposite7 = new Composite(topComposite, SWT.None);
				PetraGridLayout.defaults().margin(50,0).spacing(0, 100).columns(3).applyTo(groupComposite7);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite7);
				
				//���� ���ǳ�
				adultSpinner = new Spinner(groupComposite7, SWT.BORDER | SWT.READ_ONLY);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 40).applyTo(adultSpinner);
				PetraGridData.defaults().grab(false, false).hint(40,25).applyTo(adultSpinner);
				adultSpinner.setMinimum(1);
				adultSpinner.setMaximum(9);
				adultSpinner.setSelection(1);
				adultSpinner.setIncrement(1);
				adultSpinner.setPageIncrement(1);
		        
				//�Ҿ� ���ǳ�
				youngChildSpinner = new Spinner(groupComposite7, SWT.BORDER | SWT.READ_ONLY);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 40).applyTo(youngChildSpinner);
				PetraGridData.defaults().grab(false, false).hint(40,25).applyTo(youngChildSpinner);
				youngChildSpinner.setMinimum(0);
				youngChildSpinner.setMaximum(9);
				youngChildSpinner.setSelection(0);
				youngChildSpinner.setIncrement(1);
				youngChildSpinner.setPageIncrement(1);
				
			
				//���� ���ǳ�
				kidSpinner = new Spinner(groupComposite7, SWT.BORDER | SWT.READ_ONLY);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 40).applyTo(kidSpinner);
				PetraGridData.defaults().grab(false, false).hint(40,25).applyTo(kidSpinner);
				kidSpinner.setMinimum(0);
				kidSpinner.setMaximum(9);
				kidSpinner.setSelection(0);
				kidSpinner.setIncrement(1);
				kidSpinner.setPageIncrement(1);
				
				// �Ǽ�
				final Composite groupComposite8 = new Composite(topComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(10,10).spacing(0, 0).columns(1).applyTo(groupComposite8);
				PetraGridData.defaults().grab(true, false).applyTo(groupComposite8);
				
				separatorLabel = new Label(groupComposite8, SWT.SEPARATOR | SWT.HORIZONTAL);
				PetraGridData.defaults().grab(true, false).applyTo(separatorLabel);		
		        
				
				//�װ��� ��ȸ �� Ȯ��
			
				final Composite groupComposite9 = new Composite(topComposite, SWT.None);
				PetraGridLayout.defaults().margin(200,0).spacing(0, 100).columns(1).applyTo(groupComposite9);
				PetraGridData.defaults().grab(false, false).applyTo(groupComposite9);			
					
			
				ToolBar aviationWayCheckToolBar = new ToolBar (groupComposite9, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 100).applyTo(aviationWayCheckToolBar);
				PetraGridData.defaults().grab(false, false).applyTo(aviationWayCheckToolBar);
				final ToolItem aviationWayCheckToolItem = new ToolItem(aviationWayCheckToolBar, SWT.NONE);
													
				aviationWayCheckToolItem.setImage(InternUIResources.getImage("aviationWayCheck.png"));
			
				aviationWayCheckToolItem.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//��ȸ�� ������ �����Ͱ� �Ѿ�� ó��
						
						//�װ��� ��ȸ�� ���� ���
						if(!dataValidation()){
							//������� �ִ���
							return ;	
							//������ �̺κ��� �Ѿ�� ��
						}
							//CONSUMER_ID�� �ϴ� ����� ����̹Ƿ� Default�� ���� (Long)
							data.CONSUMER_ID = (long) 2859693;
							data.CONSUMER_NUMBER = (long) IBatisAdapter.queryForObject("intern_getSeq");
							data.AIRLINETICKETTYPE = dataValidation; //�� �պ� ���� ������
							data.DEPARTURE_PLACE = departureCombo.getSelectionIndex();	//�����
							data.ARRIVE_PLACE = destinationCombo.getSelectionIndex();	//������
							data.DEPARTURE_DATE = departureStringDate;
							data.ARRIVE_DATE = arriveStringDate;
							if(data.ARRIVE_DATE==null){
								data.ARRIVE_DATE=  "0000-00-00";
							}
							data.SEAT_TYPE = seatType;
							data.SEATNUMBER = kidSpinner.getSelection() + adultSpinner.getSelection() + youngChildSpinner.getSelection();
							data.CHECKIN = 1;  //1�� false
			
							//trim�� ��������
//							if(manBtn.getSelection()){
//								data.SEX = 0;
//							}else{
//								data.SEX = 1;
//							}
							if(modifyData == null){
								//���� �˻��� ���
								//����� �˻�
								//data.CONSUMER_ID = (long) IBatisAdapter.queryForObject("intern_getSeq");
													//Consumer ���ٰ� �ֱ�
								getSearchDto(data);
								setConsumerDtoDATA(data);
								
							}else{
								//���� �����Ͱ� �ִ� ���
						/*		if(type){
									data.INTERN_ID = (long) IBatisAdapter.queryForObject("intern_getSeq");
																//������
									IBatisAdapter.execute("intern_insertIntern",data);
								}else{
									//������ ���̵� ���
									data.INTERN_ID = modifyData.INTERN_ID;
									IBatisAdapter.execute("intern_updateIntern",data);
								
								}*/
							}
							
						
						placeSashForm.setWeights(new int[]{0,1}); //�װ� ���̺��� ������
						bookingListSashForm.setWeights(new int[]{1,0}); //�װ� ���̺��� ������
						
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
								
				//////////////////////////////////SearchView
				//����� ���Žý��� ����
				
				//SashForm ����
				bookingListSashForm = new SashForm(placeSashForm, SWT.VERTICAL);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(bookingListSashForm);
				PetraGridData.defaults().applyTo(bookingListSashForm);
				bookingListSashForm.setSashWidth(0);
				
				
				
				
				//SashForm ���� �Ѱ�
				Composite tableComposite = new Composite(bookingListSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(40, 40).spacing(10, 0).applyTo(tableComposite);
				PetraGridData.defaults().applyTo(tableComposite);
				
				Composite tableFilterComposite = new Composite(tableComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(315, 0).spacing(0, 0).applyTo(tableFilterComposite);
				PetraGridData.defaults().grab(true,false).hint(30,35).applyTo(tableFilterComposite);
				
				Composite tableUnderComposite = new Composite(tableComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(tableUnderComposite);
				PetraGridData.defaults().applyTo(tableUnderComposite);
				
				String[] columnHeader = {"�װ�","�¼�Ÿ��","��� �ð�","���� �ð�","���� �¼�"};
				//int[] columnWidth = {70,70,60,70,20};
				int[] columnWidth = {150,150,150,150,150};
				final TableFilterComposite filterComposite = new TableFilterComposite(tableFilterComposite, columnHeader, false, SWT.RIGHT);
				PetraGridData.defaults().alignment(SWT.FILL, SWT.CENTER).grab(false, false).applyTo(filterComposite);
				searchTableViewer = new TableViewer(tableUnderComposite, SWT.FULL_SELECTION | SWT.MULTI );
				searchTable = searchTableViewer.getTable();	
				
				PetraGridLayout.defaults().applyTo(searchTable);
				PetraGridData.defaults().span(2, 1).applyTo(searchTable);
										//�̷��� �س����� ���̺��� ���ٿ� ��������. ��� span(3,1)�� �ؾ���
				TableUtil.makeTableColumns(searchTable, columnHeader, columnWidth);
				// util  ������Ʈ �� ���� �����ϸ��
				
				searchTable.setHeaderVisible(true);
				//false �� ��� : �̸�,���� ��� ����� 
				searchTable.setLinesVisible(true);
					//false �� ��� : ���� ������ �����
				
				searchTableViewer.setContentProvider(new ArrayContentProvider());
				searchTableViewer.setLabelProvider(new SearchTableLabelProvider());
				
				searchTable.addFocusListener(new FocusListener(){
					@Override
					public void focusGained(FocusEvent e) {
						// TODO Auto-generated method stub
						bookingListSashForm.setWeights(new int[]{9,1}); //�װ� ���̺��� ������
					}

					@Override
					public void focusLost(FocusEvent e) {
						// TODO Auto-generated method stub
						bookingListSashForm.setWeights(new int[]{1,0}); //�װ� ���̺��� ������
					}
					
				});
				
				//1. ����⿹�Žý��� -> �װ��� ��ȸ -> ���̺� ����Ŭ���� ����Ǵ� â
				Composite tableBookingListComposite = new Composite(bookingListSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(200, 0).spacing(10, 60).columns(2).applyTo(tableBookingListComposite);
				PetraGridData.defaults().applyTo(tableBookingListComposite);
				

				ToolBar reSearchToolBar = new ToolBar (tableBookingListComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 100).applyTo(reSearchToolBar);
				PetraGridData.defaults().grab(false, false).applyTo(reSearchToolBar);
				final ToolItem reSearchToolItem = new ToolItem(reSearchToolBar, SWT.NONE);
													
				reSearchToolItem.setImage(InternUIResources.getImage("reSearch.png"));
																//��˻�
				reSearchToolItem.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						placeSashForm.setWeights(new int[]{1,0}); //�װ� ���̺��� ������
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
			
				ToolBar requestToolBar = new ToolBar (tableBookingListComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 100).applyTo(requestToolBar);
				//PetraGridData.defaults().grab(false,false).hint(100,40).applyTo(button2);
				PetraGridData.defaults().grab(false, false).applyTo(requestToolBar);
				final ToolItem requestToolItem = new ToolItem(requestToolBar, SWT.NONE);
													
				requestToolItem.setImage(InternUIResources.getImage("reQuest.png"));
																//��û
				requestToolItem.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//NavigationView ���� ���õǰ��մϴ�.
						navigationView.getTable1().select(1);
						
						//searchTable���� selection
						searchPlaneTableIndex = searchTable.getSelectionIndex();
						placeStackLayout.topControl = bookingListCheckIn;
						placeStackComposite.layout(); 
						checkInSashForm.setWeights(new int[]{1,0});
						getRequest(data,searchPlaneTableIndex);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
		
				///������ üũ��  
				//placeStackComposite 2������ üũ��  Ŭ���� ������ ���� ȭ��
				//��
				Composite bookingListComposite = new Composite(checkInSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(30, 30).spacing(10, 10).applyTo(bookingListComposite);
				PetraGridData.defaults().applyTo(bookingListComposite);
				
				Composite bookingListComposite1 = new Composite(bookingListComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).columns(2).applyTo(bookingListComposite1);
				PetraGridData.defaults().applyTo(bookingListComposite1);
				String[] bookingListColumnHeader = {"�����ȣ","�װ�������","�����","������","�����","�¼����","üũ�ο���"};
				int[] bookingListColumnWidth = {90,105,70,70,100,90,105};
				//int[] bookingListColumnWidth = {90,150,150,150,150,150,150};
				bookingTableViewer = new TableViewer(bookingListComposite1, SWT.FULL_SELECTION | SWT.MULTI);
				bookingTable = bookingTableViewer.getTable();	
				
				PetraGridLayout.defaults().applyTo(bookingTable);
				PetraGridData.defaults().span(2, 1).applyTo(bookingTable);
										//�̷��� �س����� ���̺��� ���ٿ� ��������. ��� span(3,1)�� �ؾ���
				TableUtil.makeTableColumns(bookingTable, bookingListColumnHeader, bookingListColumnWidth);
				
				bookingTable.setHeaderVisible(true); 
				bookingTable.setLinesVisible(true);
					//false �� ��� : ���� ������ �����
				bookingTableViewer.setContentProvider(new ArrayContentProvider());
				bookingTableViewer.setLabelProvider(bookingTableLabelProvider);
				//bookingTableViewer.setLabelProvider(new BookingTableLabelProvider());
				
				bookingTableViewer.setInput(bookingList);
				bookingTableViewer.refresh();

				bookingTable.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						String[] arrive= {"����", "����¡","����","�ĸ�"}; 
						bookingListTableIndex = bookingTable.getSelectionIndex();
						consumerBookingData = bookingList.get(bookingListTableIndex); 
						
						//������ ����
						setConsumerBookingData(consumerBookingData);
						
						//üũ�ο� �Է� ������/�����ȣ �� �Է�
						for(int i=0; i<arrive.length; i++){
							if(consumerBookingData.ARRIVE_PLACE==i){
								insertCheckInLabel2.setText(arrive[i]);
							}
						}
						
						insertCheckInLabel4.setText(Long.toString(consumerBookingData.BOOKING_NUMBER));
						//consumerBookingData.BOOKING_NUMBER
						//��  (long) 2859693
						
						if(consumerBookingData.CHECKIN==1){
							//üũ���� ���� ���� ���
							checkInSashForm.setWeights(new int[]{0,1}); //�װ� ���̺��� ������
							checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
						}else if(consumerBookingData.CHECKIN==0){
							//üũ���� �� ���
							MessageDialog.openWarning(null, "üũ�οϷ�", "�̹� üũ���� �Ǿ����ϴ�");
						}
					}//end of mouseDoubleClick 
				});
				
				//placeStackComposite 2������ üũ��  Ŭ���� ������ ���� ȭ��
				//SashForm ����
				checkInSeatConfirmationSashForm = new SashForm(checkInSashForm, SWT.HORIZONTAL); 
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(checkInSeatConfirmationSashForm);
				PetraGridData.defaults().applyTo(checkInSeatConfirmationSashForm);
				checkInSeatConfirmationSashForm.setSashWidth(0);
				
				final Composite checkInTopComposite = new Composite(checkInSeatConfirmationSashForm, SWT.NONE );
				PetraGridLayout.defaults().margin(0, 0).applyTo(checkInTopComposite);
				PetraGridData.defaults().grab(false, true).hint(780,40).applyTo(checkInTopComposite);
				
				final Composite checkInGroupComposite1 = new Composite(checkInTopComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(20,0).spacing(0, 0).applyTo(checkInGroupComposite1);
				PetraGridData.defaults().grab(false, false).hint(780,260).applyTo(checkInGroupComposite1);	
				
				final Group checkInGroup = new Group(checkInGroupComposite1,  SWT.NONE );
				PetraGridLayout.defaults().margin(70,0).spacing(0, 0).applyTo(checkInGroup);
				PetraGridData.defaults().grab(false, true).hint(640,260).applyTo(checkInGroup);	
															//
				
				Label explanationCheckInLabel1 = new Label(checkInGroup, SWT.NONE| SWT.CENTER);
				explanationCheckInLabel1.setText("���� ���� �� CHECK-IN ����� �Ұ����մϴ�");
				explanationCheckInLabel1.setFont(PetraFont.S13_B_FONT);
				
				Label explanationCheckInLabel2 = new Label(checkInGroup, SWT.NONE| SWT.CENTER);
				explanationCheckInLabel2.setText(
				"�� �ӽ��� ����\n"+
				"�� ���Ƶ��� ��\n"+
				"�� �ŵ��� ������ ��, Ư���� ������ �ʿ�� �ϴ� ��\n"+
				"�� 16�� �̸����� ȥ�� �����ϴ� ��\n"+
				"�� ġ�ᰡ �ʿ��ϰų� ȯ���� ��\n"+
				"�� �� ���� �̿� ��\n"
						);
				explanationCheckInLabel2.setFont(PetraFont.S10_FONT);
				
				
				final Composite checkInGroupComposite2 = new Composite(checkInTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(40,40).spacing(10, 5).columns(3).applyTo(checkInGroupComposite2);
				PetraGridData.defaults().grab(false, false).applyTo(checkInGroupComposite2);	
				
				//üũ�� - ARRIVEǥ��
				Label insertCheckInLabel1 = new Label(checkInGroupComposite2, SWT.NONE);
				insertCheckInLabel1.setText("���� ");
				insertCheckInLabel1.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertCheckInLabel1);
				
				insertCheckInLabel2 = new Label(checkInGroupComposite2,  SWT.BORDER);
				insertCheckInLabel2.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertCheckInLabel2);
				
				Label insertCheckInLabel2_1 = new Label(checkInGroupComposite2, SWT.NONE);
				insertCheckInLabel2_1.setText("");
				
				//üũ�� - �����ȣ 
				Label insertCheckInLabel3 = new Label(checkInGroupComposite2, SWT.NONE);
				insertCheckInLabel3.setText("�����ȣ ");
				insertCheckInLabel3.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertCheckInLabel3);
				insertCheckInLabel4= new Label(checkInGroupComposite2, SWT.BORDER );
				insertCheckInLabel4.setFont(PetraFont.S9_FONT);
				
			//	insertCheckInLabel4.setText(Long.toString(getBookingNumber()));
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertCheckInLabel4);
				vacuumLabel = new Label(checkInGroupComposite2, SWT.NONE);
				
				//�缱��   üũ��
				final Composite checkInGroupComposite3 = new Composite(checkInTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(130,20).spacing(20, 20).columns(2).applyTo(checkInGroupComposite3);
				PetraGridData.defaults().grab(false, false).applyTo(checkInGroupComposite3);	
				
				ToolBar insertCheckInToolBar = new ToolBar (checkInGroupComposite3, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(insertCheckInToolBar);
				PetraGridData.defaults().grab(true, false).hint(100,50).applyTo(insertCheckInToolBar);
				final ToolItem insertRechoiceToolItem = new ToolItem(insertCheckInToolBar, SWT.NONE);
				
				ToolBar insertCheckInToolBar2 = new ToolBar (checkInGroupComposite3, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 30).applyTo(insertCheckInToolBar2);
				PetraGridData.defaults().grab(true, false).hint(100,60).applyTo(insertCheckInToolBar2);
				final ToolItem insertCheckInToolItem1 = new ToolItem(insertCheckInToolBar2, SWT.NONE);
					
				insertRechoiceToolItem.setImage(InternUIResources.getImage("reSelected.png"));
				insertCheckInToolItem1.setImage(InternUIResources.getImage("checkIn.png"));
			
				//�缱��
				insertRechoiceToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//��ȸ�� ������ �����Ͱ� �Ѿ�� ó��
						//�缱�� ��ư�� ���� ���
						checkInSashForm.setWeights(new int[]{1,0});
					}
				});
				
				//üũ��
				insertCheckInToolItem1.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//��ȸ�� ������ �����Ͱ� �Ѿ�� ó��
						
						//��ü �ʱ�ȭ
						CLabelReset(listLabel);
						
						//count �ʱ�ȭ
						for(int i = 0; i<3;i++){  
							for(int j = 0 ;j<22; j++){
								count[(22*i)+j] = 0;
							} 
						}
						selectedCoordinateList = (List<CoordinateDto>) IBatisAdapter.queryForList("coordinate_selectCoordinateOne",consumerBookingData.PLANE_ID);
						//Ŭ���� ����� �ڸ��� ��� �ҷ��´�
						for(int a= 0; a< selectedCoordinateList.size() ; a++){
							CoordinateDto coordinateDto = selectedCoordinateList.get(a);
							System.out.println("�߾ƾƾ�");
							System.out.println(coordinateDto.PLANE_ID);
							System.out.println(coordinateDto.COUNT);
							count[coordinateDto.COUNT] = 1;
							//Ŭ���ص� ����� �ڸ��� ��� 1�� ����
							final CLabel seatSelectionCLabel = listLabel.get(coordinateDto.COUNT);
							if(seatSelectionCLabel.getData().equals(coordinateDto.COUNT)){
																				//220,020,060
								seatSelectionCLabel.setBackground(new Color(null,211,211,211));}						}
																			//����
						/**/
						//üũ�� ��ư�� ���� ���
						checkInSeatConfirmationSashForm.setWeights(new int[]{0,1});
					}
				});
				
				//üũ�� ������ �Ѿ�� �¼� ����
				final Composite seatConfirmationTopComposite = new Composite(checkInSeatConfirmationSashForm, SWT.NONE );
				PetraGridLayout.defaults().margin(0, 0).applyTo(seatConfirmationTopComposite);
				PetraGridData.defaults().grab(false, true).hint(780,40).applyTo(seatConfirmationTopComposite);
				
				final Composite seatConfirmationGroupComposite1 = new Composite(seatConfirmationTopComposite, SWT.BORDER );
				PetraGridLayout.defaults().margin(155,129).spacing(10, 6).columns(22).applyTo(seatConfirmationGroupComposite1);
				PetraGridData.defaults().grab(false, true).hint(780,260).applyTo(seatConfirmationGroupComposite1);
				seatConfirmationGroupComposite1.setBackgroundImage(InternUIResources.getImage("plane.png"));
				
				
				for(int i = 0; i<3;i++){  
					for(int j = 0 ;j<22; j++){
						clbel = new CLabel(seatConfirmationGroupComposite1, SWT.NONE);
						PetraGridData.defaults().grab(false, false).hint(17,18).applyTo(clbel);
						//listLabel�� ��� �ִ´�(ó������ ��� ���õ�)
						listLabel.add(clbel);
						//clbel.setBackground(new Color(null,245,245,220));
						
						clbel.setText(Integer.toString((22*i)+j));
						clbel.setFont(PetraFont.S6_FONT);
						clbel.setData((22*i)+j);
						
						//clbel.setData(new int[]{i,j});
						 
						final CLabel seatSelectionCLabel = listLabel.get(((22*i)+j));
														//��ü listLabel�� �ҷ��� ���� �̺�Ʈ ó��
//						if(seatSelectionCLabel.getData().equals(1)){
//							seatSelectionCLabel.setBackground(new Color(null,220,020,060));}
								
						seatSelectionCLabel.addMouseListener(new MouseAdapter(){
							@Override
							public void mouseUp(MouseEvent e) {
								// TODO Auto-generated method stub
								
								//listLabel.remove(seatSelectionCLabel);
								//���� �ȵ� ��
								
								CLabelCount(seatSelectionCLabel);
								//���õ� ���� �ҷ��� CLabelCount�� �ִ´�
							}//end of MouseUp
							
							@Override
							public void mouseDown(MouseEvent e) {
								// TODO Auto-generated method stub
								
								selectLabel.remove(seatSelectionCLabel);
								//listLabel.remove(seatSelectionCLabel);
							}
						});
					}//end of Inner For
				}//end of OutFor
				
				//����� �׸� �Ʒ� �� ������
				final Composite seatConfirmationGroupComposite2 = new Composite(seatConfirmationTopComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(0,0).spacing(0,0).applyTo(seatConfirmationGroupComposite2);
				PetraGridData.defaults().grab(false, false).hint(780, 120).applyTo(seatConfirmationGroupComposite2);
				
				//������������, �¼����� �ٽ��ϱ�, �¼����� ����
				final Composite seatConfirmationInnerGroupComposite1 = new Composite(seatConfirmationGroupComposite2, SWT.NONE );
				PetraGridLayout.defaults().margin(40,0).spacing(0, 0).columns(3).applyTo(seatConfirmationInnerGroupComposite1);
				PetraGridData.defaults().grab(false, true).hint(780,50).applyTo(seatConfirmationInnerGroupComposite1);
				
				ToolBar seatConfirmationToolBar = new ToolBar (seatConfirmationInnerGroupComposite1, SWT.NONE);
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(seatConfirmationToolBar);
				PetraGridData.defaults().grab(true, false).hint(0, 50).applyTo(seatConfirmationToolBar);
				final ToolItem bookingDataExampleToolItem = new ToolItem(seatConfirmationToolBar, SWT.NONE);
				final ToolItem seatSelectionAgainToolItem = new ToolItem(seatConfirmationToolBar, SWT.NONE);
				final ToolItem seatSelectionHelpToolItem = new ToolItem(seatConfirmationToolBar, SWT.NONE);	

				bookingDataExampleToolItem.setImage(InternUIResources.getImage("bookingDataExample.png"));
				seatSelectionAgainToolItem.setImage(InternUIResources.getImage("seatSelectionAgain.png"));
				seatSelectionHelpToolItem.setImage(InternUIResources.getImage("seatSelectionHelp.png"));
				
				final Composite seatConfirmationInnerGroupComposite2 = new Composite(seatConfirmationGroupComposite2, SWT.NONE );
				PetraGridLayout.defaults().margin(540,0).spacing(0, 0).applyTo(seatConfirmationInnerGroupComposite2);
				PetraGridData.defaults().grab(false, false).hint(780,50).applyTo(seatConfirmationInnerGroupComposite2);
				
				ToolBar seatConfirmationToolBar2 = new ToolBar (seatConfirmationInnerGroupComposite2, SWT.NONE);
				PetraGridLayout.defaults().margin(540, 0).spacing(0, 0).applyTo(seatConfirmationToolBar2);
				PetraGridData.defaults().grab(false, false).hint(780, 50).applyTo(seatConfirmationToolBar2);
				final ToolItem seatSelectionOKToolItem = new ToolItem(seatConfirmationToolBar2, SWT.NONE);	
				seatSelectionOKToolItem.setImage(InternUIResources.getImage("ok.png"));
				
				//������������
				bookingDataExampleToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						//consumerBookingData
					}
				});
				
				//�¼����� �ٽ��ϱ�
				seatSelectionAgainToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						
						CLabelSelected();
					}
				});
				
				//�¼����� ����
				seatSelectionHelpToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
					}
				});
				
				//Ȯ�ι�ư 
				seatSelectionOKToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						int[] array = null;
					/*	
						for(int i=0 ; i<selectLabel.size(); i++){
							CLabel seatSelectionCLabel = selectLabel.get(i);
							array[i] = (int) seatSelectionCLabel.getData();
						}*/
						
						DialogReturn<Object> returnData = new BookingInfoDialog(getSite().getShell(),consumerBookingData,array).openWithResult();
						if(returnData.retCode == Window.OK){
							//NavigationView ���� ���õǰ��մϴ�.
							navigationView.getTable1().select(2);
							
							
							//�¼� ��û Ŭ���� �Ѱ��
							for(int i=0 ; i<selectLabel.size(); i++){
								CLabel seatSelectionCLabel = selectLabel.get(i);
								CoordinateDto coordinateDto = new CoordinateDto();
								//coordinateDto.setCOORDINATE_NUMBER((long) IBatisAdapter.queryForObject("intern_getSeq"));
								coordinateDto.setPLANE_ID(consumerBookingData.PLANE_ID);
								coordinateDto.setCOUNT(Integer.parseInt(seatSelectionCLabel.getText()));
								IBatisAdapter.execute("coordinate_insertCoordinate",coordinateDto);
							}
							CLabelReset(listLabel);
							//���⿡ select.. �װ�
							
							//��
							//checkin 1->0 ���� ����
							consumerBookingData.CHECKIN = 0;
							IBatisAdapter.execute("consumerBooking_updateConsumerBooking",consumerBookingData);
							getConsumerChecking();
							placeStackLayout.topControl = allCheckList;
							placeStackComposite.layout(); 
							allCheckSashForm.setWeights(new int[]{1,0});
						}//end of OK
						
						
					}
				});
				
				
				//��
				///3. ��ü���
				///������ üũ��  
				//placeStackComposite 2������ üũ��  Ŭ���� ������ ���� ȭ��
				
				Composite allBookingListComposite = new Composite(allCheckSashForm, SWT.NONE);
				PetraGridLayout.defaults().margin(30, 30).spacing(10, 10).applyTo(allBookingListComposite);
				PetraGridData.defaults().applyTo(allBookingListComposite);
				
				Composite allBookingListComposite1 = new Composite(allBookingListComposite, SWT.BORDER);
				PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).columns(2).applyTo(allBookingListComposite1);
				PetraGridData.defaults().applyTo(allBookingListComposite1);
				String[] allBookingListColumnHeader = {"�����ȣ","�װ�������","�����","������","�����","�¼����","üũ�ο���"};
				int[] allBookingListColumnWidth = {90,105,70,70,110,90,105};
				allBookingTableViewer = new TableViewer(allBookingListComposite1, SWT.FULL_SELECTION | SWT.MULTI);
				allBookingTable = allBookingTableViewer.getTable();	
				
				PetraGridLayout.defaults().applyTo(allBookingTable);
				PetraGridData.defaults().span(2, 1).applyTo(allBookingTable);
										//�̷��� �س����� ���̺��� ���ٿ� ��������. ��� span(3,1)�� �ؾ���
				TableUtil.makeTableColumns(allBookingTable, allBookingListColumnHeader, allBookingListColumnWidth);
				
				allBookingTable.setHeaderVisible(true); 
				allBookingTable.setLinesVisible(true);
					//false �� ��� : ���� ������ �����
				allBookingTableViewer.setContentProvider(new ArrayContentProvider());
				allBookingTableViewer.setLabelProvider(bookingTableLabelProvider);
				
				allBookingTableViewer.setInput(allBookingList);
				allBookingTableViewer.refresh();

				allBookingTable.addMouseListener(new MouseAdapter(){

					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						//��
						String[] departure= {"��õ", "����","�뱸","����"}; 
						String[] arrive = {"����","����¡","����","�ĸ�"};
						allBookingListTableIndex = allBookingTable.getSelectionIndex();
						consumerBookingData = allBookingList.get(allBookingListTableIndex); 
						
						//������ ����
						setConsumerBookingData(consumerBookingData);

						insertReservationConfirmationLabel2.setText(Long.toString(consumerBookingData.BOOKING_NUMBER));
						
						//�����
						for(int i=0; i<departure.length; i++){
							if(consumerBookingData.DEPARTURE_PLACE==i){
								insertReservationConfirmationLabel4.setText(departure[i]);
							}
						}
						insertReservationConfirmationLabel6.setText(consumerBookingData.DEPARTURE_DATE);
	
						//������
						for(int i=0; i<arrive.length; i++){
							if(consumerBookingData.ARRIVE_PLACE==i){
								insertReservationConfirmationLabel8.setText(arrive[i]);
							}
						}
						
						
						//�¼� ����
						if(consumerBookingData.SEAT_TYPE==0){
							insertReservationConfirmationLabel12.setText("�Ϲݼ�");
						}else if(consumerBookingData.SEAT_TYPE==1){
							insertReservationConfirmationLabel12.setText("����Ͻ���");
						}else if(consumerBookingData.SEAT_TYPE==2){
							insertReservationConfirmationLabel12.setText("�ϵ");
						}else{	}
						
						
						insertReservationConfirmationLabel14.setText(consumerBookingData.FLIGHT_NAME);
						
						//consumerBookingData.BOOKING_NUMBER
						// (long) 2859693
						consumerBookingData = (ConsumerBookingDto) IBatisAdapter.queryForObject("consumerBookingNumber_selectConsumerBookingNumber",consumerBookingData);
						
						if(consumerBookingData.CHECKIN==1){
							//üũ���� ���� ���� ���
							MessageDialog.openWarning(null, "üũ���ʼ�", "üũ�� ���ֽñ� �ٶ��ϴ�.");
								//�޼��� ���̾�αװ� �ƴ� ���̾�α׸� ���� ���� �Ѿ���� �غ���
							//üũ�ο� �Է� ������/�����ȣ �� �Է�
							for(int i=0; i<arrive.length; i++){
								if(consumerBookingData.ARRIVE_PLACE==i){
									insertCheckInLabel2.setText(arrive[i]);
								}
							}
							navigationView.getTable1().select(1);
							insertCheckInLabel4.setText(Long.toString(consumerBookingData.BOOKING_NUMBER));
							placeStackLayout.topControl = bookingListCheckIn;
							placeStackComposite.layout(); 
							checkInSashForm.setWeights(new int[]{0,1}); //�װ� ���̺��� ������
							checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
							
						}else if(consumerBookingData.CHECKIN==0){
							//üũ���� �� ���
							//���̺� �÷� Ŭ���� SashForm �̵�
							allCheckSashForm.setWeights(new int[]{0,1}); 
							//checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
						}
					
					}
				});
				
				
				//3 ��üȮ�� Ŭ���� ������ sashform �����ʺκ�
				//SashForm ����

				reservationConfirmationSashForm = new SashForm(allCheckSashForm, SWT.HORIZONTAL); 
				PetraGridLayout.defaults().margin(0, 0).spacing(0, 0).applyTo(reservationConfirmationSashForm);
				PetraGridData.defaults().applyTo(reservationConfirmationSashForm);
				reservationConfirmationSashForm.setSashWidth(0);
				
				final ScrolledComposite reservationConfirmationTopScrolledComposite = new ScrolledComposite(reservationConfirmationSashForm, SWT.H_SCROLL | SWT.V_SCROLL  );
				PetraGridLayout.defaults().margin(0, 0).applyTo(reservationConfirmationTopScrolledComposite);
				PetraGridData.defaults().grab(false, true).hint(620,40).applyTo(reservationConfirmationTopScrolledComposite);
				
				final Composite reservationConfirmationTopComposite = new Composite(reservationConfirmationTopScrolledComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(0, 0).applyTo(reservationConfirmationTopComposite);
				PetraGridData.defaults().grab(false, true).hint(620,40).applyTo(reservationConfirmationTopComposite);
															//780,40
				
				reservationConfirmationTopScrolledComposite.setContent(reservationConfirmationTopComposite);
				reservationConfirmationTopScrolledComposite.setMinSize(600, 600);
				reservationConfirmationTopScrolledComposite.setExpandHorizontal(true);
				reservationConfirmationTopScrolledComposite.setExpandVertical(true);
				reservationConfirmationTopScrolledComposite.addListener(SWT.Activate, new Listener() {

						@Override
						public void handleEvent(Event event) {
							// TODO Auto-generated method stub
							
						}
			            });
				
				
				final Composite reservationConfirmationGroupComposite1 = new Composite(reservationConfirmationTopComposite, SWT.NONE );
				PetraGridLayout.defaults().margin(20,0).spacing(0, 0).applyTo(reservationConfirmationGroupComposite1);
				PetraGridData.defaults().grab(false, false).hint(620,150).applyTo(reservationConfirmationGroupComposite1);	
														  //780,150
				
				
				
				final Group reservationConfirmationGroup = new Group(reservationConfirmationGroupComposite1,   SWT.COLOR_GRAY | SWT.BOLD);
											//���� 70,120
				PetraGridLayout.defaults().margin(200,0).spacing(0, 70).applyTo(reservationConfirmationGroup);
				PetraGridData.defaults().grab(false, true).hint(620,150).applyTo(reservationConfirmationGroup);	
															//750,150
				
				Label explanationReservationConfirmationLabel1 = new Label(reservationConfirmationGroup, SWT.NONE);
				explanationReservationConfirmationLabel1.setText("���� ���� Ȯ��");
				explanationReservationConfirmationLabel1.setFont(PetraFont.S15_B_FONT);
				PetraGridData.defaults().grab(false, false).hint(400,50).applyTo(explanationReservationConfirmationLabel1);
																//550
				
				Label explanationReservationConfirmationLabel2 = new Label(reservationConfirmationGroup, SWT.NONE);
				explanationReservationConfirmationLabel2.setText(
				"�� üũ-���� �� ����\n"+
				"�� �¼� ��ü�� �� �����մϴ�.\n");
				explanationReservationConfirmationLabel2.setFont(PetraFont.S10_FONT);
				PetraGridData.defaults().grab(false, false).hint(400,60).applyTo(explanationReservationConfirmationLabel2);
															//��590
				
				final Composite reservationConfirmationGroupComposite2 = new Composite(reservationConfirmationTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(85,10).spacing(10, 5).columns(3).applyTo(reservationConfirmationGroupComposite2);
												//40
				PetraGridData.defaults().grab(false, false).applyTo(reservationConfirmationGroupComposite2);	
				
				//����Ȯ�� - Booking Numberǥ��
				Label insertReservationConfirmationLabel1 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel1.setText("���� ��ȣ ");
				insertReservationConfirmationLabel1.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel1);
				
				insertReservationConfirmationLabel2 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel2.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel2);
				
				// Booking Number �������̼� ����
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				//informationLabel1.setImage(InternUIResources.getImage("information2.png"));
				
				/*final ToolTip informationTip1 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip1.setMessage("�����Ͻ� �����ȣ�Դϴ�");
				informationTip1.setAutoHide(true);
				informationLabel1.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip1.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip1.setVisible(false);
					}});*/
				
				//����Ȯ�� - Departureǥ��
				Label insertReservationConfirmationLabel3 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel3.setText("����� ");
				insertReservationConfirmationLabel3.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel3);
				
				insertReservationConfirmationLabel4 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel4.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel4);
				
				// Departure �������̼� ����
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				/*informationLabel2.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip2 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip2.setMessage("�����Ͻ� ������Դϴ�");
				informationTip2.setAutoHide(true);
				informationLabel2.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip2.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip2.setVisible(false);
					}
					});*/
				
				//����Ȯ�� - Departure Dateǥ��
				Label insertReservationConfirmationLabel5 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel5.setText("��߳�¥ ");
				insertReservationConfirmationLabel5.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel5);
				
				insertReservationConfirmationLabel6 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel6.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel6);
				
				// Departure Date �������̼� ����
				Label informationLabel3 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel3.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip3 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip3.setMessage("����� ���� ����� ž�� ��¥�Դϴ�");
				informationTip3.setAutoHide(true);
				informationLabel3.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip3.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip3.setVisible(false);
					}});
				
				
				//����Ȯ�� - AARIVE ǥ��
				Label insertReservationConfirmationLabel7 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel7.setText("������ ");
				insertReservationConfirmationLabel7.setFont(PetraFont.S10_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel7);
				
				insertReservationConfirmationLabel8 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel8.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel8);
				
				// AARIVE �������̼� ����
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				/*informationLabel4.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip4 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip4.setMessage("�����Ͻ� �������Դϴ�");
				informationTip4.setAutoHide(true);
				informationLabel4.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip4.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip4.setVisible(false);
					}});
				*/
				
				//����Ȯ�� - AARIVE DATEǥ��
				Label insertReservationConfirmationLabel9 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel9.setText("������¥ ");
				insertReservationConfirmationLabel9.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel9);
				
				insertReservationConfirmationLabel10 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel10.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel10);
				
				// AARIVE DATE �������̼� ����
				Label informationLabel5 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel5.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip5 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip5.setMessage("������� ���� ������ ��¥�Դϴ�");
				informationTip5.setAutoHide(true);
				informationLabel5.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip5.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip5.setVisible(false);
					}});
				
				
				//����Ȯ�� - SEAT TYPEǥ��
				Label insertReservationConfirmationLabel11 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel11.setText("�¼� ���� ");
				insertReservationConfirmationLabel11.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel11);
				
				insertReservationConfirmationLabel12 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel12.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel12);
				
				// SEAT TYPE �������̼� ����
				Label informationLabel6 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel6.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip6 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip6.setMessage("�Ϲݼ�/����Ͻ���/�ϵ");
				informationTip6.setAutoHide(true);
				informationLabel6.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip6.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip6.setVisible(false);
					}});
				
				
				//����Ȯ�� - FLIGHT NAMEǥ��
				Label insertReservationConfirmationLabel13 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel13.setText("�װ� �̸� ");
				insertReservationConfirmationLabel13.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel13);
				
				insertReservationConfirmationLabel14 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel14.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel14);
				
				// FLIGHT NAME �������̼� ����
				informationLabel = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				/*informationLabel7.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip7 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip7.setMessage("�����Ͻ� �װ� �̸��Դϴ�");
				informationTip7.setAutoHide(true);
				informationLabel7.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip7.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip7.setVisible(false);
					}});*/
				
				
				//����Ȯ�� - SEAT NUMBER ǥ��
				Label insertReservationConfirmationLabel15 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				insertReservationConfirmationLabel15.setText("�¼� ����");
				insertReservationConfirmationLabel15.setFont(PetraFont.S11_FONT);
				PetraGridData.defaults().grab(false, false).hint(200,30).applyTo(insertReservationConfirmationLabel15);
				
				insertReservationConfirmationLabel16 = new Label(reservationConfirmationGroupComposite2,  SWT.BORDER);
				insertReservationConfirmationLabel16.setFont(PetraFont.S9_FONT);
				PetraGridData.defaults().grab(false, false).hint(250,30).applyTo(insertReservationConfirmationLabel16);
				
				// SEAT NUMBER �������̼� ����
				Label informationLabel8 = new Label(reservationConfirmationGroupComposite2, SWT.NONE);
				informationLabel8.setImage(InternUIResources.getImage("information2.png"));
				
				final ToolTip informationTip8 = new ToolTip(getSite().getShell(), SWT.BALLOON);
				informationTip8.setMessage("��û�Ͻ� �¼� ����");
				informationTip8.setAutoHide(true);
				informationLabel8.addMouseTrackListener(new MouseTrackAdapter(){
					@Override
					public void mouseHover(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip8.setVisible(true);
					}

					@Override
					public void mouseExit(MouseEvent e) {
						// TODO Auto-generated method stub
						informationTip8.setVisible(false);
					}});
				
				
				//����Ʈ �ǵ��ư���
				final Composite reservationConfirmationGroupComposite3 = new Composite(reservationConfirmationTopComposite, SWT.NONE);
				PetraGridLayout.defaults().margin(120,10).spacing(0, 10).columns(2).applyTo(reservationConfirmationGroupComposite3);
				PetraGridData.defaults().grab(false, false).applyTo(reservationConfirmationGroupComposite3);	
				
				ToolBar insertReservationConfirmationToolBar = new ToolBar (reservationConfirmationGroupComposite3, SWT.NONE);
				PetraGridData.defaults().grab(true, true).applyTo(insertReservationConfirmationToolBar);
				final ToolItem reservationConfirmationRebackToolItem = new ToolItem(insertReservationConfirmationToolBar, SWT.NONE);
				final ToolItem reservationConfirmationPrintToolItem = new ToolItem(insertReservationConfirmationToolBar, SWT.NONE);
				final ToolItem reservationConfirmationOKToolItem = new ToolItem(insertReservationConfirmationToolBar, SWT.NONE);	
				
				reservationConfirmationRebackToolItem.setImage(InternUIResources.getImage("reSelected.png"));
				reservationConfirmationPrintToolItem.setImage(InternUIResources.getImage("reprint.png"));
				reservationConfirmationOKToolItem.setImage(InternUIResources.getImage("ok.png"));
				
				//�缱��
				reservationConfirmationRebackToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						allCheckSashForm.setWeights(new int[]{1,0});
					}
				});
				
				//����Ʈ�ϱ�
				reservationConfirmationPrintToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("����Ʈ�Դϴ�");
					}
				});
				
				//�������
				reservationConfirmationOKToolItem.addSelectionListener(new SelectionAdapter(){
					@Override
					public void widgetSelected(SelectionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("��������Դϴ�");
					}
				});
				
				initialize();
			}
	
		///�װ��� ��ȸ�� ���� ��� �� ������ ������ ����
		public boolean dataValidation(){
				//�� ���鸸 �� �ְ� ó���ϴ� ��
				if(departureCombo.getText().trim().equals("")){
					//����� ���þ��Ѱ��
					MessageDialog.openInformation(null, "�˸�", "������� �Է� ���ּ���.");
					departureCombo.setFocus();
					//�׺κ��� �ٽ� �Է��� �� �ְ� ��Ŀ���� �����ִ� ��
					return false;
				}
				if(destinationCombo.getText().trim().equals("")){
					MessageDialog.openInformation(null, "�˸�", "�������� �Է� ���ּ���.");
					destinationCombo.setFocus();
					return false;
				}
		
				if(departureDate.toString().equals(smpleDateFormat.format(date))){
					MessageDialog.openInformation(null, "�˸�", "��߳�¥�� �Է� ���ּ���.");
					departureDate.setFocus();
					return false;
				}else {
					if(dataValidation==0){
						//���� ���
					}else if(dataValidation==1){
						//�պ��� ���	
						if(arriveDate.toString().equals(smpleDateFormat.format(date))){
							MessageDialog.openInformation(null, "�˸�", "������¥�� �Է� ���ּ���.");
							arriveDate.setFocus();
							return false;
						}
					}	
				}
			return true;
		}//end of dataValidation() �޼ҵ�
	
			private void initialize() {
				
				placeStackLayout.topControl = airlineTicketBook;
				placeStackComposite.layout(); 
				
				navigationView.getTable1().select(0); //�׺���̼� �信�� 0��° Ŭ���ǰ� �ʱ�ȭ
				
				//����⿹�Žý���
				placeSashForm.setWeights(new int[]{1,0});
				//������&üũ��Ȯ��
				checkInSashForm.setWeights(new int[]{1,0});
				//����⿹�Žý��� -> ���̺�/��û
				bookingListSashForm.setWeights(new int[]{1,0});
				//sashForm ����
				checkInSeatConfirmationSashForm.setWeights(new int[]{1,0});
				
				oneWay.setSelection(true);
				generalSeat.setSelection(true);
				/////2
				stackLayout.topControl = stackComposite1;
				//������ ������ �÷�������, �⺻����
				stackComposite.layout(); //������Ʈ �϶�
	
				searchInitialize();  //DB�ʱ�ȭ
				
				//�������
				//IBatisAdapter.queryForObject("consumerBooking_select");
				
				//2. ������/üũ�� ���̺� ��Ÿ����
				getConsumerBooking();
				//3. ��ü��� ���̺� ��Ÿ����
				getConsumerChecking();
				
				
			}
			
			
			//////////////////////////////////SearchView
			private void searchInitialize() {
				//�ʱ�ȭ
				////////�װ� ���
				flightDto1.FLIGHT_ID =  (long) IBatisAdapter.queryForObject("flight_getSeq");
				flightDto1.FLIGHT_NAME = "�����װ�";
				IBatisAdapter.execute("flight_insertFlight",flightDto1);
				
				flightDto2.FLIGHT_ID =  (long) IBatisAdapter.queryForObject("flight_getSeq");
				flightDto2.FLIGHT_NAME = "�ƽþƳ��װ�";
				IBatisAdapter.execute("flight_insertFlight",flightDto2);
				
				//����� ���
				planeDto1.PLANE_ID = (long) IBatisAdapter.queryForObject("plane_getSeq");
				planeDto1.FLIGHT_ID = flightDto1.FLIGHT_ID;
				planeDto1.SEAT_TYPE = 0;
				//0�̸� �Ϲݼ� 1�̸� ����Ͻ� 2�̸� �ϵ
				//planeDto1.ALL_SEAT = 25; //�����װ� �� ����� �¼��� 10��
				planeDto1.X_SEAT = 24;
				planeDto1.Y_SEAT = 3;
				IBatisAdapter.execute("plane_insertPlane",planeDto1);
				
				planeDto2.PLANE_ID = (long) IBatisAdapter.queryForObject("plane_getSeq");
				planeDto2.FLIGHT_ID = flightDto2.FLIGHT_ID;
				planeDto2.SEAT_TYPE = 1;
				//0�̸� �Ϲݼ� 1�̸� ����Ͻ� 2�̸� �ϵ
				//planeDto2.ALL_SEAT = 15; //�����װ� �� ����� �¼��� 10��
				planeDto2.X_SEAT = 24;
				planeDto2.Y_SEAT = 3;
				IBatisAdapter.execute("plane_insertPlane",planeDto2);
				
				
				//���� ���
				departureDto1.DEPARTURE_ID = (long) IBatisAdapter.queryForObject("departure_getSeq");
				departureDto1.PLANE_ID = planeDto1.PLANE_ID;
				departureDto1.DEPARTURE_TIME = "11:00AM";
				departureDto1.ARRIVE_TIME = "12:10PM";
				departureDto1.DEPARTURE_DATE = "2016-1-28";
				departureDto1.ARRIVE_DATE = "2016-1-31";
				departureDto1.DEPARTURE_PLACE = 0; //0�� ��õ
				departureDto1.ARRIVE_PLACE = 0; //0 ����
				IBatisAdapter.execute("departure_insertDeparture",departureDto1);
				
				departureDto2.DEPARTURE_ID = (long) IBatisAdapter.queryForObject("departure_getSeq");
				departureDto2.PLANE_ID = planeDto2.PLANE_ID;
				departureDto2.DEPARTURE_TIME = "11:00AM";
				departureDto2.ARRIVE_TIME = "12:10PM";
				departureDto2.DEPARTURE_DATE = "2016-2-1";
				departureDto2.ARRIVE_DATE = "2016-2-3";
				departureDto2.DEPARTURE_PLACE = 3; //3 ����
				departureDto2.ARRIVE_PLACE = 2; //2 ����
				IBatisAdapter.execute("departure_insertDeparture",departureDto2);
				
			}
			
			public void getSearchDto(ConsumerDto data){
				//IBatisAdapter.execute("consumer_insertConsumer",data);
				
				//���� �˻� 
				searchList = IBatisAdapter.queryForList("departure_selectConditionDepartures",data);
				searchTableViewer.setInput(searchList);
				searchTableViewer.refresh();
			}
			
			//����� ���� �ֱ�
			public void setConsumerDtoDATA(ConsumerDto data){
				//insert  
				IBatisAdapter.execute("consumer_insertConsumer",data);
				this.data = data;
			}
			
			// ������/üũ�ο� �Ѹ���
			public void getConsumerBooking(){
				//bookingList = IBatisAdapter.queryForList("consumerBooking_selectConsumerBooking",(long) 2859693);
				bookingList = IBatisAdapter.queryForList("consumerBooking_selectConsumerCheckingBooking",(long) 2859693);
				bookingTableViewer.setInput(bookingList);
				bookingTableViewer.refresh();
			}
			
			// 3.��üȮ�ο� �Ѹ���
			public void getConsumerChecking(){
				allBookingList = IBatisAdapter.queryForList("consumerBooking_selectConsumerAllCheckingBooking",(long) 2859693);
				allBookingTableViewer.setInput(allBookingList);
				allBookingTableViewer.refresh();
			}
						
			
			public void getRequest(ConsumerDto data,int index){
				
				//���� ���⼭ ConsumerDto selected �ؼ� consumer_number�� ������;���
				//consumer_selectConsumer
				//data=(ConsumerDto) IBatisAdapter.queryForObject("consumer_selectConsumer",data);
				
				consumerBookingDto.BOOKING_NUMBER = (long) IBatisAdapter.queryForObject("intern_getSeq");
				SearchDto searchRequestData = searchList.get(index); //����
				consumerBookingDto.setBOOKING_NUMBER((long) IBatisAdapter.queryForObject("departure_getSeq"));
				consumerBookingDto.setFLIGHT_ID(searchRequestData.FLIGHT_ID);
				consumerBookingDto.setPLANE_ID(searchRequestData.PLANE_ID);
				consumerBookingDto.setDEPARTURE_ID(searchRequestData.DEPARTURE_ID);
				consumerBookingDto.setCONSUMER_ID((long) 2859693);
				consumerBookingDto.setFLIGHT_NAME(searchRequestData.FLIGHT_NAME);
				consumerBookingDto.setDEPARTURE_PLACE(searchRequestData.DEPARTURE_PLACE);
				consumerBookingDto.setARRIVE_PLACE(searchRequestData.ARRIVE_PLACE);
				consumerBookingDto.setDEPARTURE_DATE(searchRequestData.DEPARTURE_DATE);
				consumerBookingDto.setSEAT_TYPE(searchRequestData.SEAT_TYPE);
				consumerBookingDto.setSEATNUMBER(data.SEATNUMBER);
				consumerBookingDto.setCHECKIN(data.CHECKIN);
				
				IBatisAdapter.execute("consumerBooking_insertBooking",consumerBookingDto);
				getConsumerBooking();
			}
			
			public void CLabelSelected(){
				
				CLabelReset(listLabel);
				
				selectedCoordinateList = (List<CoordinateDto>) IBatisAdapter.queryForList("coordinate_selectCoordinateOne",consumerBookingData.PLANE_ID);
				//Ŭ���� ����� �ڸ��� ��� �ҷ��´�
				for(int a= 0; a< selectedCoordinateList.size() ; a++){
					CoordinateDto coordinateDto = selectedCoordinateList.get(a);
					System.out.println("�¼� �ٽ� ����");
					System.out.println(coordinateDto.PLANE_ID);
					System.out.println(coordinateDto.COUNT);
					count[coordinateDto.COUNT] = 1;
					//Ŭ���ص� ����� �ڸ��� ��� 1�� ����
					final CLabel seatSelectionCLabel = listLabel.get(coordinateDto.COUNT);
						
					if(seatSelectionCLabel.getData().equals(coordinateDto.COUNT)){
																		//220,020,060
						seatSelectionCLabel.setBackground(new Color(null,211,211,211));}
																	//������ �����͸� ��� �ִ´�
				
					allCount=1;		//��� ��û �ʱ�ȭ
					cancle = 0;		//���� ���� �ʱ�ȭ
					again = 1;
					selectLabel.clear();
					//CLabelCount(seatSelectionCLabel);
				}
			}
			
			public void CLabelReset(List<CLabel> listLabel){
				for(int i=0; i<listLabel.size();i++){
					final CLabel seatSelectionCLabel = listLabel.get(i);
					seatSelectionCLabel.setBackground(new Color(null,245,245,220));
					for(int j=0; j<count.length;j++){
						count[j] = 0; 	//��û �ʱ�ȭ
					}
					allCount=1;		//��� ��û �ʱ�ȭ
					cancle = 0;		//���� ���� �ʱ�ȭ
					again = 1;
				}
			}//end of CLabelReset()
			
			
			//�¼��� �������� ��, �¼��� Ŭ���Ǿ� �� ����
			public void CLabelCount(CLabel seatSelectionCLabel){
				
				if(count[Integer.parseInt(seatSelectionCLabel.getText())]==0 && cancle==1){
					//�� ��û�ҷ��� �ϴµ� MAX�� �ʰ����� ���
					MessageDialog.openConfirm(null, "��û����", "��û������ "+consumerBookingData.SEATNUMBER+"���Դϴ�.");
					return ;
				}
				if(count[Integer.parseInt(seatSelectionCLabel.getText())]==1){
					count[Integer.parseInt(seatSelectionCLabel.getText())]  = 0;
					seatSelectionCLabel.setBackground(new Color(null,245,245,220));
																//������
					allCount--;
					
					selectLabel.remove(seatSelectionCLabel);
					//��û Ŭ���� ����� ���
					if(allCount<=consumerBookingData.SEATNUMBER){
						//MAX�ο����� ������� �߰� ����
						cancle = 0;
					}
				}
				else if(count[Integer.parseInt(seatSelectionCLabel.getText())]==0 && cancle ==0){
					//��û Ŭ���� �ϴ� ���
					if(allCount>consumerBookingData.SEATNUMBER){
						MessageDialog.openConfirm(null, "��û����", "��û������ "+consumerBookingData.SEATNUMBER+"���Դϴ�.");
						cancle=1;
						return ;
					}
					if(again==1){
						//�¼� ���� �ٽ��ϱ⸦ �������
						again =0;
					}else{
						count[Integer.parseInt(seatSelectionCLabel.getText())]  = 1;
						seatSelectionCLabel.setBackground(new Color(null,220,020,060));
																//null,211,211,211
						selectLabel.add(seatSelectionCLabel);
						//���õ� ��
						allCount++;
					}
				}//end of ��û Ŭ���� �ϴ� ���
			}
			
			public void setFont(){
				font = new Font(null, "font", iPreferenceStore.getInt("text_font_size"), SWT.None);
				//messageText.setFont(font);
				 
				bookingTableLabelProvider.setFont(font);
				
			}
			
			public void setColor(int i){
				Color Fontcolor = setFontColor(i);
				logger.info("Fontcolor�� :"+Fontcolor);
				
				bookingTableLabelProvider.setForeground(Fontcolor);
				//messageText.setForeground(Fontcolor);
			}
			
			public Color setFontColor(int i){
				logger.info("i��? "+i);
				switch(i){
					case 0:
						//black
						return color = new Color(null, 0, 0, 0);
					case 1:
						//blue
						return color = new Color(null,0, 0, 255);
					case 2:
						//GREEN
						return color = new Color(null,0, 255, 0);
					case 3:
						return color = new Color(null,255, 0, 0);
					default :
						return color = new Color(null,0, 0, 0);
				}
				
			}
			
			@Override
			public void setFocus() {
				// TODO Auto-generated method stub
			}
			//placeStackLayout �������� �޼ҵ�
			public StackLayout getPlaceStackLayout() {
				return placeStackLayout;
			}
			public void setPlaceStackLayout(StackLayout placeStackLayout) {
				this.placeStackLayout = placeStackLayout;
			}
			//getPlaceStackComposite
			public Composite getPlaceStackComposite() {
				return placeStackComposite;
			}
			public void setPlaceStackComposite(Composite placeStackComposite) {
				this.placeStackComposite = placeStackComposite;
			}
			
			
			
			//getPlaceStackComposite1
			public Composite getPlaceStackComposite1() {
				return airlineTicketBook;
			}
			public void setPlaceStackComposite1(Composite placeStackComposite1) {
				this.airlineTicketBook = placeStackComposite1;
			}
			
			//getPlaceStackComposite2
			public Composite getPlaceStackComposite2() {
				return bookingListCheckIn;
			}
			public void setPlaceStackComposite2(Composite placeStackComposite2) {
				this.bookingListCheckIn = placeStackComposite2;
			}
			
			//placeSashForm
			public SashForm getPlaceSashForm() {
				return placeSashForm;
			}
			public void setPlaceSashForm(SashForm placeSashForm) {
				this.placeSashForm = placeSashForm;
			}
			
			//checkInSashForm
			public SashForm getCheckInSashForm() {
				return checkInSashForm;
			}
			public void setCheckInSashForm(SashForm checkInSashForm) {
				this.checkInSashForm = checkInSashForm;
			}
			
			//bookingListSashForm
			public SashForm getBookingListSashForm() {
				return bookingListSashForm;
			}
			public void setBookingListSashForm(SashForm bookingListSashForm) {
				this.bookingListSashForm = bookingListSashForm;
			}

			//getCheckInSeatConfirmationSashForm
			public SashForm getCheckInSeatConfirmationSashForm() {
				return checkInSeatConfirmationSashForm;
			}

			public void setCheckInSeatConfirmationSashForm(
					SashForm checkInSeatConfirmationSashForm) {
				this.checkInSeatConfirmationSashForm = checkInSeatConfirmationSashForm;
			}
			
			
		
			
			//getAllCheckList
			public Composite getAllCheckList() {
				return allCheckList;
			}

			public void setAllCheckList(Composite allCheckList) {
				this.allCheckList = allCheckList;
			}

			//getAllCheckSashForm
			public SashForm getAllCheckSashForm() {
				return allCheckSashForm;
			}

			public void setAllCheckSashForm(SashForm allCheckSashForm) {
				this.allCheckSashForm = allCheckSashForm;
			}

			//���� üũ�� �Ҷ� ���̺��� Ŭ���� ������ �����ϴ� ��
			public ConsumerBookingDto getConsumerBookingData() {
				return consumerBookingData;
			}

			public void setConsumerBookingData(ConsumerBookingDto consumerBookingData) {
				
				this.consumerBookingData = consumerBookingData;
			}

			
			//getBookingTableViewer
			public TableViewer getBookingTableViewer() {
				return bookingTableViewer;
			}

			//getAllBookingTableViewer
			public TableViewer getAllBookingTableViewer() {
				return allBookingTableViewer;
			}

		

			
			
			

}
