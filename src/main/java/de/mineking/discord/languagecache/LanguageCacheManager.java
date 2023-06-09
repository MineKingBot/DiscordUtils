package de.mineking.discord.languagecache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import de.mineking.discord.DiscordUtils;
import de.mineking.discord.Module;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;

import java.util.concurrent.TimeUnit;

public class LanguageCacheManager extends Module {
	private final Cache<Long, DiscordLocale> cache = Caffeine.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.build();

	private final DiscordLocale defaultLocale;

	public LanguageCacheManager(DiscordUtils manager, DiscordLocale defaultLocale) {
		super(manager);

		this.defaultLocale = defaultLocale;
	}

	public DiscordLocale getLocale(long user) {
		return cache.asMap().getOrDefault(user, defaultLocale);
	}

	@Override
	public void onGenericInteractionCreate(GenericInteractionCreateEvent event) {
		cache.put(event.getUser().getIdLong(), event.getUserLocale());
	}
}
