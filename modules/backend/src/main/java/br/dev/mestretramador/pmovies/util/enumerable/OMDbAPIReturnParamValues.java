package br.dev.mestretramador.pmovies.util.enumerable;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * All valid OMDb API Return Data Type param values.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public enum OMDbAPIReturnParamValues implements EnumerableStringParser {
  //#region Entries
  /**
   * To return a JSON in the REST Response.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  JSON("json"),

  /**
   * To return a XML in the REST Response.
   * @see <a href="http://www.omdbapi.com">OMDb API docs</a>
   */
  XML("xml");
  //#endregion

  //#region Parser
  /**
   * Try convert a <code>String</code> into a valid entry of the enum.
   *
   * @param string Any valid <code>String</code>.
   * @return If not possible to convert, <code>null</code> is returned.
   */
  @Nullable
  public static OMDbAPIReturnParamValues parseString(final String string) {
    return EnumerableStringParser.parseString(
      OMDbAPIReturnParamValues.class,
      string
    );
  }
  //#endregion

  //#region Attributes
  /**
   * The query param value itself.
   */
  private String returnDataType;

  /**
   * Every entry of the enum assigns the value of the query param.
   *
   * @param returnDataTypeValue A valid value of the param according
   *                            to OMDb API docs.
   */
  OMDbAPIReturnParamValues(final String returnDataTypeValue) {
    returnDataType = returnDataTypeValue;
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
    return returnDataType;
  }
  //#endregion
}
