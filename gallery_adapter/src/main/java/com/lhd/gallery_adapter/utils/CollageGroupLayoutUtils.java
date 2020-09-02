package com.lhd.gallery_adapter.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollageGroupLayoutUtils {

    private static Map<Integer, Map<String, List<GalleryGridData>>> listSupportedLayout = new HashMap<>();
    public static float BORDER_PERCENT = 0.01f;

    static {
        loadSupportedLayouts();
    }

    public static List<GalleryGridData> getLayoutSupport(int numberItem, String key){
        Map<String, List<GalleryGridData>> listLayout = getLayoutSupportForNumberItem(numberItem);
        if (listLayout==null)
            return null;
        return listLayout.get(key);
    }

    public static Map<String, List<GalleryGridData>> getLayoutSupportForNumberItem(int numberItem) {
        return listSupportedLayout.get(numberItem);
    }

    public static void loadSupportedLayouts() {
        Map<String, List<GalleryGridData>> map1Item = new HashMap<>();
        map1Item.put("0_0_1", get0_0_1());
        map1Item.put("1_0_0", get1_0_0());
        map1Item.put("0_1_0", get0_1_0());
        listSupportedLayout.put(1, map1Item);

        Map<String, List<GalleryGridData>> map2Item = new HashMap<>();
        map2Item.put("2_0_0", get2_0_0());
        map2Item.put("0_2_0", get0_2_0());
        map2Item.put("0_0_2", get0_0_2());
        listSupportedLayout.put(2, map2Item);

        Map<String, List<GalleryGridData>> map3Item = new HashMap<>();
        listSupportedLayout.put(3, map3Item);
    }

    /**
     * 1 Item
     */
    private static List<GalleryGridData> get0_0_1() {
        List<GalleryGridData> listLayout1Item = new ArrayList<>();
        float percentSizeWidth = getSizePercent(1);
        float percentSizeHeight = getSizePercent(1);
        listLayout1Item.add(new GalleryGridData(percentSizeWidth, percentSizeHeight, BORDER_PERCENT, BORDER_PERCENT));
        return listLayout1Item;
    }

    private static List<GalleryGridData> get0_1_0() {
        List<GalleryGridData> listLayout1Item = new ArrayList<>();
        float percentSizeWidth = getSizePercent(1);
        float percentSizeHeight = percentSizeWidth * 2;
        listLayout1Item.add(new GalleryGridData(percentSizeWidth, percentSizeHeight, BORDER_PERCENT, BORDER_PERCENT));
        return listLayout1Item;
    }

    private static List<GalleryGridData> get1_0_0() {
        List<GalleryGridData> listLayout1Item = new ArrayList<>();
        float percentSizeWidth = getSizePercent(1);
        float percentSizeHeight = percentSizeWidth * 2;
        listLayout1Item.add(new GalleryGridData(percentSizeWidth, percentSizeHeight, BORDER_PERCENT, BORDER_PERCENT));
        return listLayout1Item;
    }

    /**
     * 2 Item
     */
    private static List<GalleryGridData> get0_0_2() {
        List<GalleryGridData> listLayoutItem = new ArrayList<>();
        float percentSizeWidth = getSizePercent(2);
        float percentSizeHeight = getSizePercent(2);
        listLayoutItem.add(new GalleryGridData(percentSizeWidth, percentSizeHeight, BORDER_PERCENT, BORDER_PERCENT));
        return listLayoutItem;
    }

    private static List<GalleryGridData> get2_0_0() {
        List<GalleryGridData> listLayoutItem = new ArrayList<>();
        float percentSizeWidth = getSizePercent(2);
        float percentSizeHeight = getSizePercent(1);
        listLayoutItem.add(new GalleryGridData(percentSizeWidth, percentSizeHeight, BORDER_PERCENT, BORDER_PERCENT));
        return listLayoutItem;
    }

    private static List<GalleryGridData> get0_2_0() {
        List<GalleryGridData> listLayoutItem = new ArrayList<>();
        float percentSizeWidth = getSizePercent(2);
        float percentSizeHeight = getSizePercent(1);
        listLayoutItem.add(new GalleryGridData(percentSizeWidth, percentSizeHeight, BORDER_PERCENT, BORDER_PERCENT));
        return listLayoutItem;
    }

    private static float getSizePercent(int itemCount) {
        return ((100f - BORDER_PERCENT * 100f * (itemCount + 1)) / itemCount) / 100f;
    }
}
