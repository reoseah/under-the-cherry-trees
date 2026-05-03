package io.github.reoseah.utct;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;
import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.leavesProperties;
import static net.minecraft.world.level.block.Blocks.logProperties;

public class UTCT implements ModInitializer {
    public static final String MOD_ID = "utct";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Blocks.initialize();
        Items.initialize();
    }

    public static Identifier modId(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static class Blocks {
        public static final Block RED_CHERRY_LEAVES = register("red_cherry_leaves", properties ->
                        new TintedParticleLeavesBlock(0.01F, properties),
                leavesProperties(SoundType.GRASS)
        );
        public static final Block CHERRY_BRANCHES = register("cherry_branches", properties ->
                        new TintedParticleLeavesBlock(0.01F, properties),
                leavesProperties(SoundType.GRASS)
        );
        public static final Block SUSPICIOUS_CHERRY_LOG = register("suspicious_cherry_log", RotatedPillarBlock::new,
                logProperties(MapColor.WOOD, MapColor.PODZOL, SoundType.WOOD));

        public static void initialize() {
        }

        private static Block register(String name, Function<BlockBehaviour.Properties, Block> constructor, BlockBehaviour.Properties properties) {
            var id = modId(name);
            properties.setId(ResourceKey.create(Registries.BLOCK, id));
            return Registry.register(BuiltInRegistries.BLOCK, id, constructor.apply(properties));
        }
    }

    public static class Items {
        public static final Item RED_CHERRY_LEAVES = register(Blocks.RED_CHERRY_LEAVES);
        public static final Item CHERRY_BRANCHES = register(Blocks.CHERRY_BRANCHES);
        public static final Item SUSPICIOUS_CHERRY_LOG = register(Blocks.SUSPICIOUS_CHERRY_LOG);

        private static void initialize() {
            CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS) //
                    .register(group -> {
                        group.accept(RED_CHERRY_LEAVES);
                        group.accept(CHERRY_BRANCHES);
                        group.accept(SUSPICIOUS_CHERRY_LOG);
                    });
        }

        private static Item register(Block block) {
            return register(block, BlockItem::new);
        }

        private static Item register(Block block, BiFunction<Block, Item.Properties, Item> constructor) {
            return register(block, constructor, new Item.Properties());
        }

        @SuppressWarnings("deprecation")
        private static Item register(Block block, BiFunction<Block, Item.Properties, Item> constructor, Item.Properties properties) {
            var id = block.builtInRegistryHolder().key().identifier();
            properties.setId(ResourceKey.create(Registries.ITEM, id));
            properties.useBlockDescriptionPrefix();
            return Registry.register(BuiltInRegistries.ITEM, id, constructor.apply(block, properties));
        }

        private static Item register(String name) {
            return register(name, Item::new);
        }

        private static Item register(String name, Function<Item.Properties, Item> constructor) {
            return register(name, constructor, new Item.Properties());
        }

        private static Item register(String name, Item.Properties properties) {
            return register(name, Item::new, properties);
        }

        private static Item register(String name, Function<Item.Properties, Item> constructor, Item.Properties properties) {
            var id = modId(name);
            properties.setId(ResourceKey.create(Registries.ITEM, id));
            return Registry.register(BuiltInRegistries.ITEM, id, constructor.apply(properties));
        }
    }
}