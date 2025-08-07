package br.dev.mestretramador.pmovies.service;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.dev.mestretramador.pmovies.config.OMDbAPIProperties;
import br.dev.mestretramador.pmovies.util.OMDbAPIParamsBuilder;

/**
 * Service for handling OMDb API requests,
 * serialization and data binding.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
@Service
public final class OMDbAPIService {
  //#region Properties
  /**
   * The properties (environment variables) of the OMDb API.
   */
  @Autowired
  private OMDbAPIProperties apiProperties;
  //#endregion

  //#region Params
  /**
   * Start the query params of the OMDb API for search requests.
   *
   * @param paramSearch The title and/or word sequence to search.
   * @return            The query params already have the
   *                    required param and the API key.
   */
  public OMDbAPIParamsBuilder makeOMDbAPIParamsForSearch(
    final String paramSearch
  ) {
    return makeOMDbAPIParams(
      OMDbAPIParamsBuilder::buildForSearch,
      paramSearch
    );
  }

  /**
   * Start the query params of the OMDb API with a model title.
   *
   * @param paramTitle The specific title to get the data and/or poster.
   * @return           The query params already have the
   *                   required param and the API key.
   */
  public OMDbAPIParamsBuilder makeOMDbAPIParamsForTitle(
    final String paramTitle
  ) {
    return makeOMDbAPIParams(
      OMDbAPIParamsBuilder::buildForTitle,
      paramTitle
    );
  }

  /**
   * Start the query params of the OMDb API with a model IMDb ID.
   *
   * @param paramIMDbID The ID from IMDb to get the data and/or poster.
   * @return            The query params already have the
   *                    required param and the API key.
   */
  public OMDbAPIParamsBuilder makeOMDbAPIParamsForIMDbID(
    final String paramIMDbID
  ) {
    return makeOMDbAPIParams(
      OMDbAPIParamsBuilder::buildForIMDbID,
      paramIMDbID
    );
  }

  /**
   * Create an instance of the {@link OMDbAPIParamsBuilder} class
   * with the given static constructor.
   *
   * @param builderType   A static constructor of the class.
   * @param requiredParam The value for the required param of
   *                      the given constructor.
   * @return              An instance of the builder with
   *                      the required param and the API key set.
   */
  private OMDbAPIParamsBuilder makeOMDbAPIParams(
    final BiFunction<String, String, OMDbAPIParamsBuilder> builderType,
    final String requiredParam
  ) {
    return builderType.apply(requiredParam, apiProperties.key());
  }
  //#endregion

  //#region Web Client
  /**
   * Start the request for the Data OMDb API .
   *
   * @return The only thing set in the client is the correct URL.
   */
  public WebClient makeOMDbAPIDataWebClient() {
    return makeOMDbAPIWebClient(apiProperties.subHostData());
  }

  /**
   * Start the request for the Poster OMDb API .
   *
   * @return The only thing set in the client is the correct URL.
   */
  public WebClient makeOMDbAPIPosterWebClient() {
    return makeOMDbAPIWebClient(apiProperties.subHostPoster());
  }

  /**
   * Create an instance of the {@link WebClient} interface
   * with the OMDb API host and the given subhost.
   *
   * @param subHost A valid subdomain of the API.
   * @return        An instance of the client with the complete URL.
   */
  private WebClient makeOMDbAPIWebClient(final String subHost) {
    return WebClient.create(
      String.format("https://%s.%s", subHost, apiProperties.host())
    );
  }
  //#endregion
}
