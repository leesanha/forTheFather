package spreadsheet.menubar.service;


import java.io.File;
import java.util.HashMap;
import java.util.List;

import spreadsheet.util.ExcelManagerXLSX;
import spreadsheet.util.ExcelManagerXLS;

/**
 * <pre>
* 메뉴바의 리스너와 연동하여 파일을 파싱하는 클래스
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
public class MenuBarService {
	public static List<HashMap<String, String>> fileLoader(File file) {
		String[] temp = file.getName().split("\\.");
		Object manager;
		if(temp[temp.length-1].equalsIgnoreCase("xls")) {
			 manager = ExcelManagerXLS.getInstance();
		}else {
			manager = ExcelManagerXLSX.getInstance();
		}
		
		List<HashMap<String, String>> list = null;
		try {
			if(temp[temp.length-1].equalsIgnoreCase("xls")) {
				list = ((ExcelManagerXLS) manager).getListExcel(file);
			}else {
				list = ((ExcelManagerXLSX) manager).getListExcel(file);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}