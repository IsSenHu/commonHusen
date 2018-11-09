//package com.husen.utils.poi;
//
//import com.husen.vo.common.CommonMessageVo;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.streaming.SXSSFCell;
//import org.apache.poi.xssf.streaming.SXSSFRow;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//import org.apache.poi.xssf.usermodel.*;
//import java.awt.Color;
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.*;
//import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
///**
// * Created by HuSen on 2018/11/7 15:45.
// */
//public class PoiUtils implements ExcelHelper {
//    private static final int MAX_ROWS = 1048576;
//
//    @Override
//    public void create(List<ExcelVo> data) {
//        int totals = data.size();
//        int size = MAX_ROWS - 2;
//        int totalPages = totals % size == 0 ? (totals / size) : (totals / size) + 1;
//        int page = 1;
//        List<InputStream> files = new ArrayList<>(1);
//        while (page <= totalPages) {
//            List<ExcelVo> excelVos = data.subList((page - 1) * size, page * size < totals ? (page * size) : totals);
//            InputStream inputStream = write(excelVos);
//            if(null != inputStream) {
//                files.add(inputStream);
//            }
//            page++;
//        }
//        byte[] buffer = new byte[1024];
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File("C:\\Users\\HS\\Desktop\\桌面壁纸\\excel.zip")))) {
//            for (InputStream inputStream : files) {
//                zipOutputStream.putNextEntry(new ZipEntry(UUID.randomUUID().toString() + ".xlsx"));
//                int len;
//                while ((len = inputStream.read(buffer)) != -1) {
//                    zipOutputStream.write(buffer, 0, len);
//                }
//                inputStream.close();
//            }
//            zipOutputStream.finish();
//            zipOutputStream.closeEntry();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private InputStream write(List<ExcelVo> data) {
//        if(CollectionUtils.isNotEmpty(data)) {
//            List<String> colNames = data.get(0).colNames();
//            int colNumbers = colNames.size();
//            SXSSFWorkbook workbook = new SXSSFWorkbook();
//            SXSSFSheet sheet = workbook.createSheet(UUID.randomUUID().toString());
//
//            // 产生标题行
//            SXSSFRow row = sheet.createRow(0);
//            SXSSFCell cellTitle = row.createCell(0);
//
//            // sheet样式定义
//            CellStyle columnTopStyle = getColumnTopStyle(workbook);
//            CellStyle titleStyle = getTitleStyle(workbook);
//            CellStyle valueStyle = getValueStyle(workbook);
//
//
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (colNumbers - 1)));
//            cellTitle.setCellStyle(columnTopStyle);
//            cellTitle.setCellValue("测试Excel导出");
//
//            // 定义第二行为列名行
//            SXSSFRow sheetRow = sheet.createRow(1);
//            for(int i = 0; i < colNumbers; i++) {
//                SXSSFCell cell = sheetRow.createCell(i);
//                cell.setCellType(CellType.STRING);
//                XSSFRichTextString text = new XSSFRichTextString(colNames.get(i));
//                cell.setCellValue(text);
//                cell.setCellStyle(titleStyle);
//            }
//
//            // 将查询出的数据设置到sheet对应的单元格中
//            int n = 0;
//            for(ExcelVo excelVo : data) {
//                SXSSFRow sxssfRow = sheet.createRow(n + 2);
//                List<Object> values = excelVo.cellValues();
//                int size = values.size();
//                for(int i = 0; i < size; i++) {
//                    SXSSFCell sxssfCell;
//                    if(i == 0) {
//                        sxssfCell = sxssfRow.createCell(i, CellType.STRING);
//                        sxssfCell.setCellStyle(valueStyle);
//                        sxssfCell.setCellValue(values.get(i).toString());
//                    }else if(i == 1) {
//                        sxssfCell = sxssfRow.createCell(i, CellType.STRING);
//                        sxssfCell.setCellStyle(valueStyle);
//                        sxssfCell.setCellValue(values.get(i).toString());
//                    }
//                }
//                n++;
//            }
//            try {
//                String fileName = "Test-Excel" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".xlsx";
//                OutputStream outputStream = new ByteArrayOutputStream();
//                workbook.write(outputStream);
//                return parseInputStream(outputStream);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//    public static void main(String[] args) {
//        List<ExcelVo> excelVos = new ArrayList<>();
//        for(int i = 0; i < 10000000; i++) {
//            CommonMessageVo messageVo = new CommonMessageVo();
//            messageVo.setId(1L);
//            messageVo.setMessage("你好世界");
//            excelVos.add(messageVo);
//        }
//
//        new PoiUtils().create(excelVos);
//    }
//
//    private static CellStyle getColumnTopStyle(SXSSFWorkbook workbook) {
//        // 设置字体
//        Font font = workbook.createFont();
//        // 设置字体大小
//        font.setFontHeightInPoints((short) 11);
//        // 字体加粗
//        font.setBold(true);
//        // 设置字体名字
//        font.setFontName("Courier New");
//        // 设置样式
//        CellStyle cellStyle = workbook.createCellStyle();
//        // 设置底边框
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        // 设置底边框颜色
//        cellStyle.setBottomBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.RED, new DefaultIndexedColorMap())).getIndex());
//        // 设置左边框
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        // 设置左边框颜色
//        cellStyle.setLeftBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLUE, new DefaultIndexedColorMap())).getIndex());
//        // 设置右边框
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        // 设置右边框颜色
//        cellStyle.setRightBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLUE, new DefaultIndexedColorMap())).getIndex());
//        // 设置顶边框
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        // 设置顶边框颜色
//        cellStyle.setTopBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.RED, new DefaultIndexedColorMap())).getIndex());
//        // 在样式中应用设置的字体
//        cellStyle.setFont(font);
//        // 设置自动换行
//        cellStyle.setWrapText(false);
//        // 设置垂直对齐的样式为居中对齐
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        return cellStyle;
//    }
//
//    private static CellStyle getTitleStyle(SXSSFWorkbook workbook) {
//        // 设置字体
//        Font font = workbook.createFont();
//        // 设置字体大小
//        font.setFontHeightInPoints((short) 10);
//        // 字体加粗
//        font.setBold(true);
//        // 设置字体名字
//        font.setFontName("Courier New");
//        // 设置样式
//        CellStyle cellStyle = workbook.createCellStyle();
//        // 设置底边框
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        // 设置底边框颜色
//        cellStyle.setBottomBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 设置左边框
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        // 设置左边框颜色
//        cellStyle.setLeftBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 设置右边框
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        // 设置右边框颜色
//        cellStyle.setRightBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 设置顶边框
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        // 设置顶边框颜色
//        cellStyle.setTopBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 在样式中应用设置的字体
//        cellStyle.setFont(font);
//        // 设置自动换行
//        cellStyle.setWrapText(false);
//        // 设置垂直对齐的样式为居中对齐
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        return cellStyle;
//    }
//
//    private static CellStyle getValueStyle(SXSSFWorkbook workbook) {
//        // 设置字体
//        Font font = workbook.createFont();
//        // 设置字体大小
//        font.setFontHeightInPoints((short) 10);
//        // 字体加粗
//        font.setBold(false);
//        // 设置字体名字
//        font.setFontName("Courier New");
//        // 设置样式
//        CellStyle cellStyle = workbook.createCellStyle();
//        // 设置底边框
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        // 设置底边框颜色
//        cellStyle.setBottomBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 设置左边框
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        // 设置左边框颜色
//        cellStyle.setLeftBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 设置右边框
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        // 设置右边框颜色
//        cellStyle.setRightBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 设置顶边框
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        // 设置顶边框颜色
//        cellStyle.setTopBorderColor(XSSFColor.toXSSFColor(new XSSFColor(Color.BLACK, new DefaultIndexedColorMap())).getIndex());
//        // 在样式中应用设置的字体
//        cellStyle.setFont(font);
//        // 设置自动换行
//        cellStyle.setWrapText(false);
//        // 设置垂直对齐的样式为居中对齐
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
//        return cellStyle;
//    }
//
//    //String转inputStream
//    private static ByteArrayInputStream parseInputStream(OutputStream out) {
//        ByteArrayOutputStream  byteArrayOutputStream = (ByteArrayOutputStream) out;
//        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//    }
//}
