package br.dev.mestretramador.pmovies.util.enumerable;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Contract to a <code>String</code> enum able to parse
 * a <code>String</code> into one of its entries.
 *
 * @since 0.0.1
 * @author Mestre-Tramador
 */
public interface EnumerableStringParser {
  /**
   * Coercion method to avoid relying on
   * {@link Object#toString()} or <code>null</code> values.
   *
   * @return A valid <code>String</code> representing the enum entry.
   */
  @NonNull
  String toParseString();

  /**
   * Parse a given <code>String</code> if
   * it is equal to one of the enum entries.
   *
   * @param <Enumerable>    An enum implementing this interface.
   * @param enumerableClass The given enum as a class.
   * @param string          A <code>String</code> to attempt to parse.
   * @return                If not results match, <code>null</code> is
   *                        returned instead.
   */
  @Nullable
  static <Enumerable extends Enum<?> & EnumerableStringParser>
  Enumerable parseString(
    @NonNull Class<Enumerable> enumerableClass,
    @NonNull String string
  ) {
    for (Enumerable entry : enumerableClass.getEnumConstants()) {
      if (entry.toParseString().equals(string)) {
        return entry;
      }
    }

    return null;
  }
}
