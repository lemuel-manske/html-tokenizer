package html.tokenizer.parser;

import list.LinkedList;
import sort.Sortable;

import java.util.Optional;

/**
 * Represents a report of {@link HtmlParser parser} execution.
 *
 * <p>Has a list of all tags found in the HTML, with their names and its occurrences.
 *
 * @see HtmlParser
 */
public final class HtmlReport {

    private final LinkedList<TagOccurrence> tagOccurrences;

    public HtmlReport() {
        this.tagOccurrences = new LinkedList<>();
    }

    public void put(final String tagName) {
        findTagOccurrenceByTagName(tagName)
                .ifPresentOrElse(TagOccurrence::inc, () -> addTag(tagName));
    }

    public int getTagCount(final String tagToFind) {
        return findTagOccurrenceByTagName(tagToFind)
                .map(TagOccurrence::getOccurrences)
                .orElse(0);
    }

    /**
     * Returns all tags found in the HTML, with their names and occurrences, sorted by the given strategy.
     *
     * @see Sortable
     */
    public TagOccurrence[] getSortedTags(final Sortable<TagOccurrence> sortStrategy) {
        TagOccurrence[] tags = new TagOccurrence[tagOccurrences.size()];

        int i = 0;

        for (LinkedList.Node<TagOccurrence> node = tagOccurrences.getByIndex(i); node != null; node = node.next())
            tags[i++] = node.value();

        return sortStrategy.sort(tags);
    }

    private void addTag(String tagName) {
        tagOccurrences.add(new TagOccurrence(tagName, 1));
    }

    private Optional<TagOccurrence> findTagOccurrenceByTagName(final String tagName) {
        TagOccurrence tagOccurrenceToFind = new TagOccurrence(tagName);

        return Optional
                .ofNullable(tagOccurrences.getByValue(tagOccurrenceToFind))
                .map(LinkedList.Node::value);
    }

    public static class TagOccurrence implements Comparable<TagOccurrence> {

        private final String tag;
        private Integer occurrences;

        private TagOccurrence(final String tag) {
            this.tag = tag;
        }

        private TagOccurrence(final String tag, final Integer occurrences) {
            this.tag = tag;
            this.occurrences = occurrences;
        }

        public String getTag() {
            return tag;
        }

        public Integer getOccurrences() {
            return occurrences;
        }

        protected void inc() {
            occurrences++;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TagOccurrence that = (TagOccurrence) obj;
            return tag.equals(that.tag);
        }

        @Override
        public int compareTo(TagOccurrence o) {
            if (o == null) return 1;
            return tag.compareTo(o.tag);
        }
    }
}
