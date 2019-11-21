package model;

import java.util.Set;
import java.util.TreeSet;

public class NonameMaterial {
    private Set<String> nonameMaterials = new TreeSet<>();

    public void add(String material) {
        nonameMaterials.add(material);
    }

    public Set<String> getNonameMaterials() {
        return nonameMaterials;
    }
}
