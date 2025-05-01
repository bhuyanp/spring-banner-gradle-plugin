package io.pbhuyan.gradle.spring.theme;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;
import java.util.Arrays;

public class ThemeFormat extends AnsiFormat {
    private final ArrayList<Attribute> _attributes = new ArrayList<>(2);
    public ThemeFormat(Attribute... attributes) {
        super(attributes);
        _attributes.addAll(Arrays.asList(attributes));
    }
    public boolean hasBackColor(){
        return this._attributes.stream().anyMatch(attribute-> attribute.getClass().getSimpleName().equals("BackColorAttribute"));
    }
}
