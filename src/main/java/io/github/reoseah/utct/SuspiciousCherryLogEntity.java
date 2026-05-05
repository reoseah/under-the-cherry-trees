package io.github.reoseah.utct;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SuspiciousCherryLogEntity extends RandomizableContainerBlockEntity {
    public static final int CONTAINER_SIZE = 9;
    private static final Component DEFAULT_NAME = Component.translatable("block.utct.suspicious_cherry_log");
    private NonNullList<ItemStack> items;

    protected SuspiciousCherryLogEntity(BlockPos worldPosition, BlockState blockState) {
        super(UTCT.BlockEntityTypes.SUSPICIOUS_CHERRY_LOG, worldPosition, blockState);
        this.items = NonNullList.withSize(9, ItemStack.EMPTY);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    protected void loadAdditional(final ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.items);
        }

    }

    protected void saveAdditional(final ValueOutput output) {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.items);
        }

    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(final NonNullList<ItemStack> items) {
        this.items = items;
    }

    protected AbstractContainerMenu createMenu(final int containerId, final Inventory inventory) {
        return new DispenserMenu(containerId, inventory, this);
    }
}
