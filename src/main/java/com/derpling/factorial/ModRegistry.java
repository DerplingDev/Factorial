package com.derpling.factorial;

import com.derpling.factorial.Blocks.OreNode;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class ModRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Factorial.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Factorial.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Factorial.MODID);
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, Factorial.MODID);

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FACTORIAL_TAB = TABS.register("factorial_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.factorial")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            /*.icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())*/
            .displayItems((parameters, output) -> {
                /*output.accept(EXAMPLE_ITEM.get());*/
            }).build());

    public static void registerRegistries(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
    }

    // Ore Node
    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<OreNode>> ORE_NODE_CODEC = BLOCK_TYPES.register(
            "ore_node",
            () -> RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            BlockBehaviour.propertiesCodec(),
                            Codec.STRING.fieldOf("prefix").forGetter(OreNode::getPrefix),
                            Codec.STRING.fieldOf("blockName").forGetter(OreNode::getBlockName),
                            Codec.STRING.fieldOf("resourceName").forGetter(OreNode::getResourceName),
                            Codec.INT.fieldOf("color").forGetter(OreNode::getColor)
                    ).apply(instance, OreNode::new)
            )
    );
}
