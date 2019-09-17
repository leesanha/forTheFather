package spreadsheet.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <pre>
* 엑셀파일을 파싱함
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
public class ExcelManagerXLS {

	private static ExcelManagerXLS excelMng;

	public ExcelManagerXLS() {
// TODO Auto-generated constructor stub
	}

	public static ExcelManagerXLS getInstance() {
		if (excelMng == null)
			excelMng = new ExcelManagerXLS();
		return excelMng;
	}

	/**
	 * 엑셀파일 파싱후 HashMap리스트를 반환
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getListExcel(File file) throws Exception {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
//엑셀파일의 시트 존재 유무 확인
		if (workbook.getNumberOfSheets() < 1)
			return null;

//첫번째 시트를 읽음
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			list.add(readCellData(sheet.getRow(i)));
		}
		return list;
	}

	private HashMap<String, String> readCellData(HSSFRow row) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int maxNum = row.getLastCellNum();
		for (int i = 0; i < maxNum; i++) {
			hMap.put("attr" + i, getStringCellData(row.getCell((short) i)));
		}
		return hMap;
	}

	private String getStringCellData(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat();
		/*
		 * HSSFCell.CELL_TYPE_BOOLEAN => 이렇게 사용
		 * BOOLEAN => 이렇게 사용
		*/		
		if (cell != null) {
			String data = null;
			switch (cell.getCellType()) {
			case BOOLEAN:
				boolean bdata = cell.getBooleanCellValue();
				data = String.valueOf(bdata);
				break;
			case NUMERIC:
				double ddata = cell.getNumericCellValue();
				data = df.format(ddata);
				break;
			case STRING:
				data = cell.toString();
				break;
			case BLANK:
			case ERROR:
			case FORMULA:
				if (!(cell.toString() == "")) {
					double fddata = cell.getNumericCellValue();
					data = df.format(fddata);
					break;
				}
			default:
				data = cell.toString();
			}
			return data;
		} else {
			return null;
		}
	}
}