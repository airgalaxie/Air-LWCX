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
package com.griefcraft.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.SideChaining;
import org.bukkit.block.data.type.Shelf;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConnectedShelves {
    private static final boolean CAN_USE = classExists();

    private static boolean classExists() {
        try {
            Class.forName("org.bukkit.block.data.type.Shelf");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean canUse() {
        return CAN_USE;
    }

    public static List<Block> connectedShelves(Block block) {
        if (!(block.getBlockData() instanceof Shelf shelf)) {
            return List.of();
        }

        final List<Block> list = new ArrayList<>(3);
        list.add(block);

        switch (shelf.getSideChain()) {
            case UNCONNECTED -> {
            }
            case RIGHT -> {
                final Block center = adjacent(block, Direction.LEFT);
                if (center != null) {
                    list.add(center);
                    final Block left = adjacent(center, Direction.LEFT);
                    if (left != null) {
                        list.add(left);
                    }
                }
            }
            case CENTER -> {
                final Block left = adjacent(block, Direction.LEFT);
                if (left != null) {
                    list.add(left);
                }
                final Block right = adjacent(block, Direction.RIGHT);
                if (right != null) {
                    list.add(right);
                }
            }
            case LEFT -> {
                final Block center = adjacent(block, Direction.RIGHT);
                if (center != null) {
                    list.add(center);
                    final Block right = adjacent(center, Direction.RIGHT);
                    if (right != null) {
                        list.add(right);
                    }
                }
            }
        }

        return list;
    }

    private enum Direction {LEFT, RIGHT}

    private static @Nullable Block adjacent(@Nullable Block block, Direction direction) {
        if (block == null || !(block.getBlockData() instanceof Directional directional)) {
            return null;
        }
        final BlockFace adjacentFace = switch (directional.getFacing()) {
            case NORTH -> direction == Direction.LEFT ? BlockFace.EAST : BlockFace.WEST;
            case SOUTH -> direction == Direction.LEFT ? BlockFace.WEST : BlockFace.EAST;
            case EAST -> direction == Direction.LEFT ? BlockFace.SOUTH : BlockFace.NORTH;
            case WEST -> direction == Direction.LEFT ? BlockFace.NORTH : BlockFace.SOUTH;
            default -> null;
        };
        if (adjacentFace != null) {
            final Block adjacent = block.getRelative(adjacentFace);
            if (adjacent.getBlockData() instanceof Shelf shelf && shelf.getSideChain() != SideChaining.ChainPart.UNCONNECTED) {
                return adjacent;
            }
        }
        return null;
    }
}
