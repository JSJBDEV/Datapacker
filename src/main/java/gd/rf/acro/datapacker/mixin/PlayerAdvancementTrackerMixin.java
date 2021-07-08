package gd.rf.acro.datapacker.mixin;

import gd.rf.acro.datapacker.ConfigUtils;
import gd.rf.acro.datapacker.Datapacker;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {

    @Shadow @Final private PlayerManager playerManager;

    @Shadow private ServerPlayerEntity owner;

    @Inject(at = @At("TAIL"), method = "grantCriterion")
    public void grantCriterion(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir)
    {
        if(this.owner.getScoreboardTeam()!=null && ConfigUtils.config.get("shouldTeamsShare").equals("true"))
        {
            NbtCompound store =this.owner.server.getDataCommandStorage().get(Datapacker.SHARED);

            NbtList adv = (NbtList) store.get(this.owner.getScoreboardTeam().getName());
            if(adv==null)
            {
                adv=new NbtList();
            }
            for(NbtElement s:adv)
            {
                NbtCompound aa = (NbtCompound) s;
                if(aa.getString("Identifier").equals(advancement.getId().toString()))
                {
                    if(aa.getString("Criteria").equals(criterionName))
                    {
                        return;
                    }
                }
            }

            NbtCompound anAdv = new NbtCompound();
            anAdv.putString("Identifier",advancement.getId().toString());
            anAdv.putString("Criteria",criterionName);
            adv.add(anAdv);
            store.put(this.owner.getScoreboardTeam().getName(),adv);
            this.owner.server.getDataCommandStorage().set(Datapacker.SHARED,store);
        }
    }
}
