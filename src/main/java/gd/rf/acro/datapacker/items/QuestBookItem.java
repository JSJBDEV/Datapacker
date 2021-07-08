package gd.rf.acro.datapacker.items;

import gd.rf.acro.datapacker.Datapacker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
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
        else
        {
            if(user.getScoreboardTeam()!=null)
            {
                NbtCompound store = user.getServer().getDataCommandStorage().get(Datapacker.SHARED);
                NbtList adv = (NbtList) store.get(user.getScoreboardTeam().getName());
                if(adv==null)
                {
                    return super.use(world, user, hand);
                }
                ServerPlayerEntity playerEntity = (ServerPlayerEntity) user;
                for(NbtElement anAdv:adv)
                {
                    NbtCompound got = (NbtCompound) anAdv;
                    playerEntity.getAdvancementTracker().grantCriterion(playerEntity.server
                            .getAdvancementLoader()
                            .get(Identifier.tryParse(got.getString("Identifier"))),got.getString("Criteria"));
                }
            }
        }
        return super.use(world, user, hand);
    }
}
