package gd.rf.acro.datapacker;

import com.mojang.brigadier.arguments.StringArgumentType;
import gd.rf.acro.datapacker.items.QuestBookItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.server.command.CommandManager.literal;

public class Datapacker implements ModInitializer {

	public static final Identifier SHARED = new Identifier("datapacker","shared_adv");
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		registerItems();
		registerCommands();
		ConfigUtils.checkConfigs();

		System.out.println("Data has been packed, I suppose");
	}

	public static final QuestBookItem QUEST_BOOK_ITEM = new QuestBookItem(new Item.Settings().group(ItemGroup.MISC));
	private void registerItems()
	{
		Registry.register(Registry.ITEM,new Identifier("datapacker","quest_book"),QUEST_BOOK_ITEM);
	}

	private void registerCommands()
	{
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(literal("bands")
					.then(literal("create").executes(context ->
					{
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.makeBand((PlayerEntity) entity, context.getSource());
							return 1;
						}
						return 0;
					}))
					.then(literal("invite").then(CommandManager.argument("player", EntityArgumentType.player()).executes(context ->
					{
						PlayerEntity recruit = EntityArgumentType.getPlayer(context,"player");
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.inviteToBand((PlayerEntity) entity,recruit, context.getSource());
							return 1;
						}
						return 0;
					})))
					.then(literal("leave").executes(context ->
					{
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.leaveBand((PlayerEntity) entity, context.getSource());

							return 1;
						}
						return 0;
					}))
					.then(literal("join").then(CommandManager.argument("band", StringArgumentType.greedyString()).executes(context ->
					{
						String band = StringArgumentType.getString(context,"band");
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.joinBand((PlayerEntity) entity,band, context.getSource());
							return 1;
						}
						return 0;
					})))
					.then(literal("reject").then(CommandManager.argument("band", StringArgumentType.greedyString()).executes(context ->
					{
						String band = StringArgumentType.getString(context,"band");
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.rejectBand((PlayerEntity) entity,band,context.getSource());
							return 1;
						}
						return 0;
					})))
					.then(literal("kick").then(CommandManager.argument("player", EntityArgumentType.player()).executes(context ->
					{
						PlayerEntity recruit = EntityArgumentType.getPlayer(context,"player");
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.kickFromBand((PlayerEntity) entity,recruit, context.getSource());
							return 1;
						}
						return 0;
					})))
					.then(literal("invites").executes(context ->
					{
						Entity entity = context.getSource().getEntity();
						if(entity instanceof PlayerEntity)
						{
							BandSystem.getInvitesFor((PlayerEntity) entity).forEach(invite->context.getSource().sendFeedback(new LiteralText(invite),false));

							return 1;
						}
						return 0;
					}))
			);
		});
	}
}
