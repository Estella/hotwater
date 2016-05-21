package sorazodia.registryhelper;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Item registry class for Cannibalism mod, have methods to make item
 * registation in the GameRegistry a bit easier/less typing. THE init() METHOD
 * MUST BE CALLED FOR IT TO WORK
 * 
 * @author SoraZodia
 */
public class SimpleItemsRegistry
{

	private static CreativeTabs tabs;
	private static String modid;

	/**
	 * Intitizate the MODID and CreativeTabs variable for the rest of the class
	 * to use. This must be called before any other methods in this class or
	 * funny stuff may happen.
	 * 
	 * @param MOD_ID_String
	 * @param CreativeTabs
	 */
	public static void init(String modID, CreativeTabs tab)
	{
		tabs = tab;
		modid = modID;
	}

	/**
	 * A more steamline way to register your items, does all of the extra stuff
	 * for you, just make sure your item object is initializated
	 */
	public static void registerItems(Item item, String itemName, String imageName)
	{
		GameRegistry.registerItem(item, itemName);
		item.setCreativeTab(tabs).setUnlocalizedName(itemName);
		
		 if(FMLCommonHandler.instance().getSide().isClient())
	            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(modid + ":" + imageName, "inventory"));
	}

	/**
	 * This method is used when the item's name is the same as its texture name.
	 * Helps reduce repetivite typing #lazy:P. Please make sure your item object
	 * is initializated
	 */
	public static void registerItems(Item item, String name)
	{
		registerItems(item, name, name);
	}

}