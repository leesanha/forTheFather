package prace.file;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuAndFileDialog extends JFrame{
	Container contentPane;
	JLabel imageLabel = new JLabel();
	
	MenuAndFileDialog(){
		setTitle("Menu와 JFileChooser활용 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		contentPane.add(imageLabel);
		
		createMenu();
		
		setSize(300,200);
		setVisible(true);
	}
	
	void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openitem = new JMenuItem("Open");
		
		//Open 메뉴아이템에 Action 리스너를 등록한다.
		openitem.addActionListener(new OpenActionListener());
		fileMenu.add(openitem);
		mb.add(fileMenu);
		this.setJMenuBar(mb);
	}
	
	class OpenActionListener implements ActionListener{
		JFileChooser chooser;
		
		public OpenActionListener() {
			chooser = new JFileChooser();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter= new FileNameExtensionFilter("excel", "xls","xlsx");
			chooser.setFileFilter(filter);
			
			//파일 다이얼로그 출력
			int ret = chooser.showOpenDialog(null);
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
				
				return;
			}
			//사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
			String filePath = chooser.getSelectedFile().getPath(); //파일 경로명을 알아온다.
			imageLabel.setIcon(new ImageIcon(filePath));//파일을 로딩하여 이미지 레이블에 출력한다.
			pack();//이미지의 크기에 맞추어 프레임의 크기 조절
		}
	}
}
