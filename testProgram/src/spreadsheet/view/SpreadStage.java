package spreadsheet.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import spreadsheet.menubar.listener.MenuBarListener;

/**
 * <pre>
* Excel viewer Main Stage
 * </pre>
 *
 * <ul>
 * <li>version : 0.5</li>
 * <ul>
 *
 * @author 조한중[hanjoongcho@gmail.com]
 * @since 2012년 7월 30일
 *
 */
public class SpreadStage extends JFrame {

	public Container contentPane;
	public JTable table;

	public MenuBar mb;

	public Menu mFile;
	public Menu mHelp;
	public Menu mProcess;

	public MenuItem mNew;
	public MenuItem mOpen;
	public MenuItem mExit;
	public MenuItem mCutting;

	String[] colNames = { "", "규격(L=mm)", "수량(24set)"};
	public Object data[][];

	public SpreadStage(String title) {
		super(title);
		this.setSize(600, 450);
		this.setLocation(300, 100);
		initMenuBar();
		initBody();
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void initMenuBar() {
		mb = new MenuBar();

		mFile = new Menu("File");
		mHelp = new Menu("Help");
		mProcess = new Menu("Process");

		mNew = new MenuItem("New");
		mOpen = new MenuItem("Open");
		mExit = new MenuItem("Exit");
		mCutting = new MenuItem("Cutting");

		mFile.add(mNew);
		mFile.add(mOpen);
		mFile.addSeparator(); // 메뉴구분선
		mFile.add(mExit);
		
		mProcess.add(mCutting);

		mb.add(mFile);
		mb.setHelpMenu(mHelp);
		mb.add(mProcess);
		this.setMenuBar(mb);

		new MenuBarListener(this);

	}

	void initBody() {
		contentPane = this.getContentPane();

		data = new Object[10000][3];
// row번호설정
		for (int i = 0; i < 10000; i++) {
			data[i][0] = i + 1;
		}
		table = new JTable(data, colNames);
		table.setRowHeight(20);
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
}