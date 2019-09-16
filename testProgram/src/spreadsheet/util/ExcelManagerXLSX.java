package spreadsheet.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
public class ExcelManagerXLSX {

	private static ExcelManagerXLSX excelMng;

	public ExcelManagerXLSX() {
// TODO Auto-generated constructor stub
	}

	public static ExcelManagerXLSX getInstance() {
		if (excelMng == null)
			excelMng = new ExcelManagerXLSX();
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
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		
//엑셀파일의 시트 존재 유무 확인
		if (workbook.getNumberOfSheets() < 1) {
			System.out.println("시트가 없음");
			return null;
		}

//첫번째 시트를 읽음
		XSSFSheet sheet = workbook.getSheetAt(0);
		System.out.println(sheet.getFirstRowNum());
		System.out.println(sheet.getLastRowNum());
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			list.add(readCellData(sheet.getRow(i)));
		}
		return list;
	}

	private HashMap<String, String> readCellData(XSSFRow xssfRow) {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int maxNum = xssfRow.getLastCellNum();
		for (int i = 0; i < maxNum; i++) {
			hMap.put("attr" + i, getStringCellData(xssfRow.getCell(i)));
		}
		return hMap;
	}

	private String getStringCellData(XSSFCell xssfCell) {
		DecimalFormat df = new DecimalFormat();
		XSSFFormulaEvaluator evaluator = new XSSFWorkbook().getCreationHelper().createFormulaEvaluator();
		if (xssfCell != null) {
			String data = null;
			switch (xssfCell.getCellType()) {
			case BOOLEAN:
				boolean bdata = xssfCell.getBooleanCellValue();
				data = String.valueOf(bdata);
				break;
			case NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					data = formatter.format(xssfCell.getDateCellValue());
				} else {
					double ddata = xssfCell.getNumericCellValue();
					data = df.format(ddata);
				}
				break;
			case STRING:
				data = xssfCell.toString();
				break;
			case BLANK:
			case ERROR:
			case FORMULA:
				if (!(xssfCell.toString() == "")) {
//					if (evaluator.evaluateFormulaCell(xssfCell) == CellType.NUMERIC) {

						double fddata = xssfCell.getNumericCellValue();
						data = df.format(fddata);
//					} else if (evaluator.evaluateFormulaCell(xssfCell) ==

//							CellType.STRING) {
//						data = xssfCell.getStringCellValue();
//					} else if (evaluator.evaluateFormulaCell(xssfCell) ==

//							CellType.BOOLEAN) {
//						boolean fbdata = xssfCell.getBooleanCellValue();
//						data = String.valueOf(fbdata);
//					}
					break;
				}
			default:
				data = xssfCell.toString();
			}
			return data;
		} else {
			return null;
		}
	}
}