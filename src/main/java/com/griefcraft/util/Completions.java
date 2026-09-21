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

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Completions {

    private static final List<String> LWC = Arrays.asList("create", "modify", "default", "unlock", "info", "limits", "remove", "mode", "flag", "admin");
    private static final List<String> ADMIN = Arrays.asList("view", "find", "forceowner", "remove", "purge", "cleanup", "version", "update", "report", "clear");
    private static final List<String> PROTECTION_TYPES = Arrays.asList("public", "private", "donation", "password", "display", "supply");
    private static final List<String> TOGGLES = Arrays.asList("on", "off");
    private static final List<String> FLAGS = Arrays.asList("redstone", "magnet", "exemption", "autoclose", "allowexplosions", "hopper", "hopperin", "hopperout", "golem");
    private static final List<String> DROPTRANSFER = Arrays.asList("select", "on", "off", "status");
    private static final List<String> REMOVE = Arrays.asList("protection", "modes");
    private static final List<String> MODES = Arrays.asList("persist", "nospam", "nolock", "droptransfer");
    private static final List<String> INTEGERS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    private static List<String> modifications = Arrays.asList("-", "@");

    public static List<String> lwc(String term) {
        return filter(LWC, term);
    }

    public static List<String> admin(String term) {
        return filter(ADMIN, term);
    }

    public static List<String> protectionTypes() {
        return PROTECTION_TYPES;
    }

    public static List<String> protectionTypes(String term) {
        return filter(PROTECTION_TYPES, term);
    }

    public static List<String> players(CommandSender requesting) {
        return Bukkit.getOnlinePlayers().stream().filter(p -> !(requesting instanceof Player) || ((Player) requesting).canSee(p)).map(HumanEntity::getName).collect(Collectors.toList());
    }

    public static List<String> players(String term, CommandSender requesting) {
        return filter(players(requesting), term);
    }

    public static List<String> toggles(String term) {
        return filter(TOGGLES, term);
    }

    public static List<String> flags(String term) {
        return filter(FLAGS, term);
    }

    public static List<String> droptransfer(String term) {
        return filter(DROPTRANSFER, term);
    }

    public static List<String> remove(String term) {
        return filter(REMOVE, term);
    }

    public static List<String> modes(String term) {
        return filter(MODES, term);
    }

    public static List<String> cmodify(String term, CommandSender requesting, boolean includeTypes) {
        List<String> players = players(requesting);
        List<String> suggestions = new ArrayList<>(players);
        if (includeTypes) {
            suggestions.addAll(PROTECTION_TYPES);
        }
        if (term.isEmpty()) {
            suggestions.addAll(modifications);
        } else {
            for (String player : players) {
                suggestions.add("-" + player);
                suggestions.add("@" + player);
            }
        }
        return filter(suggestions, term);
    }

    public static List<String> integers(String term) {
        return filter(INTEGERS, term);
    }

    private static List<String> filter(List<String> list, String term) {
        return list.stream().filter(e -> e.toLowerCase().startsWith(term.toLowerCase())).collect(Collectors.toList());
    }

}
