# Collapsing Avatar Toolbar

This little library demonstrates how to add a Collapsible Toolbar to your application with an **avatar** that moves and expands as seen in the Telegram Android application.

This library consist in **one single view**, and uses the Design Support Library.

# Demo

Check out the sample proyect included.

![sample](https://raw.githubusercontent.com/Sloy/CollapsingAvatarToolbar/master/art/collapsingavatar.gif)

# Gradle
```
dependencies {
    compile 'com.sloydev:collapsingavatartoolbar:1.0.0'
}
```

# Usage

Add `com.sloydev.collapsingavatartoolbar.CollapsingAvatarToolbar` to your layout:

```xml
<com.sloydev.collapsingavatartoolbar.CollapsingAvatarToolbar
  android:layout_width="wrap_content"
  android:layout_height="?attr/actionBarSize"
  app:collapsedPadding="@dimen/collapsedPadding"
  app:expandedPadding="@dimen/expandedPadding"
  app:collapsedImageSize="@dimen/collapsedImageSize"
  app:expandedImageSize="@dimen/expandedImageSize"
  app:collapsedTextSize="@dimen/collapsedTextSize"
  app:expandedTextSize="@dimen/expandedTextSize"
  >
```

It must be placed inside a CollapsingToolbarLayout with a sibling Toolbar, like this:

```xml
<android.support.design.widget.CoordinatorLayout
  ...
  >

    <android.support.design.widget.AppBarLayout
      ...
      android:layout_height="@dimen/expanded_toolbar_height"
      >
      
        <android.support.design.widget.CollapsingToolbarLayout
    	  ...
          app:contentScrim="?attr/colorPrimary"
          app:layout_scrollFlags="scroll|exitUntilCollapsed"
          >

            <android.support.v7.widget.Toolbar
        	  ...
              app:layout_collapseMode="pin"
              />

            <com.sloydev.collapsingavatartoolbar.CollapsingAvatarToolbar
              ...
              >

                <de.hdodenhof.circleimageview.CircleImageView
                  android:id="@id/cat_avatar"
        	      ...
                  />

               	<TextView 
                  android:id="@id/cat_title"
                  ...
                  />
                
            </com.sloydev.collapsingavatartoolbar.CollapsingAvatarToolbar>

        </android.support.design.widget.CollapsingToolbarLayout>

    </android.support.design.widget.AppBarLayout>

    <!-- The rest of your activity layout -->
    ...

</android.support.design.widget.CoordinatorLayout>
```

### Important points

- CollapsingAvatarToolbar must be placed inside an AppBarLayout, wrapped with a CollapsingToolbarLayout. Check out the [Android Design Support library](http://android-developers.blogspot.com.es/2015/05/android-design-support-library.html) for more info.

- CollapsingAvatarToolbar must have a sibling Toolbar, which will be shown below it for navigation controls and menu items. If you don't want to use a Toolbar... well we should talk about it.

- Expanded height will be determined by the AppBarLayout's height.

- Collapsed height will be determined by the Toolbar's height.

- You must provide the avatar and title views inside CollapsingAvatarToolbar, with the exact same ids shown above. Note: these are the library's ids, not your owns. 

- You can use any kind of TextView for the title, and any view you want for the avatar. I used hdodenhof's [CircleImageView](https://github.com/hdodenhof/CircleImageView) in the sample, but that's up to you.

- You can also add more views inside CollapsingAvatarToolbar. Take a look at [the sample](https://github.com/Sloy/CollapsingAvatarToolbar/blob/master/sample%2Fsrc%2Fmain%2Fres%2Flayout%2Factivity_avatar_toolbar_sample.xml) for an example with subtitle.

- All custom attributes are optional. If not provided [defaults](https://github.com/Sloy/CollapsingAvatarToolbar/blob/master/library/src/main/res/values/dimens.xml) will be used.

# Contribute

Improvements are most than welcome. Feel free to send a Pull Request or open an Issue.

I am pretty bad at explaining myself. Check out the sample project for implementation details or ask me anything you need to know.

# External libraries
- [Support v7 AppCompat Library](https://developer.android.com/tools/support-library/features.html)
- [Design Support Library](https://developer.android.com/tools/support-library/features.html)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) (for sample)
