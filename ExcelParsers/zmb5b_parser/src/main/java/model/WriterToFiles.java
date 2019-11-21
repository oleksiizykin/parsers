package model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;
import java.util.Queue;

public class WriterToFiles {
    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private CellStyle dateStyle;
    private XSSFRow row;

    public void writeToFiles(Map<String, Item> itemMap, NonameMaterial nonameMaterial, String path, String fileName) throws IOException {
        book = new XSSFWorkbook();
        sheet = book.createSheet("Data");

        dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(book.createDataFormat().getFormat("dd.mm.yyyy"));

        row = sheet.createRow(0);
        row.createCell(0).setCellValue("SLoc");
        row.createCell(1).setCellValue("MvT");
        row.createCell(2).setCellValue("Plant");
        row.createCell(3).setCellValue("SAP code");
        row.createCell(4).setCellValue("SAP name");
        row.createCell(5).setCellValue("S");
        row.createCell(6).setCellValue("Mat. Doc.");
        row.createCell(7).setCellValue("Item");
        row.createCell(8).setCellValue("DocumentNo");
        row.createCell(9).setCellValue("Pstng Date");
        row.createCell(10).setCellValue("Quantity");
        row.createCell(11).setCellValue("BUn");
        row.createCell(12).setCellValue("LC2 amount");
        row.createCell(13).setCellValue("Batch");
        row.createCell(14).setCellValue("Vendor");
        row.createCell(15).setCellValue("Vendor Description");
        row.createCell(16).setCellValue("PO");
        row.createCell(17).setCellValue("Text");

        int rowId = 1;
        for (Map.Entry<String, Item> itemOfMap : itemMap.entrySet()) {
            Item item1 = itemOfMap.getValue();
            Queue<RowItem> rowItems = item1.getRowItemList();
            for (RowItem rowItem : rowItems) {
                row = sheet.createRow(rowId++);
                row.createCell(0).setCellValue(rowItem.getSloc());

                row.createCell(1).setCellValue(!rowItem.getMvt().equals("0") ? rowItem.getMvt() : null);

                row.createCell(2).setCellValue(item1.getPlant());
                row.createCell(3).setCellValue(item1.getMaterial());
                row.createCell(4).setCellValue(item1.getNameOfMaterial());
                row.createCell(5).setCellValue(rowItem.getS());

                if (rowItem.getMatDoc() != 0) {
                    row.createCell(6).setCellValue(rowItem.getMatDoc());
                }
                if (rowItem.getItem() != 0) {
                    row.createCell(7).setCellValue(rowItem.getItem());
                }
                if (rowItem.getDocumentNumber() != 0) {
                    row.createCell(8).setCellValue(rowItem.getDocumentNumber());
                }

                Cell dataCell = row.createCell(9);
                LocalDate date = rowItem.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.set(
                        date.getYear(),
                        date.getMonthValue() - 1,
                        date.getDayOfMonth()
                );
                dataCell.setCellStyle(dateStyle);
                double d = DateUtil.getExcelDate(calendar, false);
                dataCell.setCellValue((int)d);

                row.createCell(10).setCellValue(rowItem.getQuantity());
                row.createCell(11).setCellValue(rowItem.getUom());
                row.createCell(12).setCellValue(rowItem.getAmount());
                row.createCell(13).setCellValue(rowItem.getBatch());
                row.createCell(14).setCellValue(rowItem.getVendor());
                row.createCell(15).setCellValue(rowItem.getVendorName());
                row.createCell(16).setCellValue(rowItem.getPo());
                row.createCell(17).setCellValue(rowItem.getText());
            }
        }
        FileOutputStream os = new FileOutputStream(new File(path + "\\" + fileName));
        System.out.println(fileName.substring(6) + " was parsed successfully");
        book.write(os);
        os.close();

        if (nonameMaterial.getNonameMaterials().size() != 0) {
            try (BufferedWriter bufferedWriter =
                         new BufferedWriter(new FileWriter(path + "\\" + fileName.replaceAll(".xlsx", "")
                                 + "_nonameMaterials.txt"))) {
                System.out.println("Find file with no name materials");
                for (String s : nonameMaterial.getNonameMaterials()) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                }
            }
        }
    }
}
