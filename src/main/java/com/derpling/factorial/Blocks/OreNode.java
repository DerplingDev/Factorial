package com.derpling.factorial.Blocks;

import com.derpling.factorial.ModRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class OreNode extends Block {
    private final String prefix;
    private final String blockName;
    private final String resourceName;
    private final int color;

    public OreNode(Properties properties, String prefix, String blockName, String resourceName, int color) {
        super(properties);
        this.prefix = prefix;
        this.blockName = blockName;
        this.resourceName = resourceName;
        this.color = color;
    }

    @Override
    public @NotNull MapCodec<OreNode> codec() {
        return ModRegistry.ORE_NODE_CODEC.value();
    }

    public String getPrefix() {
        return prefix;
    }

    public String getBlockName() {
        return blockName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public int getColor() {
        return color;
    }
}
