package br.dev.mestretramador.pmovies.util.enumerable;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * All valid OMDb API Type param values.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public enum OMDbAPITypeParamValues implements EnumerableStringParser {
  //#region Entries
  /**
   * For a movie.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  MOVIE("movie"),

  /**
   * For a series.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  SERIES("series"),

  /**
   * For an episode.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  EPISODE("episode");
  //#endregion

  //#region Parser
  /**
   * Try convert a <code>String</code> into a valid entry of the enum.
   *
   * @param string Any valid <code>String</code>.
   * @return If not possible to convert, <code>null</code> is returned.
   */
  @Nullable
  public static OMDbAPITypeParamValues parseString(final String string) {
    return EnumerableStringParser.parseString(
      OMDbAPITypeParamValues.class,
      string
    );
  }
  //#endregion

  //#region Attributes
  /**
   * The query param value itself.
   */
  private String type;

  /**
   * Every entry of the enum assigns the value of the query param.
   *
   * @param typeValue A valid value of the param according to OMDb API docs.
   */
  OMDbAPITypeParamValues(final String typeValue) {
    type = typeValue;
  }
  //#endregion

  //#region Override Methods
  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public String toParseString() {
    return toString();
  }

  @Override
  public String toString() {
    return type;
  }
  //#endregion
}
