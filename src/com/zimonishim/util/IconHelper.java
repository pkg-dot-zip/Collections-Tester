package com.zimonishim.util;

import javafx.scene.image.ImageView;

import java.net.URL;

//TODO: Support icons.

/**
 * Helps us retrieve the right icon for the defined collections type (list, set or map).
 * NOTE: This is not used right now!
 */
public class IconHelper {

    public static ImageView getCollectionIcon(String className){
        URL url = null;
        if (className.toLowerCase().contains("list")) {
            url = getImageFromPath("/listIcon.png");
        } else if (className.toLowerCase().contains("set")) {
            url = getImageFromPath("/setIcon.png");
        } else if (className.toLowerCase().contains("map")){
            url = getImageFromPath("/mapIcon.png");
        } else {
            throw new IllegalArgumentException("Couldn't find a valid image for " + className + " in the " + IconHelper.class.getSimpleName() + " class.");
        }

        return new ImageView(url.toString());
    }

    private static URL getImageFromPath(String path) {
        return IconHelper.class.getResource(path);
    }
}