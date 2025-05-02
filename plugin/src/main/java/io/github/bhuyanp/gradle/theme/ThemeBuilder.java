package io.github.bhuyanp.gradle.theme;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;
import java.util.Arrays;

public class ThemeBuilder extends AnsiFormat {


    private final ArrayList<Attribute> attributes = new ArrayList<>(2);

    public ThemeBuilder(Attribute... attributes){
        super(attributes);
        this.attributes.addAll(Arrays.asList(attributes));
    }

    @Override
    public String toString(){
        return attributes.toString();
    }
}
