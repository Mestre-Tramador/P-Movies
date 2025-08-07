package br.dev.mestretramador.pmovies.util;

import java.time.Year;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.dev.mestretramador.pmovies.util.enumerable.OMDbAPIParams;
import br.dev.mestretramador.pmovies.util.enumerable.OMDbAPIPlotParamValues;
import br.dev.mestretramador.pmovies.util.enumerable.OMDbAPIReturnParamValues;
import br.dev.mestretramador.pmovies.util.enumerable.OMDbAPITypeParamValues;

/**
 * A builder of OMDb API's query params for the requests,
 * easily convertible into a {@link LinkedMultiValueMap map}.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public final class OMDbAPIParamsBuilder {
  //#region Properties
  /**
   * Current version of OMDb API.
   */
  private static final String API_VERSION = "1";

  /**
   * Query params {@link LinkedMultiValueMap map} to use on the request.
   */
  private final LinkedMultiValueMap<OMDbAPIParams, String> query =
    new LinkedMultiValueMap<OMDbAPIParams, String>();
  //#endregion

  //#region Static Constructors
  /**
   * Initialize the query with the
   * <b>required</b> {@link OMDbAPIParams#SEARCH search} param.
   *
   * @param requiredSearchParam Any word or phrase to
   *                            filter the results from OMDb API.
   * @return                    This query does not come with the API Key set.
   */
  public static OMDbAPIParamsBuilder buildForSearch(
    final String requiredSearchParam
  ) {
    return buildForSearch(requiredSearchParam, null);
  }

  /**
   * Initialize the query with the
   * <b>required</b> {@link OMDbAPIParams#SEARCH search} param.
   *
   * @param requiredSearchParam Any word or phrase to
   *                            filter the results from OMDb API.
   * @param accessAPIKey        The authorization API Key.
   * @return                    This query comes with the API Key set.
   */
  public static OMDbAPIParamsBuilder buildForSearch(
    final String requiredSearchParam,
    final String accessAPIKey
  ) {
    return new OMDbAPIParamsBuilder(
      OMDbAPIParams.SEARCH,
      requiredSearchParam,
      accessAPIKey
    );
  }

  /**
   * Initialize the query with the
   * <b>required</b> {@link OMDbAPIParams#TITLE title} param.
   *
   * @param requiredTitleParam Any title to filter the results from OMDb API.
   * @return                   This query does not come with the API Key set.
   */
  public static OMDbAPIParamsBuilder buildForTitle(
    final String requiredTitleParam
  ) {
    return buildForTitle(requiredTitleParam, null);
  }

  /**
   * Initialize the query with the
   * <b>required</b> {@link OMDbAPIParams#TITLE title} param.
   *
   * @param requiredTitleParam Any title to filter the results from OMDb API.
   * @param accessAPIKey       The authorization API Key.
   * @return                   This query comes with the API Key set.
   */
  public static OMDbAPIParamsBuilder buildForTitle(
    final String requiredTitleParam,
    final String accessAPIKey
  ) {
    return new OMDbAPIParamsBuilder(
      OMDbAPIParams.TITLE,
      requiredTitleParam,
      accessAPIKey
    );
  }

  /**
   * Initialize the query with the
   * <b>required</b> {@link OMDbAPIParams#IMDB_ID IMDb ID} param.
   *
   * @param requiredIMDbIDParam Any ID from IMDb to filter
   *                            the results from OMDb API.
   * @return                    This query does not come with the API Key set.
   */
  public static OMDbAPIParamsBuilder buildForIMDbID(
    final String requiredIMDbIDParam
  ) {
    return buildForIMDbID(requiredIMDbIDParam, null);
  }

  /**
   * Initialize the query with the
   * <b>required</b> {@link OMDbAPIParams#IMDB_ID IMDb ID} param.
   *
   * @param requiredIMDbIDParam Any ID from IMDb to filter
   *                            the results from OMDb API.
   * @param accessAPIKey        The authorization API Key.
   * @return                    This query comes with the API Key set.
   */
  public static OMDbAPIParamsBuilder buildForIMDbID(
    final String requiredIMDbIDParam,
    final String accessAPIKey
  ) {
    return new OMDbAPIParamsBuilder(
      OMDbAPIParams.IMDB_ID,
      requiredIMDbIDParam,
      accessAPIKey
    );
  }
  //#endregion

  //#region Constructors
  /**
   * <p>
   *  The OMDb API needs, besides filters, a required parameter
   *  among all options to execute its search, both for data and posters.
   * </p>
   *
   * <p>Valid options includes:</p>
   *
   * <ul>
   *  <li>{@link OMDbAPIParams#SEARCH Search};</li>
   *  <li>{@link OMDbAPIParams#TITLE Title};</li>
   *  <li>{@link OMDbAPIParams#IMDB_ID IMDb ID}.</li>
   * </ul>
   *
   * @param requiredParamName  The name of the required parameter.
   * @param requiredParamValue The value of the required parameter.
   */
  private OMDbAPIParamsBuilder(
    final OMDbAPIParams requiredParamName,
    final String requiredParamValue
  ) {
    this(requiredParamName, requiredParamValue, null);
  }

  /**
   * <p>
   *  The OMDb API needs, besides filters, a required parameter
   *  among all options to execute its search, both for data and posters.
   * </p>
   *
   * <p>Valid options includes:</p>
   *
   * <ul>
   *  <li>{@link OMDbAPIParams#SEARCH Search};</li>
   *  <li>{@link OMDbAPIParams#TITLE Title};</li>
   *  <li>{@link OMDbAPIParams#IMDB_ID IMDb ID}.</li>
   * </ul>
   *
   * @param requiredParamName  The name of the required parameter.
   * @param requiredParamValue The value of the required parameter.
   * @param requiredAPIKey     Optional value of the API Key required parameter.
   */
  private OMDbAPIParamsBuilder(
    final OMDbAPIParams requiredParamName,
    final String requiredParamValue,
    final @Nullable String requiredAPIKey
  ) {
    query.add(requiredParamName, requiredParamValue);

    query.add(OMDbAPIParams.RETURN, OMDbAPIReturnParamValues.JSON.toString());
    query.add(OMDbAPIParams.VERSION, API_VERSION);

    if (requiredAPIKey != null) {
      query.add(OMDbAPIParams.API_KEY, requiredAPIKey);
    }
  }
  //#endregion

  //#region Methods
  /**
   * Add a param into the query. If it is already added,
   * then reset its value.
   *
   * @param paramName                 The name of the param.
   * @param paramValue                The value of the param.
   * @return                          Itself, being able to chain the method.
   * @throws IllegalStateException    If a <b>required</b> param is given.
   * @throws IllegalArgumentException If an invalid value is given
   *                                  for an <i>enumerable</i> param.
   */
  public OMDbAPIParamsBuilder add(
    final OMDbAPIParams paramName,
    final String paramValue
  ) {
    if (isRequiredParam(paramName)) {
      throw new IllegalStateException(
        "Required params are set on instantiation!"
      );
    }

    Predicate<String> paramValueValidator = switch (paramName) {
      case OMDbAPIParams.TYPE   -> this::isValidTypeParamValue;
      case OMDbAPIParams.PLOT   -> this::isValidPlotParamValue;
      case OMDbAPIParams.RETURN -> this::isValidReturnParamValue;
      case OMDbAPIParams.YEAR   -> this::isValidYearParamValue;
      case OMDbAPIParams.PAGE   -> this::isValidPageParamValue;
      default -> null;
    };

    if (paramValueValidator != null && !paramValueValidator.test(paramValue)) {
      throw new IllegalArgumentException(
        String.format(
          "Given param \"%s\" value \"%s\" is not a legal type!",
          paramName,
          paramValue
        )
      );
    }

    if (query.containsKey(paramName)) {
      query.set(paramName, paramValue);
    } else {
      query.add(paramName, paramValue);
    }

    return this;
  }

  /**
   * Add all given params into the query. Repeated params do not overlap.
   *
   * @param params                    A map of the params indexed by its names.
   * @return                          Itself, being able to chain the method.
   * @throws IllegalStateException    If a <b>required</b> param is given.
   * @throws IllegalArgumentException If an invalid value is given
   *                                  for an <i>enumerable</i> param.
   */
  public OMDbAPIParamsBuilder add(final Map<OMDbAPIParams, String> params) {
    for (Map.Entry<OMDbAPIParams, String> param : params.entrySet()) {
      add(param.getKey(), param.getValue());
    }

    return this;
  }
  //#endregion

  //#region Validating Methods
  /**
   * Verify if the given param is one of the <b>required</b> params.
   *
   * @param param The given param.
   * @return      <code>true</code> if it is one of
   *              the parameters used for instantiation.
   */
  private boolean isRequiredParam(final OMDbAPIParams param) {
    return (
      param == OMDbAPIParams.SEARCH
        || param == OMDbAPIParams.TITLE
        || param == OMDbAPIParams.IMDB_ID
    );
  }

  /**
   * Verify if the given value is one of the
   * <i>enumerable</i> values of the {@link OMDbAPIParams#TYPE Type} param.
   *
   * <p>Valid options includes:</p>
   *
   * <ul>
   *  <li>{@link OMDbAPITypeParamValues#MOVIE Movie};</li>
   *  <li>{@link OMDbAPITypeParamValues#SERIES Series};</li>
   *  <li>{@link OMDbAPITypeParamValues#EPISODE Episode}.</li>
   * </ul>
   *
   * @param value Any <code>String</code> to be parsed.
   * @return      <code>true</code> if it is one of the listed values.
   */
  private boolean isValidTypeParamValue(final String value) {
    final OMDbAPITypeParamValues valueType =
      OMDbAPITypeParamValues.parseString(value);

    return (
      valueType == OMDbAPITypeParamValues.MOVIE
        || valueType == OMDbAPITypeParamValues.SERIES
        || valueType == OMDbAPITypeParamValues.EPISODE
    );
  }

  /**
   * Verify if the given value is one of the
   * <i>enumerable</i> values of the {@link OMDbAPIParams#PLOT Plot} param.
   *
   * <p>Valid options includes:</p>
   *
   * <ul>
   *  <li>{@link OMDbAPIPlotParamValues#SHORT Short};</li>
   *  <li>{@link OMDbAPIPlotParamValues#FULL Full};</li>
   * </ul>
   *
   * @param value Any <code>String</code> to be parsed.
   * @return      <code>true</code> if it is one of the listed values.
   */
  private boolean isValidPlotParamValue(final String value) {
    final OMDbAPIPlotParamValues valuePlot =
      OMDbAPIPlotParamValues.parseString(value);

    return (
      valuePlot == OMDbAPIPlotParamValues.SHORT
        || valuePlot == OMDbAPIPlotParamValues.FULL
    );
  }

  /**
   * Verify if the given value is one of the
   * <i>enumerable</i> values of the
   * {@link OMDbAPIParams#RETURN Return Data Type} param.
   *
   * <p>Valid options includes:</p>
   *
   * <ul>
   *  <li>{@link OMDbAPIReturnParamValues#JSON JSON};</li>
   *  <li>{@link OMDbAPIReturnParamValues#XML XML};</li>
   * </ul>
   *
   * @param value Any <code>String</code> to be parsed.
   * @return      <code>true</code> if it is one of the listed values.
   */
  private boolean isValidReturnParamValue(final String value) {
    final OMDbAPIReturnParamValues valueReturn =
      OMDbAPIReturnParamValues.parseString(value);

    return (
      valueReturn == OMDbAPIReturnParamValues.JSON
        || valueReturn == OMDbAPIReturnParamValues.XML
    );
  }

  /**
   * Verify if the given value is a valid year.
   *
   * @param value Any <code>String</code> to be parsed.
   * @return      <code>true</code> if it is logically valid for a year.
   */
  private boolean isValidYearParamValue(final String value) {
    try {
      final int valueYear = Integer.parseInt(value);

      return valueYear >= 1 && valueYear <= Year.now().getValue();
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Verify if the given value is a valid page index.
   *
   * @param value Any <code>String</code> to be parsed.
   * @return      <code>true</code> if it is a positive integer above zero.
   */
  private boolean isValidPageParamValue(final String value) {
    try {
      return Integer.parseInt(value) >= 1;
    } catch (NumberFormatException e) {
      return false;
    }
  }
  //#endregion

  //#region Converting Methods
  /**
   * Convert the built query into a MultiValueMap for use on requests.
   *
   * @return Specifically the simplest
   *         {@link LinkedMultiValueMap implementation} of a MultiValueMap.
   * @see MultiValueMap
   */
  public MultiValueMap<String, String> toMultiValueMap() {
    final LinkedMultiValueMap<String, String> stringMultiValueMap =
      new LinkedMultiValueMap<String, String>();

    for (OMDbAPIParams param : this.query.keySet()) {
      stringMultiValueMap.add(param.toString(), this.query.getFirst(param));
    }

    return stringMultiValueMap;
  }
  //#endregion

  //#region Override Methods
  @Override
  public int hashCode() {
    return query.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof OMDbAPIParamsBuilder) {
      return this
        .toMultiValueMap()
        .equals(((OMDbAPIParamsBuilder) obj).toMultiValueMap());
    }

    return false;
  }

  @Override
  public String toString() {
    return query.toString();
  }
  //#endregion
}
