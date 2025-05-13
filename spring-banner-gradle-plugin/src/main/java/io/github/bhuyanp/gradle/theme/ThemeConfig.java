package io.github.bhuyanp.gradle.theme;


import io.github.bhuyanp.gradle.ansi.AnsiFormat;
import io.github.bhuyanp.gradle.ansi.Attribute;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public final class ThemeConfig extends AnsiFormat {
    private final ArrayList<Attribute> attributes = new ArrayList<>(2);
    public ThemeConfig(Attribute... attributes){
        super(attributes);
        this.attributes.addAll(Arrays.asList(attributes));
    }

    public boolean hasBackColor(){
        return attributes.stream().map(Attribute::construct).anyMatch(attribute -> attribute.contains("BACK"));
    }
    @Override
    public String toString(){
        String attributeString = attributes.stream().map(Attribute::construct).collect(Collectors.joining(", "));
        return "%s(%s)".formatted(this.getClass().getSimpleName(),attributeString);
    }
}
