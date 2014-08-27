package com.example.schalarm_android_app.activities.elements;

import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public interface TagSelectedListener {
    void tagUnselected(String tag);

    void tagsSelected(Set<String> tags);

    void tagSelected(String tag);
}
