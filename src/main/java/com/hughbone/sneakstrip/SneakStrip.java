package com.hughbone.sneakstrip;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class SneakStrip implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        UseBlockCallback.EVENT.register(((playerEntity, world, hand, blockHitResult) -> {

            ItemStack stack = playerEntity.getStackInHand(hand);
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);

            if (!playerEntity.isSneaking()) {
                if (stack.getItem() instanceof ToolItem) {
                    if (AxeItem.STRIPPED_BLOCKS.containsKey(blockState.getBlock())) {
                        return ActionResult.FAIL;
                    }
                }

                if (stack.getItem() instanceof ShovelItem) {
                    if (ShovelItem.PATH_STATES.containsKey(blockState.getBlock())) {
                        return ActionResult.FAIL;
                    }
                }
            }

            return ActionResult.PASS;
        }));
    }
}