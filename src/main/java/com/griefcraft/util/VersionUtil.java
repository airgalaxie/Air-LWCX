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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class VersionUtil {
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?");

    public static boolean isAtLeast(final int major, final int minor, final int patch) {
        final String[] versionParts = getVersionParts(Bukkit.getVersion());
        final int majorPart = Integer.parseInt(versionParts[0]);
        if (majorPart > major) {
            return true;
        }
        final int minorPart = Integer.parseInt(versionParts[1]);
        if (majorPart == major && minorPart > minor) {
            return true;
        }
        final int patchPart = Integer.parseInt(versionParts[2]);
        return majorPart == major && minorPart == minor && patchPart >= patch;
    }

    public static boolean isAtLeast(final int major, final int minor) {
        return isAtLeast(major, minor, -1);
    }

    public static int getMajorVersion() {
        return Integer.parseInt(getVersionParts(Bukkit.getVersion())[0]);
    }

    public static int getMinorVersion() {
        return Integer.parseInt(getVersionParts(Bukkit.getVersion())[1]);
    }

    public static int getPatchVersion() {
        return Integer.parseInt(getVersionParts(Bukkit.getVersion())[2]);
    }

    private static String[] getVersionParts(final String input) {
        final Matcher versionMatcher = VERSION_PATTERN.matcher(input);
        final String[] version = new String[]{"-1", "-1", "-1"};
        if (!versionMatcher.find()) {
            return version;
        }
        final String[] versionParts = versionMatcher.group().split("\\.");
        System.arraycopy(versionParts, 0, version, 0, versionParts.length);
        return version;
    }
}
