package br.dev.mestretramador.pmovies.config;

import java.util.Objects;

/**
 * Subdomains to be used on OMDb API requests.
 *
 * @since                       0.0.1
 * @author                      Mestre-Tramador
 * @param data                  Subhost for Data API requests.
 * @param poster                Subhost for Poster API requests.
 * @throws NullPointerException If any data given is <code>null</code>.
 */
public record OMDbAPISubHosts(String data, String poster) {
  /**
   * The subdomains values cannot be <code>null</code>.
   */
  public OMDbAPISubHosts {
    Objects.requireNonNull(data);
    Objects.requireNonNull(poster);
  }

  /**
   * Read the subdomain of Data API requests.
   *
   * @return It's a valid URL subdomain.
   */
  public String data() {
    return data;
  }

  /**
   * Read the subdomain of Poster API requests.
   *
   * @return It's a valid URL subdomain.
   */
  public String poster() {
    return poster;
  }
}
