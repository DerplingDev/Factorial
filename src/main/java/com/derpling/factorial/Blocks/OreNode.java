package com.derpling.factorial.Blocks;

import com.derpling.factorial.ModRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class OreNode extends Block {
    private final String resourceName;
    private final ArrayList<Block> blocks = new ArrayList<>();
    private final Item resourceItem;
    private final int color;

    public OreNode(Properties properties, String resourceName, List<String> blocks, String item, int color) {
        super(properties);
        this.resourceName = resourceName;
        for (String block : blocks) {
            this.blocks.add(BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(block)));
        }
        this.resourceItem = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(item));
        this.color = color;
    }

    public static void registerOreNode(@NotNull String resourceName, @NotNull ArrayList<Block> resourceBlocks, @NotNull Item resourceItem, int hexARGBcolor) {
        Properties prop = BlockBehaviour.Properties.of();

        float destroyTime = 5f;
        float explosionResistance = 50f;

        prop.strength(destroyTime, explosionResistance);
        prop.sound(SoundType.AMETHYST_CLUSTER);
        prop.lightLevel(state -> 9);

        List<String> blockNames = new ArrayList<>();
        for (Block block : resourceBlocks) {
            blockNames.add(BuiltInRegistries.BLOCK.getKey(block).toString());
        }

        String id = resourceName.replace(" ", "_").toLowerCase() + "_ore_node";
        DeferredBlock<OreNode> out = ModRegistry.BLOCKS.register(id, () -> new OreNode(prop, resourceName, blockNames, BuiltInRegistries.ITEM.getKey(resourceItem).toString(), hexARGBcolor));
        DeferredItem<OreNodeItem> blockItem = ModRegistry.ITEMS.register(id, () -> new OreNodeItem(out.get(), new Item.Properties()));

        ModRegistry.ORE_NODE_BLOCKS.add(out);
        ModRegistry.ORE_NODE_ITEMS.add(blockItem);
    }

    @Override
    public boolean canHarvestBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Player player) {
        for (Block block : blocks) {
            if (!block.canHarvestBlock(state, level, pos, player)) return false;
        }
        return super.canHarvestBlock(state, level, pos, player);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Stream.of(
                Block.box(6, 0, 6, 10, 12, 10),
                Block.box(10, 0, 6, 14, 8, 10),
                Block.box(2, 0, 6, 6, 8, 10),
                Block.box(10, 0, 10, 14, 4, 14),
                Block.box(2, 0, 2, 6, 4, 6),
                Block.box(6, 0, 2, 10, 10, 6),
                Block.box(2, 0, 10, 6, 10, 14),
                Block.box(10, 0, 2, 14, 6, 6),
                Block.box(6, 0, 10, 10, 6, 14)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    @Override
    public @NotNull MapCodec<OreNode> codec() {
        return ModRegistry.ORE_NODE_CODEC.value();
    }

    public String getResourceName() {
        return resourceName;
    }
    public ArrayList<String> getBlockIDs() {
        ArrayList<String> blocks = new ArrayList<>();
        for (Block block : this.blocks) {
            blocks.add(BuiltInRegistries.BLOCK.getKey(block).toString());
        }
        return blocks;
    }
    public String getItemID() {
        return BuiltInRegistries.ITEM.getKey(this.resourceItem).toString();
    }
    public ArrayList<Block> getOreBlocks() {
        return blocks;
    }
    public Item getResourceItem() {
        return this.resourceItem;
    }
    public void addOreBlock(Block block) {
        this.blocks.add(block);
    }
    public int getColor() {
        return color;
    }
}
