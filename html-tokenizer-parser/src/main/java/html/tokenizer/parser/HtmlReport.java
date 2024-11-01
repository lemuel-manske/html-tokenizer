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

    void put(final String tagName) {
        findTagOccurrenceByTagName(tagName)
                .ifPresentOrElse(TagOccurrence::incrementTagOccurrence, () -> addTag(tagName));
    }

    public int getTagCount(final String tagToFind) {
        return findTagOccurrenceByTagName(tagToFind.toLowerCase())
                .map(TagOccurrence::getOccurrences)
                .orElse(0);
    }

    /**
     * Returns all tags found in the HTML, with their names and occurrences, sorted by the given strategy.
     *
     * @see Sortable
     */
    public TagOccurrence[] sortTagOccurrences(final Sortable<TagOccurrence> sortStrategy) {
        TagOccurrence[] tags = new TagOccurrence[tagOccurrences.size()];

        int i = 0;

        LinkedList.Node<TagOccurrence> firstNode = tagOccurrences.getByIndex(i);

        while (firstNode != null) {
            tags[i++] = firstNode.getValue();
            firstNode = firstNode.getNextNode();
        }

        return sortStrategy.sort(tags);
    }

    private void addTag(String tagName) {
        tagOccurrences.add(new TagOccurrence(tagName));
    }

    private Optional<TagOccurrence> findTagOccurrenceByTagName(final String tagName) {
        TagOccurrence tagOccurrenceToFind = new TagOccurrence(tagName);

        return Optional
                .ofNullable(tagOccurrences.getByValue(tagOccurrenceToFind))
                .map(LinkedList.Node::getValue);
    }
}
