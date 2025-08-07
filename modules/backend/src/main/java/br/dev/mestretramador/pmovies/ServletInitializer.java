package br.dev.mestretramador.pmovies;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ...
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public class ServletInitializer extends SpringBootServletInitializer {
  /**
   * {@inheritDoc}
   */
  @Override
  protected SpringApplicationBuilder configure(
    final SpringApplicationBuilder application
  ) {
    return application.sources(PMoviesBackend.class);
  }
}
