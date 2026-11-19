package net.mofusya.mechanical_ageing.handlers;

import net.minecraftforge.common.util.LazyOptional;
import net.mofusya.ornatelib.util.annotation.EverythingAreNonNullByDefault;

import java.util.Optional;

/**
 * A class to store both a handler and a lazy version of the handler.
 *
 * @param <HANDLER>   The main handler.
 * @param <I_HANDLER> The root interface of the handler.
 */
@EverythingAreNonNullByDefault
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalMasterHandler<HANDLER extends I_HANDLER, I_HANDLER> {
    /**
     * The stored optional handler.
     */
    private final Optional<HANDLER> handler;
    /**
     * The stored optional lazy handler.
     */
    private LazyOptional<I_HANDLER> lazyHandler;

    /**
     * A root constructor.
     *
     * @param handler The handler to store.
     */
    public OptionalMasterHandler(Optional<HANDLER> handler) {
        this.handler = handler;
        this.lazyHandler = LazyOptional.empty();
    }

    /**
     * A helper method to initialize the lazy handler.
     */
    public void initializeLazyHandler(){
        this.getOptionalHandler().ifPresent(handler -> this.setLazyHandler(LazyOptional.of(() -> handler)));
    }

    /**
     * A helper method to invalidate the lazy handler.
     */
    public void invalidateLazyHandler(){
        this.getOptionalHandler().ifPresent(handler -> this.getLazyHandler().invalidate());
    }

    //region Getters, helper getters, and setters.
    /**
     * A helper getter to get the raw handler.
     *
     * @return The raw handler of this.
     * @throws IllegalHandlerGetterException if the raw handler is not present.
     */
    public HANDLER getHandler() {
        if (this.getOptionalHandler().isPresent()) {
            return this.getOptionalHandler().get();
        }
        throw new IllegalHandlerGetterException();
    }

    /**
     * A getter for this {@code handler}.
     *
     * @return This {@code handler}/
     */
    public Optional<HANDLER> getOptionalHandler() {
        return handler;
    }

    /**
     * A getter for this {@code lazyHandler}.
     *
     * @return This {@code lazyHandler}.
     */
    public LazyOptional<I_HANDLER> getLazyHandler() {
        return lazyHandler;
    }

    /**
     * A setter for this {@code lazyHandler}.
     *
     * @param lazyHandler The new {@code LazyOptional<I_HANDLER>}.
     */
    public void setLazyHandler(LazyOptional<I_HANDLER> lazyHandler) {
        this.lazyHandler = lazyHandler;
    }
    //endregion
}
