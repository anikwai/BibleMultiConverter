package biblemulticonverter.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a single book of the bible.
 */
public class Book {
	private final String abbr;
	private final BookID id;
	private final String shortName;
	private final String longName;
	private final List<Chapter> chapters;

	public Book(String abbr, BookID id, String shortName, String longName) {
		this.abbr = Utils.validateString("abbr", abbr, Utils.BOOK_ABBR_REGEX);
		this.id = Utils.validateNonNull("id", id);
		this.shortName = Utils.validateString("shortName", shortName, Utils.NORMALIZED_WHITESPACE_REGEX);
		this.longName = Utils.validateString("longName", longName, Utils.NORMALIZED_WHITESPACE_REGEX);
		this.chapters = new ArrayList<Chapter>();
	}

	public void validate(Bible bible, List<String> danglingReferences, Map<String, Set<String>> dictionaryEntries) {
		if (chapters.size() == 0)
			throw new IllegalStateException("Book has no chapters: " + getAbbr());
		Chapter lastChapter = chapters.get(chapters.size() - 1);
		if (lastChapter.getVerses().size() == 0 && lastChapter.getProlog() == null)
			throw new IllegalStateException("Last chapter has neither prolog nor verses: " + getAbbr());
		int cnumber = 0;
		for (Chapter chapter : chapters) {
			cnumber++;
			chapter.validate(bible, getId(), getAbbr(), cnumber, danglingReferences, dictionaryEntries);
		}
	}

	public String getAbbr() {
		return abbr;
	}

	public BookID getId() {
		return id;
	}

	public String getShortName() {
		return shortName;
	}

	public String getLongName() {
		return longName;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

}
