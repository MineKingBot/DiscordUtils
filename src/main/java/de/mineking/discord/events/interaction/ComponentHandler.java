package de.mineking.discord.events.interaction;

import de.mineking.discord.events.EventHandlerBase;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ComponentHandler<T extends GenericComponentInteractionCreateEvent> extends EventHandlerBase<T> {
	private final Predicate<T> filter;
	private final Consumer<T> handler;

	public ComponentHandler(Class<T> type, Predicate<T> filter, Consumer<T> handler) {
		super(type);

		this.filter = filter;
		this.handler = handler;
	}

	public ComponentHandler(Class<T> type, String id, Consumer<T> handler) {
		this(type, event -> event.getComponentId().matches(id), handler);
	}

	@Override
	public void handleEvent(T event) {
		if(filter.test(event)) {
			handler.accept(event);
		}
	}
}
