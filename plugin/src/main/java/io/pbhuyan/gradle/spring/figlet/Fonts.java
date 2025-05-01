/*
 * Copyright 2022-2023 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pbhuyan.gradle.spring.figlet;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @since 0.1.0
 */
public final class Fonts {

    private static SortedSet<String> NAMES;

    public static SortedSet<String> all() {
        if (NAMES == null) {
            SortedSet<String> result = new TreeSet<>(String::compareTo);
            result.addAll(LocalFontLoader.fontNames());
            result.addAll(LibraryFontLoader.fontNames());
            NAMES = result;
        }
        return NAMES;
    }

    public static List<Integer> getPadding(String font){
        return switch (font){
            case "3d"->List.of(1,1,1,1);
            case "3dascii"->List.of(1,1,0,1);
            case "3ddiagonal"->List.of(0,1,1,1);
            case "4max"->List.of(1,2,1,2);
            case "5lineoblique"->List.of(0,1,1,1);
            case "ansiregular"->List.of(2,0,0,1);
            case "ansishadow"->List.of(1,1,0,1);
            case "bolger"->List.of(1,1,1,2);
            case "calvins"->List.of(1,2,1,2);
            case "colossal"->List.of(1,1,0,1);
            case "elite"->List.of(1,2,1,2);
            case "georgia11"->List.of(0,1,1,1);
            case "lean"->List.of(0,0,1,1);
            case "epic"->List.of(1,1,0,1);
            case "poison"->List.of(0,1,0,2);
            case "puffy"->List.of(1,2,1,2);
            case "soft"->List.of(0,1,0,1);
            case "univers"->List.of(1,0,1,1);
            case "usaflag"->List.of(1,1,0,1);
            case "whimsy"->List.of(0,3,1,3);
            default -> List.of(1,1,1,1);
        };
    }

}
