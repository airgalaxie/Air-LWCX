/*
 * Copyright evilmidget38. Obtained from: https://gist.github.com/evilmidget38/a5c971d2f2b2c3b3fb37
 * I (Hidendra) have made minor changes (mainly removing unused code)
 */

package com.griefcraft.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class NameFetcher implements Callable<Map<UUID, String>> {
    private static final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private final List<UUID> uuids;

    public NameFetcher(List<UUID> uuids) {
        this.uuids = ImmutableList.copyOf(uuids);
    }

    public Map<UUID, String> call() throws Exception {
        Map<UUID, String> uuidStringMap = new HashMap<>();
        for (UUID uuid : uuids) {
            HttpURLConnection connection = (HttpURLConnection) new URL(PROFILE_URL + uuid.toString().replace("-", "")).openConnection();
            connection.setConnectTimeout(10000);
            JsonObject response;
            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                response = JsonParser.parseReader(reader).getAsJsonObject();
            }
            String cause = getString(response, "cause");
            String errorMessage = getString(response, "errorMessage");
            if (cause != null && !cause.isEmpty()) {
                throw new IllegalStateException(errorMessage);
            }
            String name = getString(response, "name");
            if (name == null) {
                continue;
            }
            uuidStringMap.put(uuid, name);
        }
        return uuidStringMap;
    }

    private static String getString(JsonObject object, String key) {
        return object.has(key) && !object.get(key).isJsonNull() ? object.get(key).getAsString() : null;
    }
}
