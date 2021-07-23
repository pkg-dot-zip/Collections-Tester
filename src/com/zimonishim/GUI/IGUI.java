package com.zimonishim.GUI;

/**
 * Basic interface used by most graphical user interfaces in our software to make sure the code is easy to understand.
 */
public interface IGUI {

    /**
     * Runs initialisation methods and sets general stage settings, then runs the <code>show()</code> or <code>showAndWait()</code> method.
     * <p>
     * These include, but are not limited to:
     * <p></ul>
     * <li><code>setScene(Scene value)</code>
     * <li><code>setTitle(String value)</code>
     * <li><code>setWidth(Double value)</code>
     * <li><code>setResizable(boolean value)</code>
     * <li><code>initModality(Modality value)</code>
     * </ul>
     */
    void load();

    /**
     * Sets the values, alignment and spacing and children on initialisation.
     * Finally, the method setups the main layout, rarely anything else than a <code>GridPane</code>.
     * <p>
     * All the values that will be set here belong to one of the following:
     * <p><ul>
     * <li>Initialising Values (For example: Setting the items of a comboBox to a list)
     * <li>Alignment and Spacing
     * <li>Tooltips
     * <li>Adding the children (Setting contents of HBox-s etc.)
     * <li>Adding it all together (Adding everything to a pane)
     * </ul>
     */
    void setup();

    /**
     * Sets EventHandling of <i>JavaFX</i> <code>Nodes</code>. Mostly <code>setOnAction</code>s for <code>Button</code>s.
     */
    void actionHandlingSetup();
}