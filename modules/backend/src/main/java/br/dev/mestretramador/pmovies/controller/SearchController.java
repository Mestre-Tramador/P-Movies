package br.dev.mestretramador.pmovies.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import com.fasterxml.jackson.databind.JsonNode;

import br.dev.mestretramador.pmovies.model.OMDbSearch;
import br.dev.mestretramador.pmovies.service.OMDbAPIService;
import br.dev.mestretramador.pmovies.util.OMDbAPIParamsBuilder;
import br.dev.mestretramador.pmovies.util.enumerable.OMDbAPIParams;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class to request generic searches in OMDb API.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
@RestController
public class SearchController extends Controller {
  //#region Routing
  /**
   * Prefix for all routes of this controller.
   *
   * @apiNote It inherits the base controller route prefix.
   */
  protected static final String ROUTE_PREFIX =
    Controller.ROUTE_PREFIX + "search";
  //#endregion

  //#region Search
  /**
   * A simple alias to make a base search with type and year already set.
   *
   * @param type       The given type to filter the search.
   * @param year       The given year to filter the search.
   * @param filter     Required filter (title name) for the search.
   * @param pageNumber Optional index of the paginator.
   * @return           The JSON contains the error message, if any,
   *                   or the result of the search.
   */
  @GetMapping(
    path = ROUTE_PREFIX + "/{type}/{year}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public final ResponseEntity<JsonNode> searchTypeWithYear(
    final @PathVariable String type,
    final @PathVariable String year,
    final @RequestParam(defaultValue = "") String filter,
    final @RequestParam(name = "page", defaultValue = "") String pageNumber
  ) {
    return search(filter, type, year, pageNumber);
  }

  /**
   * A simple alias to make a base search with type already set.
   *
   * @param type       The given type to filter the search.
   * @param filter     Required filter (title name) for the search.
   * @param year       Optional year to filter the search.
   * @param pageNumber Optional index of the paginator.
   * @return           The JSON contains the error message, if any,
   *                   or the result of the search.
   */
  @GetMapping(
    path = ROUTE_PREFIX + "/{type}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public final ResponseEntity<JsonNode> searchType(
    final @PathVariable String type,
    final @RequestParam(defaultValue = "") String filter,
    final @RequestParam(defaultValue = "") String year,
    final @RequestParam(name = "page", defaultValue = "") String pageNumber
  ) {
    return search(filter, type, year, pageNumber);
  }

  /**
   * The base search and fallback method to request
   * OMDb API with all given params.
   *
   * @param filter     Required filter (title name) for the search.
   * @param type       Optional type to filter the search.
   * @param year       Optional year to filter the search.
   * @param pageNumber Optional index of the paginator.
   * @return           The JSON contains the error message, if any,
   *                   or the result of the search.
   */
  @GetMapping(
    path = ROUTE_PREFIX,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public final ResponseEntity<JsonNode> search(
    final @RequestParam(defaultValue = "") String filter,
    final @RequestParam(defaultValue = "") String type,
    final @RequestParam(defaultValue = "") String year,
    final @RequestParam(name = "page", defaultValue = "") String pageNumber
  ) {
    if (filter.isEmpty()) {
      return responseBadRequest(
        "Missing param \"filter\"! Unable to make a search!"
      );
    }

    final HashMap<OMDbAPIParams, String> additionalParams =
      new HashMap<OMDbAPIParams, String>();

    if (!type.isEmpty()) {
      additionalParams.put(OMDbAPIParams.TYPE, type);
    }

    if (!year.isEmpty()) {
      additionalParams.put(OMDbAPIParams.YEAR, year);
    }

    if (!pageNumber.isEmpty()) {
      additionalParams.put(OMDbAPIParams.PAGE, pageNumber);
    }

    try {
      final RequestBodyUriSpec webClientRequest =
        prepareWebClientRequest(filter, additionalParams.entrySet());

      final OMDbSearch webClientResponse =
        webClientRequest
          .retrieve()
          .bodyToMono(OMDbSearch.class)
          .block();

      if (webClientResponse.hasError()) {
        return responseNotFound("No results for the given filter were found!");
      }

      final int totalResults = webClientResponse.totalResultsNumber();
      final int maxResults = OMDbSearch.MAX_RESULTS_IN_SEARCH;

      if (totalResults > maxResults) {
        final int pages = (
          Math.round(totalResults / maxResults)
            + totalResults % maxResults == 0 ? 0 : 1
        );

        System.out.println("Pages: " + pages);
      }

      return responseOK("search", webClientResponse.parsed());
    } catch (IllegalArgumentException e) {
      return responseUnprocessableEntity(e.getMessage());
    }
  }
  //#endregion

  //#region Web Client
  /**
   * This Web client uses the
   * {@link OMDbAPIService#makeOMDbAPIParamsForSearch(String) search}
   * static builder to create the params.
   *
   * @return The Web Client is created with the
   *         {@link OMDbAPIService#makeOMDbAPIDataWebClient() data} builder
   *         and all params are set in the given order.
   */
  @Override
  protected final RequestBodyUriSpec prepareWebClientRequest(
    final String requiredParam,
    final Set<Map.Entry<OMDbAPIParams, String>> additionalParams
  ) {
    final OMDbAPIService service = getOMDbAPIService();
    final OMDbAPIParamsBuilder builder =
      service.makeOMDbAPIParamsForSearch(requiredParam);

    if (additionalParams != null) {
      for (
        Map.Entry<OMDbAPIParams, String> additionalParam : additionalParams
      ) {
        builder.add(
          additionalParam.getKey(),
          additionalParam.getValue()
        );
      }
    }

    return (
      (RequestBodyUriSpec) service
        .makeOMDbAPIDataWebClient()
        .get()
        .uri(
          (p) -> p.queryParams(builder.toMultiValueMap()).build()
        )
    );
  }
  //#endregion
}
