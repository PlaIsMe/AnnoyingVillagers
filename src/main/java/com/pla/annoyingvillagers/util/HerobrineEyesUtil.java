package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class HerobrineEyesUtil {
    private static final Identifier DEFAULT_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/default.png");
    private static final Identifier DEFAULT_UP_1_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/up_1.png");
    private static final Identifier DEFAULT_DOWN_1_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/down_1.png");
    private static final Identifier SQUARE_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/square/default.png");
    private static final Identifier SQUARE_DOWN_1_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/square/down_1.png");
    private static final Identifier SQUARE_UP_1_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/square/up_1.png");
    private static final Identifier SQUARE_UP_2_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/square/up_2.png");
    private static final Identifier VERTICAL_FAR_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/vertical/default_far.png");
    private static final Identifier VERTICAL_CLOSE_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/vertical/default_close.png");

    private static final Map<String, Identifier> eyeTextures = new HashMap<>();

    static {
        eyeTextures.put("Gory_Moon", DEFAULT_TEXTURE);
        eyeTextures.put("Darkosto", Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/vertical/far_down.png"));
        eyeTextures.put("016Nojr", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("BluSunrize", SQUARE_TEXTURE);
        eyeTextures.put("Buuz135", VERTICAL_FAR_TEXTURE);
        eyeTextures.put("Darkere", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("Darkhax", DEFAULT_TEXTURE);
        eyeTextures.put("Drullkus", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("Ellpeck", DEFAULT_DOWN_1_TEXTURE);
        eyeTextures.put("Emberwalker", DEFAULT_TEXTURE);
        eyeTextures.put("Gigabit101", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("Kamefrede", SQUARE_TEXTURE);
        eyeTextures.put("KnightMiner_", DEFAULT_TEXTURE);
        eyeTextures.put("Lat", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("LexManos", DEFAULT_TEXTURE);
        eyeTextures.put("Mrbysco", SQUARE_UP_2_TEXTURE);
        eyeTextures.put("P3pp3rF1y", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("Ray", DEFAULT_TEXTURE);
        eyeTextures.put("Ridanis", SQUARE_DOWN_1_TEXTURE);
        eyeTextures.put("SOTMead", DEFAULT_TEXTURE);
        eyeTextures.put("ShyNieke", SQUARE_DOWN_1_TEXTURE);
        eyeTextures.put("SkySom", DEFAULT_TEXTURE);
        eyeTextures.put("Soaryn", DEFAULT_TEXTURE);
        eyeTextures.put("TamasHenning", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("ValkyrieofNight", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("XCompWiz", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("cpw11", DEFAULT_TEXTURE);
        eyeTextures.put("darkphan", DEFAULT_TEXTURE);
        eyeTextures.put("direwolf20", DEFAULT_TEXTURE);
        eyeTextures.put("dmodoomsirius", SQUARE_UP_2_TEXTURE);
        eyeTextures.put("malte0811", DEFAULT_TEXTURE);
        eyeTextures.put("nekosune", Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/single/default_far_down_1.png"));
        eyeTextures.put("neptunepink", SQUARE_UP_1_TEXTURE);
        eyeTextures.put("vadis365", VERTICAL_CLOSE_TEXTURE);
        eyeTextures.put("wyld", DEFAULT_TEXTURE);
        eyeTextures.put("paulsoaresjr", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("Mhykol", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("Vswe", DEFAULT_TEXTURE);
        eyeTextures.put("TurkeyDev", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("Gen_Deathrow", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("Sevadus", DEFAULT_TEXTURE);
        eyeTextures.put("jeb_", DEFAULT_UP_1_TEXTURE);
        eyeTextures.put("Dinnerbone", DEFAULT_TEXTURE);
        eyeTextures.put("Grumm", SQUARE_UP_2_TEXTURE);
        eyeTextures.put("fry_", DEFAULT_TEXTURE);
    }

    public static Identifier getHerobrineEyesTexture(String name) {
        return eyeTextures.getOrDefault(name, DEFAULT_TEXTURE);
    }
}
