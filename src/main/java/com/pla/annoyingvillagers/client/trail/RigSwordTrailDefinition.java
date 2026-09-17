package com.pla.annoyingvillagers.client.trail;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.phys.Vec3;

/** Item-skin trail geometry/color. Timing is supplied by the server animation packet. */
public record RigSwordTrailDefinition(
        int red,
        int green,
        int blue,
        Vec3 beginPos,
        Vec3 endPos,
        int lifetimeTicks,
        int interpolations
) {
    private static final int MAX_INTERPOLATIONS = 32;

    public RigSwordTrailDefinition {
        red = clampColor(red);
        green = clampColor(green);
        blue = clampColor(blue);
        if (beginPos == null || endPos == null) throw new IllegalArgumentException("Trail positions cannot be null");
        lifetimeTicks = Math.max(1, lifetimeTicks);
        interpolations = Math.max(1, Math.min(MAX_INTERPOLATIONS, interpolations));
    }

    public static RigSwordTrailDefinition fromItemSkin(JsonElement element) {
        JsonObject root = GsonHelper.convertToJsonObject(element, "item skin");
        if (!root.has("trail") || !root.get("trail").isJsonObject()) return null;
        JsonObject trail = root.getAsJsonObject("trail");
        JsonArray color = requiredArray(trail, "color", 3);
        JsonArray begin = requiredArray(trail, "begin_pos", 3);
        JsonArray end = requiredArray(trail, "end_pos", 3);
        return new RigSwordTrailDefinition(
                color.get(0).getAsInt(),
                color.get(1).getAsInt(),
                color.get(2).getAsInt(),
                vec3(begin, "begin_pos"),
                vec3(end, "end_pos"),
                GsonHelper.getAsInt(trail, "lifetime", 4),
                GsonHelper.getAsInt(trail, "interpolations", 5)
        );
    }

    public static RigSwordTrailDefinition fromSwordTrail(JsonElement element) {
        JsonObject root = GsonHelper.convertToJsonObject(element, "sword trail");
        if (!root.has("trail") || !root.get("trail").isJsonObject()) return null;
        JsonObject trail = root.getAsJsonObject("trail");
        JsonArray color = requiredArray(trail, "color", 3);
        JsonArray begin = requiredArray(trail, "begin_pos", 3);
        JsonArray end = requiredArray(trail, "end_pos", 3);
        return new RigSwordTrailDefinition(
                color.get(0).getAsInt(),
                color.get(1).getAsInt(),
                color.get(2).getAsInt(),
                vec3(begin, "begin_pos"),
                vec3(end, "end_pos"),
                4,
                5
        );
    }

    public RigSwordTrailDefinition withColor(int red, int green, int blue) {
        return new RigSwordTrailDefinition(
                red, green, blue,
                this.beginPos, this.endPos,
                this.lifetimeTicks, this.interpolations
        );
    }

    public float redF() { return this.red / 255.0F; }
    public float greenF() { return this.green / 255.0F; }
    public float blueF() { return this.blue / 255.0F; }
    public boolean isPureBlack() { return this.red == 0 && this.green == 0 && this.blue == 0; }

    private static JsonArray requiredArray(JsonObject object, String name, int expectedSize) {
        JsonArray array = GsonHelper.getAsJsonArray(object, name);
        if (array.size() != expectedSize) throw new JsonParseException(name + " must contain exactly " + expectedSize + " values");
        return array;
    }

    private static Vec3 vec3(JsonArray array, String name) {
        try {
            return new Vec3(array.get(0).getAsDouble(), array.get(1).getAsDouble(), array.get(2).getAsDouble());
        } catch (RuntimeException exception) {
            throw new JsonParseException(name + " must contain numeric values", exception);
        }
    }

    private static int clampColor(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
