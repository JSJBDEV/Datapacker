package gd.rf.acro.datapacker.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class QuestBookItem extends Item {
    public QuestBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient)
        {
            MinecraftClient.getInstance().openScreen(new AdvancementsScreen(MinecraftClient.getInstance().getNetworkHandler().getAdvancementHandler()));
        }
        return super.use(world, user, hand);
    }
}
