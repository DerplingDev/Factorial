package com.derpling.factorial;

import com.derpling.factorial.Blocks.OreNode;
import com.derpling.factorial.Blocks.OreNodeItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;

public class ModRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Factorial.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Factorial.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Factorial.MODID);
    public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, Factorial.MODID);

    public static final ArrayList<DeferredBlock<OreNode>> ORE_NODE_BLOCKS = new ArrayList<>();
    public static final ArrayList<DeferredItem<OreNodeItem>> ORE_NODE_ITEMS = new ArrayList<>();

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FACTORIAL_TAB = TABS.register("factorial_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.factorial"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(Items.REDSTONE::getDefaultInstance)
            .displayItems((parameters, output) -> {
                for (DeferredItem<OreNodeItem> oreNode : ORE_NODE_ITEMS) {
                    output.accept(oreNode.toStack());
                }
            }).build());

    public static void registerRegistries(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);

        registerOreNodes();
    }

    public static final DeferredHolder<MapCodec<? extends Block>, MapCodec<OreNode>> ORE_NODE_CODEC = BLOCK_TYPES.register(
            "ore_node",
            () -> RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            BlockBehaviour.propertiesCodec(),
                            Codec.STRING.fieldOf("resourceName").forGetter(OreNode::getResourceName),
                            Codec.list(Codec.STRING).fieldOf("blocks").forGetter(OreNode::getBlockIDs),
                            Codec.STRING.fieldOf("items").forGetter(OreNode::getItemID),
                            Codec.INT.fieldOf("color").forGetter(OreNode::getColor)
                    ).apply(instance, OreNode::new)
            )
    );

    public static void registerOreNodes() {
        OreNode.registerOreNode("Coal", list(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE), Items.COAL, 0xFF505060);
        OreNode.registerOreNode("Iron", list(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE), Items.RAW_IRON, 0xFFFEDEC8);
    }

    @SafeVarargs
    public static <E> ArrayList<E> list(E... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }
}
