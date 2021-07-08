package gd.rf.acro.datapacker.mixin;

import gd.rf.acro.datapacker.ConfigUtils;
import gd.rf.acro.datapacker.Datapacker;
import net.minecraft.data.server.AdventureTabAdvancementGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {


    @Shadow @Final private MinecraftServer server;

    @Inject(at = @At("TAIL"), method = "onPlayerConnected")
    public void onPlayerConnected(ServerPlayerEntity player, CallbackInfo callbackInfo)
    {
        if(!player.getScoreboardTags().contains(ConfigUtils.config.get("sTag")))
        {
            player.giveItemStack(new ItemStack(Datapacker.QUEST_BOOK_ITEM));
            player.addScoreboardTag(ConfigUtils.config.get("sTag"));

        }

    }



}
