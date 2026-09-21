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

import com.griefcraft.lwc.LWC;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class Updater {

    @SuppressWarnings("deprecation")
    public void init() {
        final LWC lwc = LWC.getInstance();
        if (lwc.getConfiguration().getBoolean("core.updateNotifier", true)) {
            lwc.getPlugin().getServer().getScheduler().scheduleAsyncDelayedTask(lwc.getPlugin(), () -> {
                Object[] updates = Updater.getLastUpdate();
                if (updates.length == 2) {
                    lwc.log("[LWCX] New update avaible:");
                    lwc.log("New version: " + updates[0]);
                    lwc.log(
                        "Your version: " + lwc.getPlugin().getDescription().getVersion());
                    lwc.log("What's new: " + updates[1]);
                }
            });
        }
    }

    final static String VERSION_URL = "https://api.spiget.org/v2/resources/69551/versions?size=" + Integer.MAX_VALUE
            + "&spiget__ua=SpigetDocs";
    final static String DESCRIPTION_URL = "https://api.spiget.org/v2/resources/69551/updates?size=" + Integer.MAX_VALUE
            + "&spiget__ua=SpigetDocs";

    public static Object[] getLastUpdate() {
        try {
            JsonArray versionsArray = readArray(VERSION_URL);
            String lastVersion = versionsArray.get(versionsArray.size() - 1).getAsJsonObject().get("name").getAsString();

            if (compareVersions(lastVersion, LWC.getInstance().getPlugin().getDescription().getVersion()) > 0) {
                JsonArray updatesArray = readArray(DESCRIPTION_URL);
                String updateName = updatesArray.get(updatesArray.size() - 1).getAsJsonObject().get("title").getAsString();

                Object[] update = {lastVersion, updateName};
                return update;
            }
        } catch (Exception e) {
            return new String[0];
        }

        return new String[0];
    }

    private static JsonArray readArray(String url) throws Exception {
        try (InputStreamReader reader = new InputStreamReader(
                URI.create(url).toURL().openStream(), StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonArray();
        }
    }

    private static int compareVersions(String left, String right) {
        String[] leftParts = left.split("[.-]");
        String[] rightParts = right.split("[.-]");
        int length = Math.max(leftParts.length, rightParts.length);
        for (int i = 0; i < length; i++) {
            int leftPart = i < leftParts.length ? parseVersionPart(leftParts[i]) : 0;
            int rightPart = i < rightParts.length ? parseVersionPart(rightParts[i]) : 0;
            if (leftPart != rightPart) {
                return Integer.compare(leftPart, rightPart);
            }
        }
        return 0;
    }

    private static int parseVersionPart(String part) {
        String digits = part.replaceFirst("^(\\d+).*$", "$1");
        return digits.matches("\\d+") ? Integer.parseInt(digits) : 0;
    }
}
