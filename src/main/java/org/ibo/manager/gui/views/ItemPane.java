package org.ibo.manager.gui.views;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lombok.Getter;
import org.ibo.manager.models.Item;

/**
 * A label that stores and display information about an {@link Item}
 * used to display the item in a parent node like a {@link javafx.scene.control.ListView}
 */
public final class ItemPane extends Label {
    @Getter
    private final Long itemId;

    public ItemPane( Long itemId ) {
        this.itemId = itemId;
    }

    public ItemPane( String text, Long itemId ) {
        super( text );
        this.itemId = itemId;
    }

    public ItemPane( String text, Node graphic, Long itemId ) {
        super( text, graphic );
        this.itemId = itemId;
    }
}
