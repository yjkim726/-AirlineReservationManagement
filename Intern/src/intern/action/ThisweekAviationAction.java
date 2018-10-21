package intern.action;

import intern.Activator;
import intern.define.CommandIdDefine;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

public class ThisweekAviationAction  extends Action{

	 private final IWorkbenchWindow window;

	    public ThisweekAviationAction(String text, IWorkbenchWindow window) {
	        super(text);
	        this.window = window;
	        // The id is used to refer to the action in a menu or toolbar
	        setId(CommandIdDefine.CMD_WEEK_AVIATION);
	        // Associate the action with a pre-defined command, to allow key bindings.
	        setActionDefinitionId(CommandIdDefine.CMD_WEEK_AVIATION);
	        setImageDescriptor(Activator.getImageDescriptor("/icons/aviation.png"));
	    }

	    public void run() {
	        MessageDialog.openInformation(window.getShell(), "������ �װ���", "������ �װ���� �ۡ��Դϴ�");
	        //��� ��ġ���� ����� ���� 
	        // EX) ū ����=> ���� ��â => �� ���� �޼���â->�� ���� �˾� 
	        //openInformation�� ����ǥ(shell, ����, ����)
	        //���� �������� ��� �� Ȯ�� ������ ����ϱ� ����
	    }
	}
