package net.mofusya.mechanical_ageing.util;

import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

/**
 * A {@code recird} for storing 3 simple final fields.
 * @param first The first field.
 * @param second The second field.
 * @param third The third field.
 * @param <T> The type of class to storing.
 */
@EverythingAreNonNullByDefault
public record TripleEntry<T>(T first, T second, T third) {
}
