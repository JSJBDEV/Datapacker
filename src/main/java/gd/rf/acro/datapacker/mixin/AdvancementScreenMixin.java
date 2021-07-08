package gd.rf.acro.datapacker.mixin;

import gd.rf.acro.datapacker.Datapacker;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementScreenMixin {



    @Shadow @Final private Map<Advancement, AdvancementTab> tabs;

    @Shadow @Final private ClientAdvancementManager advancementHandler;

    @Inject(at = @At("TAIL"), method = "init()V")
    protected void init(CallbackInfo callbackInfo)
    {
        if(MinecraftClient.getInstance().player.getMainHandStack().getItem()== Datapacker.QUEST_BOOK_ITEM)
        {
            Optional<AdvancementTab> questTab = tabs.values().stream().filter(s->s.getRoot().getId().equals(new Identifier("quest","root"))).findFirst();
            questTab.ifPresent(advancementTab -> this.advancementHandler.selectTab(advancementTab.getRoot(), true));
        }

    }
}
