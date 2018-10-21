package intern.preferences;

import java.util.ArrayList;
import java.util.List;

import intern.BookAFlightView;
import intern.NavigationView;
import intern.View;
import intern.util.FrequentlyUsedMethodsUtil;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.sinsiway.petra.commons.log.PetraLogger;
import com.sinsiway.petra.commons.ui.util.PetraGridData;
import com.sinsiway.petra.commons.ui.util.PetraGridLayout;

public class FontSetting extends PreferencePage implements IWorkbenchPreferencePage{

	public static String ID = "intern.preferences.FontSetting";
	private IPreferenceStore iPreferenceStore = PlatformUI.getPreferenceStore();
	private Label label;
	private Combo fontSizeCombo, fontColorCombo ;
	private String[] fontList = {"1","3","5","7","9","11","13","15","17"};
	private String[] colorList = {"BLACK","BLUE","GREEN","RED"};
	private Logger logger = PetraLogger.getLogger(View.class);
	List<IViewPart> listView = new ArrayList<IViewPart>();
	
	//	private IPreferenceStore iPreferenceStore = PlatformUI.getPreferenceStore(); - ��ܿ� ����
	//	iPreferenceStore.setDefault("TREE_LEVEL", 1);   - �⺻�� ����
	//	iPreferenceStore.setValue("TREE_LEVEL", value);   - �� ����
	//	iPreferenceStore.getInt("TREE_LEVEL");       - �� ��������
	
	
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(iPreferenceStore);
	}

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		Composite TopComposite = new Composite(parent, SWT.NONE);
		PetraGridLayout.defaults().spacing(20,20).applyTo(TopComposite);
		PetraGridData.defaults().grab(true, false).applyTo(TopComposite);
		
		Composite fontSizeComposite = new Composite(TopComposite, SWT.NONE);
		PetraGridLayout.defaults().spacing(20,20).applyTo(fontSizeComposite);
		PetraGridData.defaults().grab(true, false).applyTo(fontSizeComposite);
		
		
		label = new Label(fontSizeComposite, SWT.NONE);
		label.setText("�ؽ�Ʈ �۾� ũ�� ����");
		
		fontSizeCombo = new Combo(fontSizeComposite, SWT.NONE);
		fontSizeCombo.setItems(fontList);
		
		Composite fontColorComposite = new Composite(TopComposite, SWT.NONE);
		PetraGridLayout.defaults().spacing(20,20).applyTo(fontColorComposite);
		PetraGridData.defaults().grab(true, false).applyTo(fontColorComposite);
		
		label = new Label(fontColorComposite, SWT.NONE);
		label.setText("�ؽ�Ʈ ���� ����");
		
		fontColorCombo = new Combo(fontColorComposite, SWT.NONE);
		fontColorCombo.setItems(colorList);
		
		
		initialize();
		
		return null;
	}
	
	//�ʱ�ȭ �޼ҵ�
	private void initialize() {

		fontSizeCombo.select((iPreferenceStore.getInt("text_font_size")-1)/2);
										//���� �˾� �� �� �ֵ��� ����
		fontColorCombo.select(iPreferenceStore.getInt("text_color_size"));
		//ó���� �� �� ������ �Ǿ� �־����.
	}
	
	@Override
	public boolean performOk(){
		if(this.isControlCreated()){
			iPreferenceStore.setValue("text_font_size", 2*(fontSizeCombo.getSelectionIndex())+1);
			iPreferenceStore.setValue("text_color_size", fontColorCombo.getSelectionIndex());
			logger.info("logger OK : "+iPreferenceStore.getInt("text_color_size"));
			
			
			List<ViewPart> listView = new ArrayList<ViewPart>();
				
			/*listView = (List<ViewPart>) FrequentlyUsedMethodsUtil.getViews(View.ID);
			
			for(ViewPart viewPart : listView){
				if(listView!=null){ 
					View view = (View) viewPart;
					view.setFont();
					view.setColor(iPreferenceStore.getInt("text_color_size"));
				}else {  }
			}*/
			
			listView = (List<ViewPart>) FrequentlyUsedMethodsUtil.getViews(BookAFlightView.ID);
			for(ViewPart viewPart : listView){
				if(listView!=null){ 
					BookAFlightView bookAFlightView = (BookAFlightView) viewPart;
					bookAFlightView.setFont();
					bookAFlightView.setColor(iPreferenceStore.getInt("text_color_size"));
				}else {  }
			}
			
			NavigationView navigationView = (NavigationView) FrequentlyUsedMethodsUtil.getView(NavigationView.ID);
			BookAFlightView view = (BookAFlightView) FrequentlyUsedMethodsUtil.getView(BookAFlightView.ID);
			//navigationView.expandTreeViewer();
			navigationView.getTable1().select(1);
			view.getPlaceStackLayout().topControl = view.getPlaceStackComposite2();
			view.getPlaceStackComposite().layout();
			view.getCheckInSashForm().setWeights(new int[]{1,0});
			view.getConsumerBooking();
			view.getConsumerChecking();
			
		}
		return super.performOk();		
	}
	
	@Override
	protected void performDefaults(){

//		View view =(View) FrequentlyUsedMethodsUtil.getViews("Intern.view");
//		
//		iPreferenceStore.setDefault("text_font_size", 15);
//		iPreferenceStore.setDefault("text_color_size", 0);
//		logger.info("logger ??? : "+iPreferenceStore.getDefaultInt("text_color_size"));
//		fontSizeCombo.select((iPreferenceStore.getDefaultInt("text_font_size")-1)/2);
//					//Default������ setValue������ ���� �ٸ���!
//		fontColorCombo.select(iPreferenceStore.getDefaultInt("text_font_size"));
//		
//		if(view==null){ }
//		else{
//			view.setDefaultFont();
//			view.setColor(iPreferenceStore.getDefaultInt("text_color_size"));
//		}
//		
//		super.performDefaults();
		
		List<ViewPart> listView = new ArrayList<ViewPart>();
		listView = (List<ViewPart>) FrequentlyUsedMethodsUtil.getViews(View.ID);
		iPreferenceStore.setDefault("text_font_size", 15);
		iPreferenceStore.setDefault("text_color_size", 0);
		logger.info("logger ??? : "+iPreferenceStore.getDefaultInt("text_color_size"));
		fontSizeCombo.select((iPreferenceStore.getDefaultInt("text_font_size")-1)/2);
					//Default������ setValue������ ���� �ٸ���!
		fontColorCombo.select(iPreferenceStore.getDefaultInt("text_font_size"));
		
		for(ViewPart viewPart : listView){
			if(listView!=null){ 
				View view = (View) viewPart;
				view.setFont();
				view.setColor(iPreferenceStore.getInt("text_color_size"));
			}else {  }
		}
		
		super.performDefaults();
	}
}
