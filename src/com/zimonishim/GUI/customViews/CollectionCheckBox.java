package com.zimonishim.GUI.customViews;

import javafx.scene.control.CheckBox;

import java.util.Collection;

public class CollectionCheckBox extends CheckBox {

    // TODO: Store class instead of instance.
    private Collection collection;

    public CollectionCheckBox(Collection collection) {
        super(collection.getClass().getSimpleName());
        this.collection = collection;
    }

    public Collection getCollection() {
        return this.collection;
    }
}
