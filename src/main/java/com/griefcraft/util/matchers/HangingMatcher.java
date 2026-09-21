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
package com.griefcraft.util.matchers;

import com.griefcraft.util.ProtectionFinder;
import com.griefcraft.util.VersionUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 * Matches hanging blocks
 */
public class HangingMatcher implements ProtectionFinder.Matcher {
    public static final Set<Material> PROTECTABLES_HANGING = EnumSet.noneOf(Material.class);

    static {
        if (VersionUtil.isAtLeast(1, 20)) {
            PROTECTABLES_HANGING.addAll(EnumSet.of(Material.OAK_HANGING_SIGN, Material.BIRCH_HANGING_SIGN,
                    Material.SPRUCE_HANGING_SIGN, Material.JUNGLE_HANGING_SIGN, Material.ACACIA_HANGING_SIGN,
                    Material.DARK_OAK_HANGING_SIGN, Material.CRIMSON_HANGING_SIGN, Material.WARPED_HANGING_SIGN,
                    Material.MANGROVE_HANGING_SIGN, Material.BAMBOO_HANGING_SIGN, Material.CHERRY_HANGING_SIGN));
        }
        if (VersionUtil.isAtLeast(1, 21, 4)) {
            Optional.ofNullable(Material.getMaterial("PALE_OAK_HANGING_SIGN")).ifPresent(PROTECTABLES_HANGING::add);
        }
        if (VersionUtil.isAtLeast(26, 3)) {
            Optional.ofNullable(Material.getMaterial("POPLAR_HANGING_SIGN")).ifPresent(PROTECTABLES_HANGING::add);
        }
    }

    @Override
    public boolean matches(ProtectionFinder finder) {
        // Nothing to match
        if (PROTECTABLES_HANGING.isEmpty()) {
            return false;
        }

        final Block block = finder.getBaseBlock().getBlock();

        boolean found = false;
        Block below = block;
        while (PROTECTABLES_HANGING.contains((below = below.getRelative(BlockFace.DOWN)).getType())) {
            finder.addBlock(below);
            found = true;
        }

        return found;
    }

}
