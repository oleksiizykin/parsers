package model;

import java.util.LinkedList;
import java.util.Queue;

public class Item {
    private String plant;
    private String material;
    private String nameOfMaterial;
    private Queue<RowItem> rowItemList = new LinkedList<>();


    public Item() {
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNameOfMaterial() {
        return nameOfMaterial;
    }

    public void setNameOfMaterial(String nameOfMaterial) {
        this.nameOfMaterial = nameOfMaterial;
    }

    public Queue<RowItem> getRowItemList() {
        return rowItemList;
    }

    public void setRowItemList(RowItem rowItem) {
        this.rowItemList.add(rowItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (getPlant() != null ? !getPlant().equals(item.getPlant()) : item.getPlant() != null) return false;
        if (getMaterial() != null ? !getMaterial().equals(item.getMaterial()) : item.getMaterial() != null)
            return false;
        if (getNameOfMaterial() != null ? !getNameOfMaterial().equals(item.getNameOfMaterial()) : item.getNameOfMaterial() != null)
            return false;
        return getRowItemList() != null ? getRowItemList().equals(item.getRowItemList()) : item.getRowItemList() == null;
    }

    @Override
    public int hashCode() {
        int result = getPlant() != null ? getPlant().hashCode() : 0;
        result = 31 * result + (getMaterial() != null ? getMaterial().hashCode() : 0);
        result = 31 * result + (getNameOfMaterial() != null ? getNameOfMaterial().hashCode() : 0);
        result = 31 * result + (getRowItemList() != null ? getRowItemList().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "plant='" + plant + '\'' +
                ", material='" + material + '\'' +
                ", nameOfMaterial='" + nameOfMaterial + '\'' +
                ", rowItemList=" + rowItemList +
                '}';
    }
}
