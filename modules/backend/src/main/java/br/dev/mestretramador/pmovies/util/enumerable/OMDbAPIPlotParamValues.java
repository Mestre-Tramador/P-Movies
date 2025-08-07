package br.dev.mestretramador.pmovies.util.enumerable;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * All valid OMDb API Plot param values.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public enum OMDbAPIPlotParamValues implements EnumerableStringParser {
  //#region Entries
  /**
   * For short plots.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  SHORT("short"),

  /**
   * For full (complete) plots.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  FULL("full");
  //#endregion

  //#region Parser
  /**
   * Try convert a <code>String</code> into a valid entry of the enum.
   *
   * @param string Any valid <code>String</code>.
   * @return If not possible to convert, <code>null</code> is returned.
   */
  @Nullable
  public static OMDbAPIPlotParamValues parseString(final String string) {
    return EnumerableStringParser.parseString(
      OMDbAPIPlotParamValues.class,
      string
    );
  }
  //#endregion

  //#region Attributes
  /**
   * The query param value itself.
   */
  private String plot;

  /**
   * Every entry of the enum assigns the value of the query param.
   *
   * @param plotValue A valid value of the param according to OMDb API docs.
   */
  OMDbAPIPlotParamValues(final String plotValue) {
    this.plot = plotValue;
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
    return plot;
  }
  //#endregion
}
