package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class RowItem {
    private String sloc;
    private String mvt;
    private String s;
    private double matDoc;
    private double Item;
    private double documentNumber;
    private LocalDate date;
    private double quantity;
    private String uom;
    private double amount;
    private String batch;
    private String vendor;
    private String vendorName;
    private String po;
    private String text;

    public RowItem() {
    }

    public String getSloc() {
        return sloc;
    }

    public void setSloc(String sloc) {
        this.sloc = sloc;
    }

    public String getMvt() {
        return mvt;
    }

    public void setMvt(String mvt) {
        this.mvt = mvt;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public double getMatDoc() {
        return matDoc;
    }

    public void setMatDoc(double matDoc) {
        this.matDoc = matDoc;
    }

    public double getItem() {
        return Item;
    }

    public void setItem(double item) {
        Item = item;
    }

    public double getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(double documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RowItem rowItem = (RowItem) o;
        return Double.compare(rowItem.matDoc, matDoc) == 0 &&
            Double.compare(rowItem.Item, Item) == 0 &&
            Double.compare(rowItem.documentNumber, documentNumber) == 0 &&
            Double.compare(rowItem.quantity, quantity) == 0 &&
            Double.compare(rowItem.amount, amount) == 0 &&
            Objects.equals(sloc, rowItem.sloc) &&
            Objects.equals(mvt, rowItem.mvt) &&
            Objects.equals(s, rowItem.s) &&
            Objects.equals(date, rowItem.date) &&
            Objects.equals(uom, rowItem.uom) &&
            Objects.equals(batch, rowItem.batch) &&
            Objects.equals(vendor, rowItem.vendor) &&
            Objects.equals(vendorName, rowItem.vendorName) &&
            Objects.equals(po, rowItem.po) &&
            Objects.equals(text, rowItem.text);
    }

    @Override
    public int hashCode() {

        return Objects
            .hash(sloc, mvt, s, matDoc, Item, documentNumber, date, quantity, uom, amount, batch,
                vendor,
                vendorName, po, text);
    }

    @Override
    public String toString() {
        return "RowItem{" +
            "sloc='" + sloc + '\'' +
            ", mvt='" + mvt + '\'' +
            ", s='" + s + '\'' +
            ", matDoc=" + matDoc +
            ", Item=" + Item +
            ", documentNumber=" + documentNumber +
            ", date=" + date +
            ", quantity=" + quantity +
            ", uom='" + uom + '\'' +
            ", amount=" + amount +
            ", batch='" + batch + '\'' +
            ", vendor='" + vendor + '\'' +
            ", vendorName='" + vendorName + '\'' +
            ", po='" + po + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
