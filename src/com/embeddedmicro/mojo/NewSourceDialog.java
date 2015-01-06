package com.embeddedmicro.mojo;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class NewSourceDialog extends Dialog {

	protected SourceFile result;
	protected Shell shell;
	private Text text;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public NewSourceDialog(Shell parent, int style) {
		super(parent, style);
		setText("New File...");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public SourceFile open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(615, 472);
		shell.setText(getText());
		shell.setLayout(new GridLayout(4, false));
		
		Label lblFileName = new Label(shell, SWT.NONE);
		lblFileName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFileName.setText("File name:");
		
		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblSelectAType = new Label(shell, SWT.NONE);
		lblSelectAType.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblSelectAType.setText("Select the type of file to add to your project.");
		
		final Button btnVerilogSourceFile = new Button(shell, SWT.RADIO);
		btnVerilogSourceFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text.getText();
				String newName;
				if (name.endsWith(".ucf"))
					newName = name.substring(0, name.length()-4)+".v";
				else if (name.endsWith(".v"))
					newName = name;
				else
					newName = name + ".v";
				text.setText(newName);
				
			}
		});
		btnVerilogSourceFile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnVerilogSourceFile.setText("Verilog Source File");
		
		
		final Button btnUcfConstraintsFile = new Button(shell, SWT.RADIO);
		btnUcfConstraintsFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = text.getText();
				String newName;
				if (name.endsWith(".v"))
					newName = name.substring(0, name.length()-2)+".ucf";
				else if (name.endsWith(".ucf"))
					newName = name;
				else
					newName = name + ".ucf";
				text.setText(newName);
			}
		});
		btnUcfConstraintsFile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnUcfConstraintsFile.setText("UCF Constraints File");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = null;
				shell.close();
			}
		});
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 100;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		
		Button btnCreateFile = new Button(shell, SWT.NONE);
		btnCreateFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = new SourceFile();
				result.fileName = text.getText();
				if (btnVerilogSourceFile.getSelection()){
					result.type = SourceFile.SOURCE;
					if (!result.fileName.endsWith(".v")){
						MessageBox box = new MessageBox(shell,SWT.OK);
						box.setText("Invalid Options");
						box.setMessage("Verilog file names must end with \".v\".");
						box.open();
						return;
					}
				} else if (btnUcfConstraintsFile.getSelection()){
					result.type = SourceFile.CONSTRAINT;
					if (!result.fileName.endsWith(".ucf")){
						MessageBox box = new MessageBox(shell,SWT.OK);
						box.setText("Invalid Options");
						box.setMessage("Constraint file names must end with \".ucf\".");
						box.open();
						return;
					}
				} else {
					result = null;
					MessageBox box = new MessageBox(shell,SWT.OK);
					box.setText("Invalid Options");
					box.setMessage("You must select the file type.");
					box.open();
					return;
				}
				shell.close();
			}
		});
		GridData gd_btnCreateFile = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCreateFile.widthHint = 100;
		btnCreateFile.setLayoutData(gd_btnCreateFile);
		btnCreateFile.setText("Create File");

		shell.pack();
	}

}
