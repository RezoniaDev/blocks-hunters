package fr.mrtayai.blocks.classes;

import java.util.HashMap;
import java.util.Map;

public class CountMap<K> {
    private final Map<K, Integer> map = new HashMap<>();


    // Méthode pour ajouter ou mettre à jour le compte d'un élément
    public void add(K key) {
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    // Méthode pour obtenir le compte d'un élément
    public int getCount(K key) {
        return map.getOrDefault(key, 0);
    }

    // Méthode pour obtenir la map entière (pour inspection ou autre usage)
    public Map<K, Integer> getMap() {
        return map;
    }
}