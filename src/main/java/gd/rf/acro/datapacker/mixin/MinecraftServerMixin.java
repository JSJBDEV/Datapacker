package gd.rf.acro.datapacker.mixin;

import com.google.common.collect.Lists;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.WorldSaveHandler;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow public abstract Path getSavePath(WorldSavePath worldSavePath);


    @Shadow public abstract CompletableFuture<Void> reloadResources(Collection<String> datapacks);

    @Shadow @Final private ResourcePackManager dataPackManager;

    @Shadow @Final protected SaveProperties saveProperties;

    @Inject(at = @At("TAIL"), method = "createWorlds")
    private void init(WorldGenerationProgressListener progressListener,CallbackInfo info) {
        try {
            FileUtils.copyFile(new File(FabricLoader.INSTANCE.getConfigDirectory()+"/Datapacker/quests.zip"),new File(this.getSavePath(WorldSavePath.DATAPACKS).toString()+"/quests-copy.zip"));
            List<ResourcePackProfile> list = Lists.newArrayList(dataPackManager.getEnabledProfiles());
            reloadResources(findNewDataPacks(dataPackManager,saveProperties,dataPackManager.getEnabledNames()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //From ReloadCommand.class
    private static Collection<String> findNewDataPacks(ResourcePackManager dataPackManager, SaveProperties saveProperties, Collection<String> enabledDataPacks) {
        dataPackManager.scanPacks();
        Collection<String> collection = Lists.newArrayList((Iterable)enabledDataPacks);
        Collection<String> collection2 = saveProperties.getDataPackSettings().getDisabled();
        Iterator var5 = dataPackManager.getNames().iterator();

        while(var5.hasNext()) {
            String string = (String)var5.next();
            if (!collection2.contains(string) && !collection.contains(string)) {
                collection.add(string);
            }
        }

        return collection;
    }
}
