package gd.rf.acro.datapacker;

import gd.rf.acro.datapacker.items.QuestBookItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Datapacker implements ModInitializer {
	public static final QuestBookItem QUEST_BOOK_ITEM = new QuestBookItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Identifier SHARED = new Identifier("datapacker","shared_adv");
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM,new Identifier("datapacker","quest_book"),QUEST_BOOK_ITEM);
		System.out.println("Hello Fabric world!");
	}
}
