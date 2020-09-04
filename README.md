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
