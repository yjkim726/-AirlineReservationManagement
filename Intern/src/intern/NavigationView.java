package intern;

import intern.define.ContainerTreeItemIdDefine;
import intern.provider.NavigationTableProvider;
import intern.provider.SearchTableLabelProvider;
import intern.provider.TreeLabelProvider;
import intern.util.FrequentlyUsedMethodsUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.sinsiway.petra.commons.log.PetraLogger;
import com.sinsiway.petra.commons.sql.connection.ConnectionChangeEvent;
import com.sinsiway.petra.commons.sql.connection.ConnectionChangeEvent.TYPE;
import com.sinsiway.petra.commons.sql.connection.ConnectionManager;
import com.sinsiway.petra.commons.sql.connection.PetraConnection;
import com.sinsiway.petra.commons.sql.define.DBDefine;
import com.sinsiway.petra.commons.sql.define.DBInfo;
import com.sinsiway.petra.commons.sql.define.SiteInformation;
import com.sinsiway.petra.commons.sql.dto.InternDto;
import com.sinsiway.petra.commons.sql.dto.SearchDto;
import com.sinsiway.petra.commons.sql.util.IBatisAdapter;
import com.sinsiway.petra.commons.ui.control.TableFilterComposite;
import com.sinsiway.petra.commons.ui.treeView.ContainerTreeItem;
import com.sinsiway.petra.commons.ui.treeView.TreeContentProvider;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;
import com.sinsiway.petra.commons.ui.util.TableUtil;

public class NavigationView extends ViewPart {
	public static final String ID = "Intern.navigationView";
	
	private Logger logger = PetraLogger.getLogger(NavigationView.class);
	//�������� �����Ͱ��� �ֿܼ� �ѹ� ���� ���   �޼ҵ� initialize() ���� Ȯ��
	private LocalSelectionTransfer transfer = LocalSelectionTransfer.getTransfer();
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private Table table1;
	private ContainerTreeItem rootContainer;
	private SiteInformation siteInfo;
	private IPreferenceStore store = PlatformUI.getPreferenceStore();

	public void createPartControl(Composite parent) {
		//view.setInternList(internList);
		final String[] menuList = {"����⿹�Žý���","������/üũ��","��������/���"};
		
		PetraGridLayout.defaults().applyTo(parent);
		PetraGridData.defaults().applyTo(parent);
		
		
		Composite tableComposite = new Composite(parent, SWT.NONE);
		PetraGridLayout.defaults().margin(0, 0).spacing(10, 10).columns(1).applyTo(tableComposite);
		PetraGridData.defaults().applyTo(tableComposite);
		String[] columnHeader = {"����ڿ�����"};
		int[] columnWidth = {200};
		tableViewer = new TableViewer(tableComposite, SWT.None/*SWT.FULL_SELECTION SWT.MULTI*/);
		table1 = tableViewer.getTable();
		
		PetraGridLayout.defaults().applyTo(table1);
		PetraGridData.defaults().span(1, 1).applyTo(table1);
		
		TableUtil.makeTableColumns(table1, columnHeader, columnWidth);
		table1.setHeaderVisible(false);
		table1.setLinesVisible(false);
		tableViewer.setContentProvider(new ArrayContentProvider());
		//tableViewer.setLabelProvider(new NavigationTableProvider());
		
		table1.addListener (SWT.Selection, new Listener () {
			@Override
			public void handleEvent (Event event) {
				String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
				System.out.println (event.item + " " + string);
				
				System.out.println("getData : "+event.item.getData());
				BookAFlightView view = (BookAFlightView) FrequentlyUsedMethodsUtil.getView(BookAFlightView.ID);
				
				if(event.item.getData().equals(menuList[0])){
					//BookAFlightView�� �ҷ���
					//����� ���� �ý���
					view.getPlaceStackLayout().topControl = view.getPlaceStackComposite1();
					view.getPlaceStackComposite().layout();
					//���� & �װ��� ���� 1:0
					view.getPlaceSashForm().setWeights(new int[]{1,0});
					//����⿹�Žý��� -> ���̺�/��û
					view.getBookingListSashForm().setWeights(new int[]{1,0});
					
					view.getCheckInSashForm().setWeights(new int[]{1,0});
					view.getCheckInSeatConfirmationSashForm().setWeights(new int[]{1,0});
					
					
				}else if(event.item.getData().equals(menuList[1])){
					//������/üũ��
					view.getPlaceStackLayout().topControl = view.getPlaceStackComposite2();
					view.getPlaceStackComposite().layout();
					view.getCheckInSashForm().setWeights(new int[]{1,0});
					
					view.getConsumerBooking();
					view.getBookingTableViewer().refresh();
				}else if(event.item.getData().equals(menuList[2])){
					//��ü���
					view.getPlaceStackLayout().topControl = view.getAllCheckList();
					view.getPlaceStackComposite().layout();
					view.getAllCheckSashForm().setWeights(new int[]{1,0});
					
					view.getConsumerChecking();
					view.getAllBookingTableViewer().refresh();
				}
				else{
				}
				
			
			}
		});
//		table1.addSelectionListener(new SelectionListener(){
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
	
		tableViewer.setInput(menuList);
		tableViewer.refresh();
	
	    
		//�ʱ�ȭ
		initialize();
	}

	private void initialize() {
		connectionSoha(); 
		//Ŀ�ؼ��� �δ´�.
		
		String connectionTest = (String) IBatisAdapter.queryForObject("intern_selectConnectionTest");
						//long���� �� ��� petra4Access.xml ����  ������ Ÿ���� String���� ����� ������ �ȵ�   / ���̺��
		//IBatisAdapter = IBatis.jar ������ �м��� �̾Ƽ� ���Ÿ� ��Ƽ� �����
		if(connectionTest.equals("OK")){
			logger.info("���� ����");
			//INFO - NavigationView : initialize ���� ����  �̶�� console â�� �� 
			//insert, update, delete �� ��� execute() �� ��� 
		}else{
			logger.info("���� ����");
			return;
		}
	}

	public void setInternList(List<InternDto> internList){
		//public ���� �ؼ� view�� getInternList()���� �޼ҵ� ������ ��
		//view���� ���̺��� �߰��ϰ� �Ǹ� ���� ���ϸ��- ~ �׺κп� ���ÿ� �߰� �ǵ��� �ϴ� �޼ҵ�
		
		rootContainer.clearChild();
		//rootContainer�� �ʱ�ȭ
		//Message�� �߰��ϰ� �Ǹ� ���ϸ�� �ؿ� �ִ� �͵� ���� �߰��� �ȴ�. test*2 
		//����ȭ�� �Ŵ� ����
		
		
		
		ContainerTreeItem childContainer = null;
		ContainerTreeItem littleContainer = null;
		for(InternDto data : internList){
			childContainer = new ContainerTreeItem(rootContainer, data.INTERN_NAME+"("+data.INTERN_ID+")", ContainerTreeItemIdDefine.CONTAINER_CHILD);
											// �θ� ������ �� ����� �Ѵ� .	 +"("+data.INTERN_ID+")"��� �ϸ� NAME�� ���ĵ� ����				Ÿ���� �����ش�. => ã�⵵ ����, ������ ������ ������
			childContainer.setData(data);
			rootContainer.addChild(childContainer);
			//////////////////////////////////
			//for(List data1 : data){
			//for���� �Ⱦ��� �ϳ��� childContainer�� �Ѱ��� littleContainer�� ����
			//�̺κ��� �˰��� ������ �� �����غ����մϴ�.
			
			/* �� ���� : View���� ó��
			 * DB select �޾ƿ� data�� id�� �̿��ؼ� �������� �����͵��� list �迭�� ����
			 *  for(������Ÿ�� �������̸� : list�̸�) ���� �ؼ� �ݺ�
			 */
				littleContainer = new ContainerTreeItem(childContainer, data.INTERN_NAME+"("+data.INTERN_ID+")", ContainerTreeItemIdDefine.CONTAINER_LITTLECHILD);
				littleContainer.setData(data);
				childContainer.addChild(littleContainer);
				
			//}
		}
		
//		treeViewer.refresh();
		//�̰� �����ָ� ȭ���� �״�� �Դϴ�.
		//treeViewer.expandAll();	// �� �������� ����? ���� ��� �ؿ� �� �ܰ���� ����?
//		treeViewer.expandToLevel(rootContainer, store.getInt("tree_level"));
		//expandToLevel�ϸ� �� ���������� ��ġ�ڴ�.
		//treeViewer.collapseToLevel(rootContainer, level);
		// Ʈ����� �ݱ� 
	}
	
	public void expandTreeViewer(){
		treeViewer.refresh();
		if(store.getInt("tree_level")!=0){
			treeViewer.collapseAll();
			treeViewer.expandToLevel(rootContainer, store.getInt("tree_level"));
		}else if(store.getInt("tree_level")==0){
			treeViewer.collapseAll();
		}
		
	}
	
	

	private void connectionSoha() {
		// SOHA DB ���� - ����
		/*
		siteInfo = new SiteInformation();
		siteInfo.title 			= "";
		siteInfo.ip 			= "192.168.1.177";
		siteInfo.port 			= "10002";
		siteInfo.serviceName 	= "cuwon10002";
		siteInfo.user 			= "dgadmin";
		siteInfo.password 		= "";
		siteInfo.charEncoding	= "utf8";*/
		
		siteInfo = new SiteInformation();
		siteInfo.title 			= "";
		siteInfo.ip 			= "sinsiway-jeju.iptime.org";
		siteInfo.port 			= "10002";
		siteInfo.serviceName 	= "cuwon10002";
		siteInfo.user 			= "dgadmin";
		siteInfo.password 		= "";
		siteInfo.charEncoding	= "utf8";

		String url = siteInfo.toJDBCString();
		DBInfo dbInfo = new DBInfo(DBDefine.PETRA_PRODUCT_MANAGER, url,siteInfo.user, siteInfo.password);
		PetraConnection petraConnection = new PetraConnection(dbInfo,siteInfo.title, siteInfo);
		
		ConnectionChangeEvent ce = new ConnectionChangeEvent(petraConnection, getClass(), TYPE.CONNECT);
		ConnectionManager.getInstance().fireConnectionChange(ce);

//		Soha ���̺� ����,��� �ε��� ���� sql ����
//		drop table intern;
//		create table intern(
//		    intern_id sb8,    
//		    intern_name schr(33),
//		    sex sb1,
//		    phone schr(33),
//		    speaking_level sb1,
//		    portfolio_flag sb1
//		)
//		perm '0001111111100'
//		extent(0,1,0);
//		create unique index intern_idx1 on intern(intern_id);
		
		//sb8�� Long���� 
	}
	
	

	public Table getTable1() {
		return table1;
	}

	public void setTable1(Table table1) {
		this.table1 = table1;
	}

	public void setFocus() {
		//tree.setFocus();
	}
}