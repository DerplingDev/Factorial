package com.derpling.factorial.Items;

import com.derpling.factorial.Blocks.OreNodeItem;
import com.derpling.factorial.Factorial;
import com.derpling.factorial.ModRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class OreNodeItemModelProvider extends ItemModelProvider {

    public OreNodeItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Factorial.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (DeferredItem<OreNodeItem> item : ModRegistry.ORE_NODE_ITEMS) {
            withExistingParent(item.getId().toString(), modLoc("block/ore_node"));
        }
    }
}
