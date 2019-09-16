package spreadsheet.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

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
//		System.out.println(sheet.getLastRowNum());
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			list.add(readCellData(sheet.getRow(i)));
		}
//		System.out.println(list);
		return list;
	}

	private HashMap<String, String> readCellData(HSSFRow row) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int maxNum = row.getLastCellNum();
//		System.out.println(maxNum);
		for (int i = 0; i < maxNum; i++) {
			hMap.put("attr" + i, getStringCellData(row.getCell(i)));
		}
		return hMap;
	}

	private String getStringCellData(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat();
		HSSFFormulaEvaluator evaluator = new HSSFWorkbook().getCreationHelper().createFormulaEvaluator();
		
		if (cell != null) {
			String data = null;
//			System.out.println(cell.getCellType());
			switch (cell.getCellType()) {
			case BOOLEAN:
				boolean bdata = cell.getBooleanCellValue();
				data = String.valueOf(bdata);
				break;
			case NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					data = formatter.format(cell.getDateCellValue());
				} else {
					double ddata = cell.getNumericCellValue();
					data = df.format(ddata);
				}
				break;
			case STRING:
				data = cell.toString();
				break;
			case BLANK:
			case ERROR:
			case FORMULA:
				if (!(cell.toString() == "")) {
//					if (evaluator.evaluateFormulaCell(cell) == CellType.NUMERIC) {

						double fddata = cell.getNumericCellValue();
						data = df.format(fddata);
//					} else if (evaluator.evaluateFormulaCell(cell) ==

//							CellType.STRING) {
//						data = cell.getStringCellValue();
//					} else if (evaluator.evaluateFormulaCell(cell) ==

//							CellType.BOOLEAN) {
//						boolean fbdata = cell.getBooleanCellValue();
//						data = String.valueOf(fbdata);
//					}
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