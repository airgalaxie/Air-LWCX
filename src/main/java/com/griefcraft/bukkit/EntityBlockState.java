/*
 * Copyright 2011 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */
package com.griefcraft.bukkit;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("deprecation")
public class EntityBlockState implements BlockState {
    private static EntityBlock entityBlock;

    public EntityBlockState(EntityBlock entityBlock) {
        EntityBlockState.entityBlock = entityBlock; // TODO: Should do deep copy.
    }

    public static EntityBlock getEntityBlock() {
        return EntityBlockState.entityBlock;
    }

    public void setEntityBlock(EntityBlock entityBlock) {
        EntityBlockState.entityBlock = entityBlock;
    }

    @Override
    public Block getBlock() {
        return entityBlock;
    }

    @Override
    public Material getType() {
        return entityBlock.getType();
    }

    @Override
    public byte getLightLevel() {
        return entityBlock.getLightLevel();
    }

    @Override
    public World getWorld() {
        return entityBlock.getWorld();
    }

    @Override
    public int getX() {
        return entityBlock.getX();
    }

    @Override
    public int getY() {
        return entityBlock.getY();
    }

    @Override
    public int getZ() {
        return entityBlock.getZ();
    }

    @Override
    public Location getLocation() {
        return entityBlock.getLocation();
    }

    @Override
    public Location getLocation(Location location) {
        return entityBlock.getLocation(); // TODO: What to do with param location
    }

    @Override
    public Chunk getChunk() {
        return entityBlock.getChunk();
    }

    @Override
    public void setData(MaterialData materialData) {
        // Yeah, this does not work: entityBlock.setData(materialData.getData());
        // TODO: What to do with it, deprecated?
    }

    @Override
    public void setType(Material material) {
        entityBlock.setType(material);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean update(boolean b) {
        return false;
    }

    @Override
    public boolean update(boolean b, boolean b1) {
        return false;
    }

    @Override
    public byte getRawData() {
        throw new IllegalStateException("getRawData should not be called.");
    }

    @Override
    public void setRawData(byte b) {
        throw new IllegalStateException("setRawData should not be called.");
    }

    @Override
    public boolean isPlaced() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public @NotNull @Unmodifiable Collection<ItemStack> getDrops(@Nullable ItemStack itemStack, @Nullable Entity entity) {
        return List.of();
    }

    @Override
    public boolean isSuffocating() {
        return false;
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {

    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return null;
    }

    @Override
    public boolean hasMetadata(String s) {
        return false;
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {

    }

    @Override
    public BlockData getBlockData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BlockState copy() {
        return null;
    }

    @Override
    public BlockState copy(Location l) {
        return null;
    }

    @Override
    public void setBlockData(BlockData arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public MaterialData getData() {
        // TODO Auto-generated method stub
        return null;
    }
}
