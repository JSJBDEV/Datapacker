package gd.rf.acro.datapacker.mixin;

import gd.rf.acro.datapacker.Datapacker;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(at = @At("TAIL"), method = "onPlayerConnected")
    public void onPlayerConnected(ServerPlayerEntity player, CallbackInfo callbackInfo)
    {
        if(!player.getScoreboardTags().contains("dp_new"))
        {
            player.giveItemStack(new ItemStack(Datapacker.QUEST_BOOK_ITEM));
            player.addScoreboardTag("dp_new");
        }
    }


}
