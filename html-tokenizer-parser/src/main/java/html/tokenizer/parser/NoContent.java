package html.tokenizer.parser;

/**
 * Defines that the input has no parsable
 * content: just nullish character sequences.
 *
 * <p>Nullish are: <code>/t /n /r /f /s /u00A0</code>
 */
public final class NoContent extends RuntimeException {
}
