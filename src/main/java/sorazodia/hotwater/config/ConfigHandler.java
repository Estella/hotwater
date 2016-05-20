package sorazodia.hotwater.config;

import java.util.ArrayList;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import org.apache.logging.log4j.Logger;

import sorazodia.hotwater.mechanics.EffectManager;

public class ConfigHandler
{
	public static Configuration config;

	private static boolean enableSuperLava = false;
	private static int biomeID = 50;

	private static String[] potionList = {};
	private static ArrayList<String> invalidEntry = new ArrayList<>();

	private static Logger log;
	
	private enum ListType
	{
		WHITELIST,
		BLACKLIST;
	}

	public ConfigHandler(FMLPreInitializationEvent event, Logger log)
	{
		config = new Configuration(event.getSuggestedConfigurationFile());
		ConfigHandler.log = log;
		syncConfig();
	}

	public void syncConfig()
	{
		addToIDList(config.getStringList("Potion Clear List", Configuration.CATEGORY_GENERAL, potionList, "Id to the potion effect that will be removed via hot spring"), ListType.BLACKLIST);
		biomeID = config.getInt("BiomeID For Hot Springs", Configuration.CATEGORY_GENERAL, 50, 40, 128, "The ID for the Hot Springs Biome [Require MC to be restarted]");
		enableSuperLava = config.getBoolean("Enable Super Lava", Configuration.CATEGORY_GENERAL, false, "If you want crazy lava in your world [Require MC to be restarted]");
		if (config.hasChanged())
			config.save();
	}

	private static void addToIDList(String[] stringList, ListType type)
	{
        boolean valid = false;
        
		for (String id : stringList)
		{
			switch (type)
			{
			case WHITELIST:
				valid = EffectManager.addToWhitelist(id);
				break;
			case BLACKLIST:
				valid = EffectManager.addToBlacklist(id);
				break;
			}
			
			if (valid == false)
			{
				log.info(id + " is not a valid entry");
				if (!invalidEntry.contains(id))
					invalidEntry.add(id);
			}
		}
	}

	public static boolean enableSuperLava()
	{
		return enableSuperLava;
	}

	public static int getBiomeID()
	{
		return biomeID;
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent config)
	{
		syncConfig();
	}

	@SubscribeEvent
	public void alertPlayer(PlayerLoggedInEvent joinEvent)
	{
		if (invalidEntry.size() > 0)
			joinEvent.player.addChatComponentMessage(new ChatComponentTranslation("[Hot Water] %s is not a valid number", invalidEntry.toString()));
	}

}
