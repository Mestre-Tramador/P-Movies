package br.dev.mestretramador.pmovies.util.enumerable;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * All available OMDb API query params.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public enum OMDbAPIParams implements EnumerableStringParser {
  //#region Entries
  /**
   * <i>Movie title to search for.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  SEARCH("s"),

  /**
   * <i>Movie title to search for.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  TITLE("t"),

  /**
   * <i>A valid IMDb ID.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  IMDB_ID("i"),

  /**
   * <i>Type of result to return.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  TYPE("type"),

  /**
   * <i>Return short or full plot.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  PLOT("plot"),

  /**
   * <i>Year of release.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  YEAR("y"),

  /**
   * <i>Page number to return.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  PAGE("page"),

  /**
   * <i>The data type to return.</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  RETURN("r"),

  /**
   * <i>API version (reserved for future use).</i>
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  VERSION("v"),

  /**
   * API Key param as <i>Usage</i> section.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  API_KEY("apikey");
  //#endregion

  //#region Parser
  /**
   * Try convert a <code>String</code> into a valid entry of the enum.
   *
   * @param string Any valid <code>String</code>.
   * @return If not possible to convert, <code>null</code> is returned.
   */
  @Nullable
  public static OMDbAPIParams parseString(final String string) {
    return EnumerableStringParser.parseString(OMDbAPIParams.class, string);
  }
  //#endregion

  //#region Attributes
  /**
   * The query param name itself.
   */
  private String param;

  /**
   * Every entry of the enum assigns the name of the query param.
   *
   * @param paramName The name of the param according to OMDb API docs.
   */
  OMDbAPIParams(final String paramName) {
    param = paramName;
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
    return param;
  }
  //#endregion
}
