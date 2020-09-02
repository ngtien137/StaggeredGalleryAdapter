package com.lhd.gallery_adapter.model;

import java.util.ArrayList;
import java.util.List;

public class GroupMedia<T extends IMediaData> {

    private List<T> listMedia = new ArrayList<>();

    public List<T> getListMedia() {
        return listMedia;
    }

    public void setListMedia(List<T> listMedia) {
        this.listMedia = listMedia;
    }

    public void setListMedia(T media) {
        List<T> list = new ArrayList<>();
        list.add(media);
        this.listMedia = list;
    }

    public String getKeyCollage() {
        return getKeyCollage(listMedia);
    }

    public static <T extends IMediaData> String getKeyCollage(List<T> listMedia) {
        String keyCollage = "0_0_0";
        if (!listMedia.isEmpty()) {
            int verticalCount = 0;
            int horizontalCount = 0;
            int squareCount = 0;
            for (int i = 0; i < listMedia.size(); i++) {
                switch (listMedia.get(i).getMediaSize()) {
                    case VERTICAL:
                        verticalCount++;
                        break;
                    case HORIZONTAL:
                        horizontalCount++;
                        break;
                    case SQUARE:
                        squareCount++;
                        break;
                }
            }
            keyCollage = verticalCount + "_" + horizontalCount + "_" + squareCount;
        }
        return keyCollage;
    }

    @Override
    public String toString() {
        return "GroupMedia{" +
                "listMedia=" + listMedia + ", KeyCollage: " + getKeyCollage() +
                '}';
    }
}
