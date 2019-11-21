package model;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Parser {

  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private Iterator<Row> rowIterator;
  private Item item;
  private Map<String, Item> itemMap = new TreeMap<>();
  private NonameMaterial nonameMaterial = new NonameMaterial();
  private String filePath;
  private int currentRow;
  public static final String PATTERN2 = "$#,##0.00;#-$,##0.00";

  public int getCurrentRow() {
    return currentRow;
  }

  public Parser(String filePath) {
    this.filePath = filePath;
  }

  public Map<String, Item> parse() throws IOException {
    FileInputStream is = new FileInputStream(new File(filePath));
    workbook = new XSSFWorkbook(is);
    sheet = workbook.getSheetAt(0);
    rowIterator = sheet.iterator();

    item = new Item();

    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      DataFormatter formatter = new DataFormatter();
      currentRow = row.getRowNum() + 1;

      if (formatter.formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
          .startsWith("Valuation")) {
        item = new Item();
        String valuation = formatter
            .formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
            .substring(17);
        item.setPlant(valuation);

        row = rowIterator.next();
        currentRow = row.getRowNum() + 1;
        String materialCode = formatter
            .formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
            .substring(17);
        item.setMaterial(materialCode);

        row = rowIterator.next();
        currentRow = row.getRowNum() + 1;

        String materialName = formatter
            .formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
        if (materialName.length() < 17) {
          nonameMaterial.add(item.getMaterial());
          item.setNameOfMaterial(null);
        } else {
          item.setNameOfMaterial(materialName.substring(17));
        }

        String itemKey = item.getPlant() + item.getMaterial();
        if (!itemMap.containsKey(itemKey)) {
          itemMap.put(itemKey, item);
        } else {
          item = itemMap.get(itemKey);
        }
      } else {
        if (formatter.formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
            .startsWith("Stock/value")) {
          RowItem rowItem = new RowItem();
          rowItem.setSloc(
              formatter.formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                  .substring(0, 11));

          String stringDate = formatter
              .formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
              .substring(15, 25);
          LocalDate localDate = LocalDate
              .parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
          rowItem.setDate(localDate);
          rowItem.setMvt(localDate.getDayOfMonth() == 1 ? "Opening" : "Closing");

          double quantity = Double.valueOf(
              formatter.formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                  .substring(26, 52).replaceAll(",", "").trim());

          rowItem.setQuantity(rowItem.getMvt() == "Closing" ? quantity * -1 : quantity);

          rowItem.setUom(
              formatter.formatCellValue(
                  row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                  .substring(52, 55));

          double amount = Double.valueOf(
              formatter.formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                  .substring(56, 84).replaceAll(",", "").trim());
          rowItem.setAmount(rowItem.getMvt() == "Closing" ? amount * -1 : amount);

          rowItem.setBatch(
              formatter.
                  formatCellValue(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                  .substring(86, 88));

          if (!item.getRowItemList().contains(rowItem)) {
            item.setRowItemList(rowItem);
          }
        } else {
          if (row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellTypeEnum()
              == CellType.NUMERIC) {
            RowItem rowItem = new RowItem();
            rowItem.setSloc(
                formatter
                    .formatCellValue(row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            rowItem.setMvt(String.valueOf(
                (int) row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                    .getNumericCellValue()));
            rowItem.setS(
                formatter
                    .formatCellValue(row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            rowItem.setMatDoc(
                row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
            rowItem.setItem(
                row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
            rowItem.setDocumentNumber(
                row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());

            Date date = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                .getDateCellValue();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            rowItem.setDate(localDate);

            rowItem.setQuantity(Double.valueOf(
                row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue()));
            rowItem.setUom(
                formatter
                    .formatCellValue(row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            rowItem.setAmount(Double.valueOf(
                row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue()));
            rowItem.setBatch(
                formatter
                    .formatCellValue(row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));

            rowItem.setVendor(
                formatter
                    .formatCellValue(row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            rowItem.setVendorName(
                formatter
                    .formatCellValue(row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            rowItem.setPo(
//                row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
            formatter.formatCellValue(row.getCell(14, MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            rowItem.setText(
                formatter
                    .formatCellValue(row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));
            item.setRowItemList(rowItem);
          }
        }
      }
    }
    is.close();
    return itemMap;
  }

  public NonameMaterial getNonameMaterial() {
    return nonameMaterial;
  }
}
