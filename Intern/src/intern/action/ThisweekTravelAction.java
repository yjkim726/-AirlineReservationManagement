package intern.action;

import intern.Activator;
import intern.define.CommandIdDefine;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class ThisweekTravelAction  extends Action{
	 private final IWorkbenchWindow window;

	    public ThisweekTravelAction(String text, IWorkbenchWindow window) {
	        super(text);
	        this.window = window;
	        // The id is used to refer to the action in a menu or toolbar
	        setId(CommandIdDefine.CMD_WEEK_TRAVEL);
	        // Associate the action with a pre-defined command, to allow key bindings.
	        setActionDefinitionId(CommandIdDefine.CMD_WEEK_TRAVEL);
	        setImageDescriptor(Activator.getImageDescriptor("/icons/travel.png"));
	    }

	    public void run() {
	        MessageDialog.openInformation(window.getShell(), "������ ������", "������ �������� �ۡ��Դϴ�");
	        //��� ��ġ���� ����� ���� 
	        // EX) ū ����=> ���� ��â => �� ���� �޼���â->�� ���� �˾� 
	        //openInformation�� ����ǥ(shell, ����, ����)
	        //���� �������� ��� �� Ȯ�� ������ ����ϱ� ����
	    }
	}