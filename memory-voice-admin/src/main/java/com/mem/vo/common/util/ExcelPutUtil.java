package com.mem.vo.common.util;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelPutUtil {
    public static void createExcel(Map<String, List<String>> map, String[] strArray) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(20);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCell cell = null;
        int i;
        for (i = 0; i < strArray.length; i++) {
            cell = row.createCell((short)i);
            cell.setCellValue(strArray[i]);
            cell.setCellStyle(style);
        }
        i = 0;
        for (String str : map.keySet()) {
            row = sheet.createRow(i + 1);
            List<String> list = map.get(str);
            for (int j = 0; j < strArray.length; j++)
                row.createCell((short)j).setCellValue(list.get(j));
            try {
                FileOutputStream fout = new FileOutputStream("D:/Members.xls");
                wb.write(fout);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
