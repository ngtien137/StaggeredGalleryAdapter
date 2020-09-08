package com.lhd.gallery_adapter.model;

public interface IMediaData {
    MediaSize getMediaSize();  //Return type size of media

    String getMediaDataSource(); //Return path of image or url image

    default String getKeyGroup() {
        return "";
    }
}

