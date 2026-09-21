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
package com.griefcraft.cache;

import com.griefcraft.lwc.LWC;
import com.griefcraft.util.UUIDRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.command.CommandSender;

/**
 *
 */
public class DefaultsCache {
    private final LWC lwc;
    private final Map<CommandSender, String> cache;


    public DefaultsCache(LWC lwc) {
        this.lwc = lwc;
        cache = new HashMap<>();
    }

    /**
     * Gets the default value from the cache, or if not set in cache, returns it from the DB.
     * @param sender
     * @return
     */
    public String getOrLoad(CommandSender sender) {
        if (sender == null) {
            return null;
        }
        if(cache.containsKey(sender)) {
            return cache.get(sender);
        }
        UUID uuid = UUIDRegistry.getUUID(sender.getName());
        String data = lwc.getPhysicalDatabase().loadDefault(uuid.toString());
        cache.put(sender, data);
        return data;
    }

    public void set(CommandSender sender, String data) {
        cache.put(sender, data);
    }

    public void clear(CommandSender sender) {
        cache.remove(sender);
    }

}
