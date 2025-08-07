package br.dev.mestretramador.pmovies.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.dev.mestretramador.pmovies.service.OMDbAPIService;
import br.dev.mestretramador.pmovies.util.enumerable.OMDbAPIParams;

/**
 * <p>Base controller class for all others.</p>
 *
 * <p>
 *  It has utility methods for JSON responses,
 *  easy calls to the OMDb API {@link OMDbAPIService service},
 *  and routes prefixes.
 * </p>
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public abstract class Controller {
  //#region Routing
  /**
   * Base prefix of all routes.
   */
  protected static final String ROUTE_PREFIX = "/";
  //#endregion

  //#region Autowired
  /**
   * Instance of the service to make requests to the OMDb API.
   */
  @Autowired
  private OMDbAPIService service;

  /**
   * Internal conversor of plain objects into JSON for REST responses.
   */
  @Autowired
  private ObjectMapper objectMapper;
  //#endregion

  //#region Getters
  /**
   * Internal getter for child Controllers be able
   * to create and send requests to OMDb API.
   *
   * @return The service is autowired, so no setting is necessary.
   */
  protected OMDbAPIService getOMDbAPIService() {
    return service;
  }
  //#endregion

  //#region Web Client
  /**
   * Abstraction for child controller classes instantiate
   * a Web Client to send requests to OMDb API.
   *
   * @param requiredParam    The required param for the specific
   *                         request of the child controller.
   * @param additionalParams Any additional params needed.
   * @return                 The Web Client shall have the correct URL
   *                         and params set.
   */
  protected abstract RequestBodyUriSpec prepareWebClientRequest(
    String requiredParam,
    Set<Map.Entry<OMDbAPIParams, String>> additionalParams
  );
  //#endregion

  //#region 2xx Status Code
  /**
   * <p>
   *  Return a response with HTTP <b>200</b> status code.
   * </p>
   *
   * <p>
   *  The JSON sent in the body has a custom key to hold the data.
   * </p>
   *
   * @param key  A string representing and/or naming the returned data.
   * @param data Any JSON acceptable value.
   * @return     The JSON has one key (custom) and the given data.
   */
  protected final ResponseEntity<JsonNode> responseOK(
    final String key,
    final Object data
  ) {
    return response(key, data, HttpStatus.OK);
  }

  /**
   * <p>
   *  Return a response with HTTP <b>200</b> status code.
   * </p>
   *
   * <p>
   *  The JSON sent in the body has a "message" key to hold the data.
   * </p>
   *
   * @param message Any JSON acceptable value.
   * @return        The JSON has one <code>message</code> key
   *                and the given data.
   */
  protected final ResponseEntity<JsonNode> responseOK(final Object message) {
    return responseMessage(message, HttpStatus.OK);
  }
  //#endregion

  //#region 4xx Status Code
  /**
   * <p>
   *  Return a response with HTTP <b>400</b> status code.
   * </p>
   *
   * <p>
   *  The JSON sent in the body has a "error" key to hold the error.
   * </p>
   *
   * @param error Any JSON acceptable value.
   * @return      The JSON has one <code>error</code> key
   *              and the given data.
   */
  protected final ResponseEntity<JsonNode> responseBadRequest(
    final Object error
  ) {
    return responseError(error, HttpStatus.BAD_REQUEST);
  }

  /**
   * <p>
   *  Return a response with HTTP <b>404</b> status code.
   * </p>
   *
   * <p>
   *  The JSON sent in the body has a "error" key to hold the error.
   * </p>
   *
   * @param error Any JSON acceptable value.
   * @return      The JSON has one <code>error</code> key
   *              and the given data.
   */
  protected final ResponseEntity<JsonNode> responseNotFound(
    final Object error
  ) {
    return responseError(error, HttpStatus.NOT_FOUND);
  }

  /**
   * <p>
   *  Return a response with HTTP <b>422</b> status code.
   * </p>
   *
   * <p>
   *  The JSON sent in the body has a "error" key to hold the error.
   * </p>
   *
   * @param error Any JSON acceptable value.
   * @return      The JSON has one <code>error</code> key
   *              and the given data.
   */
  protected final ResponseEntity<JsonNode> responseUnprocessableEntity(
    final Object error
  ) {
    return responseError(error, HttpStatus.UNPROCESSABLE_ENTITY);
  }
  //#endregion

  //#region HTTP Status Code
  /**
   * Create a response of a JSON containing only the
   * "message" key and a given value.
   *
   * @param value Any JSON acceptable value.
   * @param code  Usually  codes, but
   *              <code>4xx</code> and <code>5xx</code>
   *              are acceptable as well.
   * @return      The JSON is immutable after the
   *              response instantiation.
   */
  private ResponseEntity<JsonNode> responseMessage(
    final Object value,
    final HttpStatus code
  ) {
    return response("message", value, code);
  }

  /**
   * Create a response of a JSON containing only the
   * "error" key and a given value.
   *
   * @param value Any JSON acceptable value.
   * @param code  Usually <code>4xx</code> and <code>5xx</code> codes,
   *              but <code>2xx</code> are acceptable as well.
   * @return      The JSON is immutable after the response instantiation.
   */
  private ResponseEntity<JsonNode> responseError(
    final Object value,
    final HttpStatus code
  ) {
    return response("error", value, code);
  }

  /**
   * Create a response of a JSON containing only the
   * given key and value.
   *
   * @param key   Any JSON acceptable key.
   * @param value Any JSON acceptable value.
   * @param code  Any HTTP code, usually <code>2xx</code>,
   *              <code>4xx</code> and <code>5xx</code> ones.
   * @return      The JSON is immutable after the
   *              response instantiation.
   */
  private ResponseEntity<JsonNode> response(
    final String key,
    final Object value,
    final HttpStatus code
  ) {
    final ObjectNode json = JsonNodeFactory.instance.objectNode();

    json.set(key, objectMapper.valueToTree(value));

    return new ResponseEntity<JsonNode>(json, code);
  }
  //#endregion
}
