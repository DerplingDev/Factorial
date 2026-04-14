package com.derpling.factorial;

import com.derpling.factorial.Blocks.OreNode;
import com.derpling.factorial.Blocks.OreNodeItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

@EventBusSubscriber(Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerBlockColorsEvent(RegisterColorHandlersEvent.Block event) {
        for (DeferredBlock<OreNode> node : ModRegistry.ORE_NODE_BLOCKS) {
            event.register((state, level, pos, tintIndex) -> node.value().getColor(), node.value());
        }
    }

    @SubscribeEvent
    public static void registerItemColorsEvent(RegisterColorHandlersEvent.Item event) {
        for (DeferredItem<OreNodeItem> node : ModRegistry.ORE_NODE_ITEMS) {
            event.register(((itemStack, index) -> {
                if (node.value().getBlock() instanceof OreNode oreNodeBlock) {
                    return oreNodeBlock.getColor();
                }
                return 0xFFFFFFFF;
            }), node.value());
        }
    }
}
