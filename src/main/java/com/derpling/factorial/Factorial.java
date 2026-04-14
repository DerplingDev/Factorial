package com.derpling.factorial;

import com.derpling.factorial.Blocks.OreNodeBlockStateProvider;
import com.derpling.factorial.Items.OreNodeItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Factorial.MODID)
public class Factorial {
    public static final String MODID = "factorial";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Factorial(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherDataEvent);

        ModRegistry.registerRegistries(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Factorizing with Factorial!");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Factorial is industrializing this server!");
    }

    public void gatherDataEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(
                event.includeClient(),
                new OreNodeBlockStateProvider(output, existingFileHelper)
        );

        generator.addProvider(
                event.includeClient(),
                new OreNodeItemModelProvider(output, existingFileHelper)
        );

        generator.addProvider(
                event.includeClient(),
                new OreNodeLanguageProvider(output)
        );
    }
}
