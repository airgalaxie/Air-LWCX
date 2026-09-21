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
package com.griefcraft.listeners;

import com.griefcraft.lwc.LWC;
import com.griefcraft.scripting.JavaModule;
import com.griefcraft.scripting.event.LWCProtectionDestroyEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LWCMCPCSupport extends JavaModule {

    /**
     * The shared multiplayer block id used by transport pipes
     */

    /**
     * The LWC object
     */
    private LWC lwc;

    /**
     * A set of blacklisted players that are blocked from destroying any blocks protected by LWC. Mainly useful for MCPC
     * where mods can remove blocks and try to break the block by sending an event first (e.g turtle)
     */
    private final Set<String> blacklistedPlayers = new HashSet<>();

    public LWCMCPCSupport(LWC lwc) {
        this.lwc = lwc;
        loadAndProcessConfig();
    }

    /**
     * Load and process the configuration
     */
    public void loadAndProcessConfig() {
        blacklistedPlayers.clear();

        for (String player : lwc.getConfiguration().getStringList("optional.blacklistedPlayers", new ArrayList<String>())) {
            blacklistedPlayers.add(player.toLowerCase());
        }
    }

    /**
     * Called when a protection is destroyed
     *
     * @param event
     */
    public void onDestroyProtection(LWCProtectionDestroyEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        String lowerPlayerName = player.getName().toLowerCase();

        if (blacklistedPlayers.contains(lowerPlayerName)) {
            event.setCancelled(true);
            lwc.sendLocale(player, "protection.accessdenied");
        }
    }

}
