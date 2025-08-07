package br.dev.mestretramador.pmovies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * ...
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public final class PMoviesBackend {
  /**
   * ...
   */
  private PMoviesBackend() { }

  /**
   * ...
   * @param args ...
   */
  public static void main(final String[] args) {
    SpringApplication.run(PMoviesBackend.class, args);
  }
}
