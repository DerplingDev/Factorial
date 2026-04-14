package com.derpling.factorial.Blocks;

import com.derpling.factorial.Factorial;
import com.derpling.factorial.ModRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class OreNodeBlockStateProvider extends BlockStateProvider {

    public OreNodeBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Factorial.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile.ExistingModelFile model = models().getExistingFile(modLoc("block/ore_node"));
        for (DeferredBlock<OreNode> block : ModRegistry.ORE_NODE_BLOCKS) {

            VariantBlockStateBuilder stateBuilder = getVariantBuilder(block.get());
            VariantBlockStateBuilder.PartialBlockstate partialState = stateBuilder.partialState();

            stateBuilder.addModels(partialState, partialState.modelForState()
                    .modelFile(model).build());


        }
    }
}
