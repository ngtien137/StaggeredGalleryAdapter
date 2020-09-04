# StaggeredGalleryAdapter
Adapter for RecyclerView which makes collage media gallery as StaggeredGridlayout
## Preview 
![alt text](https://github.com/ngtien137/StaggeredGalleryAdapter/blob/master/git_resources/preview.gif)
## Features
- Support create a adapter for show a list as StaggeredGridlayout
- Support selecting item (single or multiple) depends on databinding and livedata
- Support load more and handle scroll to last position item
- Configure features by annotations
## Getting Started
### Configure build.gradle (Project)
* Add these lines:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
### Configure build gradle (Module):
* Import module base:
```gradle
dependencies {
  implementation 'com.github.ngtien137:StaggeredGalleryAdapter:Tag'
}
```
* You can get version of this module [here](https://jitpack.io/#ngtien137/StaggeredGalleryAdapter)
### Create object implements IMediaData
- IMediaDataInterface: It has two function to define the size of media and the source of media (URL or file path)
```java
public interface IMediaData {
  MediaSize getMediaSize();  //Return type size of media
  String getMediaDataSource(); //Return path of image or url image
}

public enum MediaSize {
  VERTICAL, HORIZONTAL, SQUARE;
}
```
- Now, create your media object:
```kotlin
data class AppPhoto(
    override var id: Long = -1,
    override var path: String = "",
    override var date: Long = Date().time,
    override var type: Int = Const.TYPE_IMAGE
) : IMediaData {
    override fun getUri(): Uri {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    var mediaSizeData: MediaSize? = null

    override fun getMediaSize(): MediaSize {
        if (mediaSizeData == null) {
            val bitmap = BitmapFactory.decodeFile(path)
            mediaSizeData = MediaSize.getMediaSize(bitmap?.width ?: 1, bitmap?.height ?: 1)
        }
        return mediaSizeData!!
    }

    override fun getMediaDataSource(): String {
        return path
    }
}
```
- As the code above, I define mediaSize in getMediaSize by get a bitmap from path. But as you know, this function is too slow if there're too many items.

