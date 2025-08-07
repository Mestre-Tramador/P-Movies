package br.dev.mestretramador.pmovies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents the result from a OMDb API search request.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 * @param response     A "boolean" indicating if the search was successful
 *                     or not.
 * @param totalResults The totality of results returned in the searching.
 * @param search       The results itself, in an array.
 * @param error        The error message, if any occurred.
 */
public record OMDbSearch(
  @JsonProperty(RESPONSE_KEY) String response,
  @JsonProperty(TOTAL_RESULTS_KEY) String totalResults,
  @JsonProperty(SEARCH_KEY) ArrayNode search,
  @JsonProperty(ERROR_KEY) String error
) {
  //#region JSON Keys
  /**
   * The key for the response "boolean".
   */
  private static final String RESPONSE_KEY = "Response";

  /**
   * The key for the totality of results.
   */
  private static final String TOTAL_RESULTS_KEY = "totalResults";

  /**
   * The key for the list of results.
   */
  private static final String SEARCH_KEY = "Search";

  /**
   * The key for the error message.
   */
  private static final String ERROR_KEY = "Error";
  //#endregion

  //#region Constants
  /**
   * Value if the search <code>response</code> was successful.
   */
  public static final String RESPONSE_KEY_TRUE_VALUE = "True";

  /**
   * Value if the search <code>response</code> wasn't successful.
   */
  public static final String RESPONSE_KEY_FALSE_VALUE = "False";

  /**
   * The maximum of results a page from OMDb API can store.
   */
  public static final int MAX_RESULTS_IN_SEARCH = 10;
  //#endregion

  //#region Accessors
  /**
   * Read the "boolean" value of the response.
   *
   * @return It is either "{@link OMDbSearch#RESPONSE_KEY_TRUE_VALUE true}"
   *         or "{@link OMDbSearch#RESPONSE_KEY_FALSE_VALUE false}".
   */
  public String response() {
    return response;
  }

  /**
   * Read the "numeric" value of the quantity of results of the response.
   *
   * @return If set, it is a valid numeric value as <code>String</code>.
   */
  public String totalResults() {
    return totalResults;
  }

  /**
   * Read the original results of the search.
   *
   * @return A {@link ArrayNode#deepCopy() deep copy}, to keep it "immutable".
   */
  public ArrayNode search() {
    return search != null ? search.deepCopy() : null;
  }

  /**
   * Read the error message of a failed search.
   *
   * @return The error message may not be accurate
   *         for the given params of the original search.
   */
  public String error() {
    return error;
  }
  //#endregion

  //#region Other Accessors
  /**
   * Easy accessor for successful search results.
   *
   * @return It is an actual <code>boolean</code>
   *         of the {@link OMDbSearch#response response} key.
   */
  public boolean hasResult() {
    return response().equals(RESPONSE_KEY_TRUE_VALUE);
  }

  /**
   * Easy accessor for failed search results.
   *
   * @return It is an actual <code>boolean</code>
   *         of the {@link OMDbSearch#response response} key.
   */
  public boolean hasError() {
    return response().equals(RESPONSE_KEY_FALSE_VALUE);
  }

  /**
   * Easy accessor for the quantity of items in the result.
   *
   * @return It is an actual <code>int</code>
   *         of the {@link OMDbSearch#totalResults total results} key.
   */
  public int totalResultsNumber() {
    try {
      return Integer.parseInt(totalResults());
    } catch (NumberFormatException e) {
      return 0;
    }
  }
  //#endregion

  //#region Parser
  /**
   * Parse the search results into a beautified version holding the same data.
   *
   * @return <i>Documentation of the parsed type in progress!</i>
   */
  public ArrayNode parsed() {
    final ArrayNode searchParsed = JsonNodeFactory.instance.arrayNode();

    for (final JsonNode searchItem : search()) {
      final ObjectNode parsedItem = JsonNodeFactory.instance.objectNode();

      parsedItem.set("title", searchItem.get("Title"));
      parsedItem.set(
        "year",
        JsonNodeFactory.instance
          .numberNode(
            searchItem.get("Year").asInt()
          )
      );
      parsedItem.set("imdb_id", searchItem.get("imdbID"));
      parsedItem.set("type", searchItem.get("Type"));
      parsedItem.set(
        "poster",
        searchItem.get("Poster").asText().equals("N/A")
          ? null
          : searchItem.get("Poster")
      );

      searchParsed.add(parsedItem);
    }

    return searchParsed;
  }
  //#endregion
}
