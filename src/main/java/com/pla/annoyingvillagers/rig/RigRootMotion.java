package com.pla.annoyingvillagers.rig;

import net.minecraft.world.phys.Vec3;

import java.util.EnumMap;
import java.util.Map;

public final class RigRootMotion {
    private static final float TICKS_PER_SECOND = 20.0F;
    private static final double MODEL_UNITS_PER_BLOCK = 16.0D;
    private static final double EPSILON = 1.0E-6D;
    private static final Map<RigAnimationId, Curve> CURVES = new EnumMap<>(RigAnimationId.class);

    static {
        put(RigAnimationId.SWORD_AUTO1, curve(
                key(0.0F, 0.7092F, -0.0878F),
                key(0.05F, 0.5764F, -5.6587F),
                key(0.0833F, 1.0247F, -14.999F),
                key(0.1F, 0.5626F, -17.1988F),
                key(0.1167F, 0.3281F, -19.4887F),
                key(0.15F, -0.0493F, -18.5721F),
                key(0.2F, 0.1197F, -18.2484F),
                key(0.25F, 0.2252F, -17.9179F),
                key(0.3F, 0.2669F, -17.5932F),
                key(0.35F, 0.2628F, -17.4233F),
                key(0.4F, 0.2416F, -17.26F),
                key(0.45F, 0.6025F, -17.6551F),
                key(0.5F, 0.8478F, -18.1379F),
                key(0.55F, 0.9616F, -18.6821F),
                key(0.6F, 0.9345F, -19.2593F)
        ));
        put(RigAnimationId.SWORD_AUTO2, curve(
                key(0.0F, 0.1639F, -0.1687F),
                key(0.05F, 1.4064F, -9.4613F),
                key(0.0833F, 1.975F, -15.4572F),
                key(0.1F, 1.4293F, -18.0793F),
                key(0.1167F, 1.0546F, -20.4996F),
                key(0.15F, 0.1665F, -20.1071F),
                key(0.2F, -0.0147F, -19.7275F),
                key(0.25F, -0.1451F, -19.323F),
                key(0.3F, -0.2208F, -18.8993F),
                key(0.35F, -0.2341F, -18.7368F),
                key(0.4F, -0.2391F, -18.5727F),
                key(0.45F, -0.3977F, -18.4002F),
                key(0.5F, -0.5075F, -18.2629F),
                key(0.55F, -0.576F, -18.1671F),
                key(0.6F, -0.6118F, -18.1178F)
        ));
        put(RigAnimationId.SWORD_AUTO3, curve(
                key(0.0F, 0.1949F, -4.539F),
                key(0.0167F, -0.1688F, -6.9645F),
                key(0.0333F, -0.4935F, -8.9629F),
                key(0.05F, 1.0798F, -10.8624F),
                key(0.1F, 0.8477F, -18.5212F),
                key(0.15F, -0.316F, -23.9079F),
                key(0.2F, -1.5796F, -24.041F),
                key(0.25F, -1.5397F, -23.8107F),
                key(0.3F, -1.5032F, -23.5679F),
                key(0.35F, -1.47F, -23.3124F),
                key(0.4F, -1.4398F, -23.0438F),
                key(0.45F, -1.1749F, -22.7418F),
                key(0.5F, -0.9201F, -22.3799F),
                key(0.55F, -0.6837F, -21.9593F),
                key(0.6F, -0.4741F, -21.4831F)
        ));
        put(RigAnimationId.SWORD_AUTO4, curve(
                key(0.0F, 0.1639F, -0.1687F),
                key(0.05F, 1.4064F, -9.4613F),
                key(0.0833F, 1.975F, -15.4572F),
                key(0.1F, 1.4293F, -18.0793F),
                key(0.1167F, 1.0546F, -20.4996F),
                key(0.15F, 0.1665F, -20.1071F),
                key(0.2F, -0.0147F, -19.7275F),
                key(0.25F, -0.1451F, -19.323F),
                key(0.3F, -0.2208F, -18.8993F),
                key(0.35F, -0.2341F, -18.7368F),
                key(0.4F, -0.2391F, -18.5727F),
                key(0.45F, -0.3977F, -18.4002F),
                key(0.5F, -0.5075F, -18.2629F),
                key(0.55F, -0.576F, -18.1671F),
                key(0.6F, -0.6118F, -18.1178F)
        ));
        put(RigAnimationId.SWEEPING_EDGE, curve(
                key(0.0F, -5.8414F, -1.2477F),
                key(0.05F, -4.8513F, -4.1622F),
                key(0.0833F, -4.0888F, -6.581F),
                key(0.1F, -3.2056F, -8.5635F),
                key(0.15F, -1.0257F, -15.5753F),
                key(0.1833F, -0.3661F, -20.7865F),
                key(0.2F, -0.5706F, -24.1597F),
                key(0.25F, -2.0567F, -33.8108F),
                key(0.2833F, -3.6279F, -39.6025F),
                key(0.3F, -3.9529F, -41.6371F),
                key(0.35F, -4.3854F, -47.791F),
                key(0.3833F, -4.4953F, -53.8209F),
                key(0.4F, -3.9905F, -55.0116F),
                key(0.4167F, -3.6065F, -56.2205F),
                key(0.45F, -3.293F, -58.5405F),
                key(0.5F, -4.0567F, -63.3516F),
                key(0.5167F, -4.3846F, -64.8501F),
                key(0.55F, -4.8896F, -67.7585F),
                key(0.5833F, -5.424F, -70.4824F),
                key(0.6F, -5.481F, -70.3981F),
                key(0.65F, -5.641F, -70.1414F),
                key(0.7F, -5.7847F, -69.8789F),
                key(0.75F, -5.9119F, -69.6115F),
                key(0.8F, -6.0227F, -69.3401F),
                key(0.85F, -6.1169F, -69.0656F),
                key(0.9F, -6.1946F, -68.7888F)
        ));
        put(RigAnimationId.SWORD_AIRSLASH, curve(
                key(0.0F, 1.8381F, -2.0254F),
                key(0.05F, 1.5778F, -2.7935F),
                key(0.1F, 1.5336F, -3.5008F),
                key(0.1167F, 1.566F, -3.7158F),
                key(0.15F, 1.5664F, -3.7676F),
                key(0.2F, 1.8547F, -3.5508F),
                key(0.2167F, 1.3774F, -5.5012F),
                key(0.25F, 1.4092F, -8.7447F),
                key(0.2833F, 0.4328F, -9.6386F),
                key(0.3F, 0.4546F, -9.4912F),
                key(0.35F, 0.5526F, -9.0078F),
                key(0.4F, 0.527F, -8.753F),
                key(0.45F, 0.5134F, -8.4802F),
                key(0.5F, 0.5115F, -8.1897F),
                key(0.55F, 0.8399F, -7.8472F),
                key(0.6F, 1.1575F, -7.4804F),
                key(0.6167F, 1.2611F, -7.3527F)
        ));
        put(RigAnimationId.SWORD_DASH, curve(
                key(0.0F, 1.6467F, -4.4104F),
                key(0.05F, 1.9528F, -7.5387F),
                key(0.1F, 2.2815F, -10.574F),
                key(0.1333F, 2.5024F, -12.5423F),
                key(0.15F, 2.2358F, -13.9301F),
                key(0.2F, 1.238F, -17.7824F),
                key(0.2167F, 0.8669F, -18.9623F),
                key(0.25F, 0.8052F, -21.7723F),
                key(0.3F, 0.4892F, -26.0678F),
                key(0.3333F, -0.0082F, -32.7216F),
                key(0.35F, -0.4542F, -36.0432F),
                key(0.3667F, -0.4049F, -39.4852F),
                key(0.4F, -0.4572F, -39.5269F),
                key(0.45F, -0.4262F, -39.5382F),
                key(0.4667F, -0.3843F, -39.5302F),
                key(0.5F, -0.5267F, -39.3381F),
                key(0.55F, -0.7179F, -39.0329F),
                key(0.6F, -0.8807F, -38.7097F),
                key(0.6167F, -0.9285F, -38.5982F)
        ));
        put(RigAnimationId.SWORD_DUAL_AUTO1, curve(
                key(0.0F, -0.9492F, -3.0339F),
                key(0.0333F, -0.6673F, -5.5154F),
                key(0.05F, 0.8595F, -13.0322F),
                key(0.0667F, 2.5296F, -19.0402F),
                key(0.1F, 1.0829F, -18.3814F),
                key(0.15F, -0.2191F, -17.0F),
                key(0.2F, -0.6192F, -16.5665F),
                key(0.25F, -0.6287F, -16.6601F),
                key(0.3F, -0.6254F, -16.7501F),
                key(0.35F, -0.6425F, -16.6593F),
                key(0.4F, -0.6738F, -16.5687F),
                key(0.45F, 0.1282F, -17.0029F),
                key(0.5F, 0.4568F, -17.6554F),
                key(0.55F, 0.396F, -17.7008F),
                key(0.6F, 0.2197F, -17.8011F),
                key(0.6167F, 0.1317F, -17.8363F)
        ));
        put(RigAnimationId.SWORD_DUAL_AUTO2, curve(
                key(0.0F, -2.8101F, 0.6073F),
                key(0.0333F, -5.9045F, -6.1574F),
                key(0.05F, -5.0593F, -10.7193F),
                key(0.0667F, -3.0805F, -14.2795F),
                key(0.1F, 1.081F, -14.0353F),
                key(0.15F, 0.9751F, -13.8842F),
                key(0.2F, 1.0008F, -13.8765F),
                key(0.25F, 0.9719F, -14.1119F),
                key(0.3F, 1.0208F, -14.3867F),
                key(0.35F, 1.1584F, -14.6806F),
                key(0.4F, 1.3908F, -14.971F),
                key(0.45F, 1.2511F, -14.8252F),
                key(0.5F, 1.1178F, -14.6424F),
                key(0.55F, 0.9951F, -14.4226F),
                key(0.6F, 0.887F, -14.1665F),
                key(0.6167F, 0.8548F, -14.0731F)
        ));
        put(RigAnimationId.SWORD_DUAL_AUTO3, curve(
                key(0.0F, 0.3228F, -3.8443F),
                key(0.05F, 0.6555F, -6.6484F),
                key(0.1F, -0.404F, -9.9908F),
                key(0.15F, -1.8705F, -15.1579F),
                key(0.2F, -1.937F, -20.704F),
                key(0.25F, -0.7153F, -29.8447F),
                key(0.3F, -0.0313F, -39.6466F),
                key(0.35F, 0.678F, -38.0442F),
                key(0.4F, 0.6845F, -37.8812F),
                key(0.45F, 0.6936F, -37.7026F),
                key(0.5F, 0.7031F, -37.5074F),
                key(0.55F, 0.5972F, -37.313F),
                key(0.6F, 0.4853F, -37.12F),
                key(0.65F, 0.3676F, -36.9284F)
        ));
        put(RigAnimationId.DANCING_EDGE, curve(
                key(0.0F, -0.3503F, -2.2738F),
                key(0.05F, -1.0124F, -4.461F),
                key(0.0833F, -1.3703F, -5.633F),
                key(0.1F, -1.231F, -6.8855F),
                key(0.15F, -0.1545F, -10.6053F),
                key(0.1833F, 1.0908F, -12.8321F),
                key(0.2F, 1.2568F, -15.981F),
                key(0.2333F, 0.8453F, -21.8958F),
                key(0.25F, 0.216F, -24.9537F),
                key(0.2667F, -0.6091F, -28.2446F),
                key(0.3F, -0.7556F, -31.3227F),
                key(0.3333F, -1.2213F, -34.4084F),
                key(0.35F, -0.8518F, -34.7742F),
                key(0.4F, 0.0665F, -35.3346F),
                key(0.45F, -0.2813F, -34.4887F),
                key(0.4667F, -0.8227F, -34.2929F),
                key(0.5F, -0.8774F, -35.5898F),
                key(0.55F, -0.5835F, -37.5921F),
                key(0.6F, 0.3078F, -50.2809F),
                key(0.6167F, 1.5793F, -54.2165F),
                key(0.65F, 2.2068F, -55.0609F),
                key(0.6833F, 2.1634F, -55.8914F),
                key(0.7F, 2.1598F, -58.0733F),
                key(0.7333F, 2.753F, -62.1699F),
                key(0.75F, 2.0171F, -64.1578F),
                key(0.7667F, 1.4546F, -66.3041F),
                key(0.8F, 1.0119F, -70.8196F),
                key(0.85F, 0.3663F, -71.8327F),
                key(0.8667F, 0.1351F, -72.283F),
                key(0.9F, 0.1933F, -73.2972F),
                key(0.95F, 0.2349F, -74.822F),
                key(1.0F, 0.2222F, -76.3575F)
        ));
        put(RigAnimationId.SWORD_DUAL_AIRSLASH, curve(
                key(0.0F, 0.9716F, -2.9972F),
                key(0.05F, -1.1646F, -2.8538F),
                key(0.1F, -2.73F, -1.9089F),
                key(0.15F, -3.4676F, -3.6474F),
                key(0.2F, 0.7292F, -6.5482F),
                key(0.25F, 2.2018F, -11.6199F),
                key(0.3F, 2.1467F, -11.365F),
                key(0.35F, 1.9581F, -11.0682F),
                key(0.4F, 1.6462F, -10.7573F),
                key(0.45F, 1.2265F, -10.4594F),
                key(0.5F, 0.7194F, -10.1998F),
                key(0.55F, 0.6253F, -10.4982F),
                key(0.6F, 0.2952F, -10.7381F)
        ));
        put(RigAnimationId.SWORD_DUAL_DASH, curve(
                key(0.0F, 0.0428F, -0.4051F),
                key(0.05F, 0.0488F, 2.3969F),
                key(0.0667F, 0.049F, 3.3186F),
                key(0.1F, 0.1744F, -14.9537F),
                key(0.15F, -0.2745F, -40.7951F),
                key(0.1667F, -0.3219F, -45.4979F),
                key(0.1833F, -0.339F, -50.1128F),
                key(0.2F, -0.353F, -55.0511F),
                key(0.2333F, -0.3416F, -64.8655F),
                key(0.25F, -0.3434F, -65.1435F),
                key(0.3F, -0.3481F, -65.9635F),
                key(0.35F, -0.3519F, -66.7657F),
                key(0.4F, -0.3547F, -67.551F),
                key(0.45F, -0.3542F, -68.2362F),
                key(0.5F, -0.3538F, -68.9203F),
                key(0.55F, -0.3534F, -69.6034F),
                key(0.6F, -0.353F, -70.2855F),
                key(0.65F, -0.791F, -69.0712F),
                key(0.7F, -0.8891F, -67.706F),
                key(0.75F, -0.6367F, -66.3008F),
                key(0.7667F, -0.4767F, -65.8421F)
        ));
        put(RigAnimationId.ROLL_BACKWARD, curve(
                key(0.0F, -2.2529F, 0.6672F),
                key(0.05F, -5.2683F, 13.2351F),
                key(0.1F, -2.088F, 24.9883F),
                key(0.1167F, 0.0771F, 27.7948F),
                key(0.15F, -0.332F, 36.5217F),
                key(0.1833F, 1.0095F, 42.3187F),
                key(0.2F, 1.1557F, 44.6394F),
                key(0.2333F, 1.8146F, 49.039F),
                key(0.25F, 0.6992F, 50.4672F),
                key(0.3F, -0.357F, 55.3525F),
                key(0.3167F, -0.8108F, 54.775F),
                key(0.35F, 1.0804F, 56.4883F),
                key(0.4F, -1.7488F, 64.3515F),
                key(0.45F, -2.5751F, 67.9027F),
                key(0.5F, -3.7175F, 71.7904F)
        ));
        put(RigAnimationId.ROLL_FORWARD, curve(
                key(0.0F, -0.1841F, -7.7457F),
                key(0.05F, 0.4001F, -19.805F),
                key(0.1F, 1.0166F, -29.8274F),
                key(0.15F, 0.3555F, -41.0873F),
                key(0.2F, -0.595F, -51.9149F),
                key(0.25F, -1.9102F, -56.9768F),
                key(0.3F, -3.3004F, -64.7367F),
                key(0.35F, -3.7332F, -67.4472F),
                key(0.4F, -2.6092F, -79.794F),
                key(0.45F, -2.3995F, -81.6639F),
                key(0.5F, -2.0834F, -83.5047F)
        ));
        put(RigAnimationId.STEP_BACKWARD, curve(
                key(0.0F, 0.9886F, -1.1226F),
                key(0.05F, 0.2854F, 11.1625F),
                key(0.1F, 0.0125F, 23.3671F),
                key(0.15F, 0.3368F, 32.2299F),
                key(0.2F, 0.1345F, 40.9098F),
                key(0.25F, -0.1612F, 47.013F),
                key(0.3F, -1.3758F, 53.2878F),
                key(0.35F, -1.7802F, 56.5544F),
                key(0.4F, -2.1071F, 59.8554F)
        ));
        put(RigAnimationId.STEP_FORWARD, curve(
                key(0.0F, -0.3232F, -6.5036F),
                key(0.05F, -0.5222F, -16.466F),
                key(0.1F, -0.567F, -26.4475F),
                key(0.15F, -1.0479F, -39.7738F),
                key(0.2F, -0.8913F, -53.3132F),
                key(0.25F, 0.4419F, -57.8887F),
                key(0.3F, 0.8944F, -62.2655F),
                key(0.35F, 1.079F, -65.1875F),
                key(0.4F, 1.1314F, -68.1424F)
        ));
        put(RigAnimationId.STEP_LEFT, curve(
                key(0.0F, 3.7348F, -2.1329F),
                key(0.05F, 13.1651F, -2.3801F),
                key(0.1F, 29.742F, -2.3698F),
                key(0.15F, 39.3587F, -3.1121F),
                key(0.2F, 48.7185F, -3.6955F),
                key(0.25F, 52.8286F, -4.6257F),
                key(0.3F, 56.6036F, -4.3502F),
                key(0.35F, 59.2936F, -4.5637F),
                key(0.4F, 61.792F, -4.5587F)
        ));
        put(RigAnimationId.STEP_RIGHT, curve(
                key(0.0F, -2.1508F, -4.0821F),
                key(0.05F, -7.3674F, -3.8811F),
                key(0.1F, -23.1836F, -2.0215F),
                key(0.15F, -35.1165F, -2.6991F),
                key(0.2F, -45.2465F, -2.6225F),
                key(0.25F, -49.8145F, -2.6104F),
                key(0.3F, -54.5404F, -2.5094F),
                key(0.35F, -57.4087F, -2.2149F),
                key(0.4F, -60.3157F, -1.9164F)
        ));
        put(RigAnimationId.JUMP, curve(
                key(0.0F, 0.0F, -2.4407F),
                key(0.05F, -0.1352F, -3.1673F),
                key(0.0667F, -0.1894F, -3.4003F),
                key(0.1F, -0.1059F, -3.8234F),
                key(0.1333F, 0.0F, -4.2206F),
                key(0.15F, 0.0F, -4.1886F),
                key(0.2F, 0.0F, -4.0923F),
                key(0.25F, 0.0F, -3.9951F),
                key(0.3F, 0.0F, -3.897F),
                key(0.3333F, 0.0F, -3.8311F),
                key(0.35F, 0.0F, -3.7572F),
                key(0.4F, 0.0F, -3.5259F),
                key(0.45F, 0.0F, -3.478F),
                key(0.5F, 0.0F, -3.4298F)
        ));
    }

    private RigRootMotion() {
    }

    public static boolean has(RigAnimationId animationId) {
        return CURVES.containsKey(animationId);
    }

    public static Vec3 modelOffset(RigAnimationId animationId, float elapsedTicks) {
        Curve curve = CURVES.get(animationId);
        return curve == null ? Vec3.ZERO : curve.modelOffset(elapsedTicks);
    }

    public static Vec3 worldDelta(RigAnimationId animationId, float previousElapsedTicks, float elapsedTicks, Vec3 forward) {
        Vec3 previous = modelOffset(animationId, previousElapsedTicks);
        Vec3 current = modelOffset(animationId, elapsedTicks);
        double sideBlocks = (previous.x - current.x) / MODEL_UNITS_PER_BLOCK;
        double forwardBlocks = (previous.z - current.z) / MODEL_UNITS_PER_BLOCK;
        if (Math.abs(sideBlocks) < EPSILON && Math.abs(forwardBlocks) < EPSILON) {
            return Vec3.ZERO;
        }

        Vec3 horizontalForward = horizontalForward(forward);
        Vec3 right = rightOf(horizontalForward);
        return right.scale(sideBlocks).add(horizontalForward.scale(forwardBlocks));
    }

    public static double maxHorizontalDistanceBlocks(RigAnimationId animationId) {
        Curve curve = CURVES.get(animationId);
        return curve == null ? 0.0D : curve.maxHorizontalDistanceBlocks();
    }

    private static Vec3 horizontalForward(Vec3 forward) {
        Vec3 horizontal = new Vec3(forward.x, 0.0D, forward.z);
        if (horizontal.lengthSqr() < EPSILON) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }

        return horizontal.normalize();
    }

    private static Vec3 rightOf(Vec3 forward) {
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
        if (right.lengthSqr() < EPSILON) {
            return new Vec3(1.0D, 0.0D, 0.0D);
        }

        return right.normalize();
    }

    private static void put(RigAnimationId animationId, Curve curve) {
        CURVES.put(animationId, curve);
    }

    private static Curve curve(Keyframe... keyframes) {
        return new Curve(keyframes);
    }

    private static Keyframe key(float timeSeconds, float xModelUnits, float zModelUnits) {
        return new Keyframe(timeSeconds, xModelUnits, zModelUnits);
    }

    private record Keyframe(float timeSeconds, float xModelUnits, float zModelUnits) {
    }

    private static final class Curve {
        private final Keyframe[] keyframes;
        private final float baseX;
        private final float baseZ;
        private final double maxHorizontalDistanceBlocks;

        private Curve(Keyframe[] keyframes) {
            if (keyframes.length == 0) {
                throw new IllegalArgumentException("root motion curve requires at least one keyframe");
            }

            this.keyframes = keyframes.clone();
            this.baseX = this.keyframes[0].xModelUnits();
            this.baseZ = this.keyframes[0].zModelUnits();

            double maxDistanceModelUnits = 0.0D;
            float previousTime = this.keyframes[0].timeSeconds();
            for (Keyframe keyframe : this.keyframes) {
                if (keyframe.timeSeconds() < previousTime) {
                    throw new IllegalArgumentException("root motion keyframes must be sorted by time");
                }

                previousTime = keyframe.timeSeconds();
                double dx = keyframe.xModelUnits() - this.baseX;
                double dz = keyframe.zModelUnits() - this.baseZ;
                maxDistanceModelUnits = Math.max(maxDistanceModelUnits, Math.sqrt(dx * dx + dz * dz));
            }

            this.maxHorizontalDistanceBlocks = maxDistanceModelUnits / MODEL_UNITS_PER_BLOCK;
        }

        private Vec3 modelOffset(float elapsedTicks) {
            Keyframe raw = sample(elapsedTicks / TICKS_PER_SECOND);
            return new Vec3(raw.xModelUnits() - this.baseX, 0.0D, raw.zModelUnits() - this.baseZ);
        }

        private double maxHorizontalDistanceBlocks() {
            return this.maxHorizontalDistanceBlocks;
        }

        private Keyframe sample(float timeSeconds) {
            if (timeSeconds <= this.keyframes[0].timeSeconds()) {
                return this.keyframes[0];
            }

            Keyframe last = this.keyframes[this.keyframes.length - 1];
            if (timeSeconds >= last.timeSeconds()) {
                return last;
            }

            for (int i = 1; i < this.keyframes.length; i++) {
                Keyframe next = this.keyframes[i];
                if (timeSeconds <= next.timeSeconds()) {
                    Keyframe previous = this.keyframes[i - 1];
                    float span = next.timeSeconds() - previous.timeSeconds();
                    float alpha = span <= 0.0F ? 1.0F : (timeSeconds - previous.timeSeconds()) / span;
                    float x = previous.xModelUnits() + (next.xModelUnits() - previous.xModelUnits()) * alpha;
                    float z = previous.zModelUnits() + (next.zModelUnits() - previous.zModelUnits()) * alpha;
                    return new Keyframe(timeSeconds, x, z);
                }
            }

            return last;
        }
    }
}
