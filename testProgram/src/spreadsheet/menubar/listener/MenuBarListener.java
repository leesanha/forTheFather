package spreadsheet.menubar.listener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import spreadsheet.algo.BinPack;
import spreadsheet.menubar.service.MenuBarService;
import spreadsheet.view.SpreadStage;

/**
 * <pre>
* 메뉴바의 이벤트를 처리하는 리스너 클래스
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
public class MenuBarListener implements ActionListener {

	private SpreadStage stage;
	private JFileChooser jfc;
	static int list_size;
	static double targetSize;

	Container contentPane;
	public JTable table;

	public MenuBarListener(SpreadStage stage) {
		this.stage = stage;
		this.stage.mOpen.addActionListener(this);
		this.stage.mExit.addActionListener(this);
		this.stage.mCutting.addActionListener(this);
		jfc = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == stage.mOpen) {
// 파일 필터
			jfc.setFileFilter(new FileNameExtensionFilter("excel[xls형식만지원]", "xls", "xlsx"));
// 멀티 셀렉트
// jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(stage) == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				
				List<HashMap<String, String>> list = MenuBarService.fileLoader(file);

				list_size = list.size();
				for (int i = 0; i < list.size() - 7; i++) {
					stage.data[i][1] = list.get(i + 5).get("attr" + 7);
					stage.data[i][2] = list.get(i + 5).get("attr" + 9);
				}
				stage.repaint();
			}
		} else if (e.getSource() == stage.mExit) {
			System.exit(0);
		} else if (e.getSource() == stage.mCutting) {// 자르기 메뉴 실행시
			targetSize = Double.parseDouble(JOptionPane.showInputDialog("자재 길이 입력(단위: M)"));
			BinPack bp = new BinPack();
			ArrayList<Double>[] bins = bp.doProcess(stage.data, list_size - 7, (int)(targetSize * 1000));

			int cuttedCnt = 0;
			int binLen = 0;
			for (int i = 0; i < bins.length; i++) {
				if (bins[i] == null)
					break;
				cuttedCnt += bins[i].size();
				binLen++;
			}

			String[][] data = new String[cuttedCnt][2];
			int cnt = 0;

			for (int i = 0; i < binLen; i++) {
				for (int j = 0; j < bins[i].size(); j++) {
					if (j == 0)
						data[cnt][0] = (i + 1) + "번 남는 길이";
					if (j == 1)
						data[cnt][0] = "자를 길이";
					if (j >= 2)
						data[cnt][0] = "";
					data[cnt][1] = bins[i].get(j) + "";
					cnt++;
				}
			}

			// 엑셀 파일로 저장
			JFrame jf = new JFrame("결과");
			jf.setSize(800, 600);
			jf.setLocation(500, 300);
			contentPane = jf.getContentPane();

			String[] colNames = { "No.", "규격(L=mm)" };

			table = new JTable(data, colNames);
			table.setRowHeight(20);
			JScrollPane scrollPane = new JScrollPane(table);
			contentPane.add(scrollPane, BorderLayout.CENTER);
			
			jf.setVisible(true);
		}
	}
}