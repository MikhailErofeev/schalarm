package application.activities.main_tab_fragments.faces;

import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public interface TagsChangeListener {
    void tagUnselected(String tag);

    void tagsSelected(Set<String> tags);

    void tagSelected(String tag);
}
