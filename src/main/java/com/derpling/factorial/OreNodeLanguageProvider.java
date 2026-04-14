package com.derpling.factorial;

import com.derpling.factorial.Blocks.OreNode;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;

public class OreNodeLanguageProvider extends LanguageProvider {

    public OreNodeLanguageProvider(PackOutput output) {
        super(output, Factorial.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (DeferredBlock<OreNode> node : ModRegistry.ORE_NODE_BLOCKS) {
            addBlock(node, node.get().getResourceName() + " Ore Node");
        }
    }
}
