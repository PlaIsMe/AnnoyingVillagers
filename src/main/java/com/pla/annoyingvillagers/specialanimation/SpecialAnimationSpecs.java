package com.pla.annoyingvillagers.specialanimation;

import com.pla.annoyingvillagers.entity.AvWarden;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class SpecialAnimationSpecs {
    private static final Map<SpecialAnimationId, SpecialAnimationSpec> SPECS = new EnumMap<>(SpecialAnimationId.class);
    private static final String[] GOLEM_LEFT = {"arm_1_L", "arm_3_L", "arm_5_L", "arm_7_L"};
    private static final String[] GOLEM_RIGHT = {"arm_1_R", "arm_3_R", "arm_5_R", "arm_7_R"};
    private static final String[] ARMS_UP_CENTER = {"garm_up_1_R", "garm_up_2_R", "garm_up_3_R", "garm_up_4_R"};
    private static final String[] ARMS_DOWN_LEFT = {"garm_down_1_L", "garm_down_2_L", "garm_down_3_L", "garm_down_4_L"};
    private static final String[] ARMS_DOWN_RIGHT = {"garm_down_1_R", "garm_down_2_R", "garm_down_3_R", "garm_down_4_R"};

    static {
        registerWarden();
        registerGolem();
        registerWeaponGolem();
        registerArms();
    }

    private SpecialAnimationSpecs() {
    }

    public static SpecialAnimationSpec get(SpecialAnimationId id) {
        SpecialAnimationSpec spec = SPECS.get(id);
        if (spec == null) throw new IllegalArgumentException("Missing special animation spec for " + id);
        return spec;
    }

    private static void registerWarden() {
        put(SpecialAnimationId.WARDEN_ATK_1_1, 2.5F, 0.8F, windows(window(30, 45, wardenRight())));
        put(SpecialAnimationId.WARDEN_ATK_1_2, 2.65F, 0.8F, windows(window(35, 40, wardenRight(), wardenLeft())), slam(40, 2.5D, 3.0D, false));
        put(SpecialAnimationId.WARDEN_ATK_2_1, 2.65F, 0.8F, windows(window(30, 45, wardenLeft()), window(35, 50, wardenRight())));
        put(SpecialAnimationId.WARDEN_ATK_2_2, 2.5F, 0.8F, windows(window(30, 40, wardenRight()), window(35, 40, wardenLeft())));
        put(SpecialAnimationId.WARDEN_ATK_2_3, 2.65F, 0.8F, windows(window(50, 55, wardenLeft(), wardenRight())), slam(50, 0.0D, 4.0D, false));
        put(SpecialAnimationId.WARDEN_ATK_3_1, 2.35F, 0.8F, windows(window(30, 40, wardenRight())));
        put(SpecialAnimationId.WARDEN_ATK_3_2, 2.35F, 0.8F, windows(window(25, 35, wardenLeft())));
        put(SpecialAnimationId.WARDEN_ATK_3_3, 2.65F, 0.8F, windows(window(43, 46, wardenLeft(), wardenRight())), slam(46, 2.1D, 4.0D, false));
        put(SpecialAnimationId.WARDEN_ATK_4_1, 2.5F, 0.8F, windows(window(35, 45, wardenLeft(), wardenRight())));
        put(SpecialAnimationId.WARDEN_ATK_4_2, 2.5F, 0.8F, windows(window(25, 30, wardenLeft(), wardenRight())), slam(30, 2.5D, 4.0D, false));
        put(SpecialAnimationId.WARDEN_SKILL_1, 2.5F, 0.8F, windows(window(51, 55, wardenLeft(), wardenRight())), slam(55, 1.0D, 5.0D, false));
        put(SpecialAnimationId.WARDEN_SKILL_2, 2.85F, 0.4F, windows(window(65, 70, wardenLeft(), wardenRight())), List.of(SpecialAnimationSpec.TimedHook.at(frameToTick(70), mob -> {
            if (mob instanceof AvWarden warden) warden.fireAvSonicBoom(15.0F);
        })));
        put(SpecialAnimationId.WARDEN_SONIC_BOOM, 2.0F, 0.4F, windows(window(40, 50, wardenLeft(), wardenRight())), List.of(SpecialAnimationSpec.TimedHook.at(frameToTick(45), mob -> {
            if (mob instanceof AvWarden warden) warden.fireAvSonicBoom(15.0F);
        })));
    }

    private static void registerGolem() {
        put(SpecialAnimationId.GOLEM_ATK_1_1, 2.65F, 1.5F, windows(window(37, 44, concat(golemLeft(), golemRight()))));
        put(SpecialAnimationId.GOLEM_ATK_1_2, 2.65F, 2.0F, windows(window(35, 40, concat(golemLeft(), golemRight()))), slam(39, 2.5D, 4.0D));
        put(SpecialAnimationId.GOLEM_ATK_2_1, 2.65F, 1.2F, windows(window(35, 40, golemLeft())));
        put(SpecialAnimationId.GOLEM_ATK_2_2, 2.65F, 1.5F, windows(window(35, 43, golemLeft())));
        put(SpecialAnimationId.GOLEM_ATK_2_3, 2.5F, 1.3F, windows(window(36, 43, golemLeft())), slam(42, 2.5D, 2.0D));
        put(SpecialAnimationId.GOLEM_ATK_3_1, 2.65F, 1.3F, windows(window(36, 43, golemRight())));
        put(SpecialAnimationId.GOLEM_ATK_3_2, 2.65F, 1.4F, windows(window(34, 40, golemLeft())));
        put(SpecialAnimationId.GOLEM_ATK_3_3, 2.85F, 1.8F, windows(window(35, 40, concat(golemLeft(), golemRight()))), slam(39, 3.0D, 5.0D));
        put(SpecialAnimationId.GOLEM_SKILL_1, 2.65F, 2.0F, windows(window(40, 44, concat(golemLeft(), golemRight()))), slam(40, 0.0D, 6.0D));
        put(SpecialAnimationId.GOLEM_SKILL_2, 4.0F, 1.8F, windows(window(45, 55, concat(golemLeft(), golemRight()))), slam(53, 0.5D, 3.0D));
        put(SpecialAnimationId.GOLEM_SKILL_3, 2.5F, 1.8F, windows(window(65, 70, concat(golemLeft(), golemRight()))), slam(70, 2.0D, 3.0D));
    }

    private static void registerWeaponGolem() {
        put(SpecialAnimationId.GOLEM_SWORD_ATK_1_1, 2.5F, 1.2F, windows(window(30, 40, toolSwordR()), window(70, 80, toolSwordR())));
        put(SpecialAnimationId.GOLEM_SWORD_ATK_1_2, 2.0F, 1.2F, windows(window(25, 35, golemLeft()), window(35, 45, toolSwordR())));
        put(SpecialAnimationId.GOLEM_SWORD_SKILL_1, 2.65F, 2.0F, windows(window(35, 40, toolSwordR())), hooks(slam(40, 2.5D, 1.5D), slam(50, 4.0D, 1.5D), slam(60, 5.5D, 1.5D), slam(70, 7.0D, 1.5D)));

        put(SpecialAnimationId.GOLEM_DUAL_SWORD_ATK_1_1, 2.0F, 1.2F, windows(window(30, 40, toolSwordR(), toolSwordL())));
        put(SpecialAnimationId.GOLEM_DUAL_SWORD_ATK_1_2, 1.85F, 1.2F, windows(window(25, 35, toolSwordR(), toolSwordL())));
        put(SpecialAnimationId.GOLEM_DUAL_SWORD_SKILL_1, 2.35F, 1.5F, windows(window(30, 60, toolSwordR(), toolSwordL())));

        put(SpecialAnimationId.GOLEM_AXE_ATK_1_1, 2.0F, 1.2F, windows(window(30, 40, golemLeft())));
        put(SpecialAnimationId.GOLEM_AXE_ATK_1_2, 2.65F, 1.2F, windows(window(25, 35, toolAxeR()), window(55, 65, toolAxeR())));
        put(SpecialAnimationId.GOLEM_AXE_SKILL_1, 2.15F, 2.0F, windows(window(30, 40, toolAxeR())), slam(40, 3.5D, 1.5D));

        put(SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_1, 2.15F, 1.2F, windows(window(25, 45, toolAxeR()), window(35, 50, toolAxeL())));
        put(SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_2, 1.85F, 1.2F, windows(window(20, 40, toolAxeR(), toolAxeL())));
        put(SpecialAnimationId.GOLEM_DUAL_AXE_ATK_1_3, 2.65F, 2.0F, windows(window(40, 45, toolAxeR(), toolAxeL())), slam(45, 1.5D, 3.0D));
        put(SpecialAnimationId.GOLEM_DUAL_AXE_SKILL_1, 3.0F, 2.0F, windows(window(60, 70, toolAxeR(), toolAxeL())), hooks(chaseTarget(35, 60, 0.5D, 2.5D), slam(70, 2.0D, 5.0D)));

        put(SpecialAnimationId.GOLEM_SPEAR_ATK_1_1, 2.0F, 1.2F, windows(window(30, 40, golemLeft()), window(40, 50, toolSpearR())));
        put(SpecialAnimationId.GOLEM_SPEAR_ATK_1_2, 1.85F, 1.2F, windows(window(30, 40, golemLeft()), window(25, 40, toolSpearR())));
        put(SpecialAnimationId.GOLEM_SPEAR_SKILL_1, 2.15F, 2.0F, windows(window(30, 50, toolSpearR())));
    }

    private static void registerArms() {
        put(SpecialAnimationId.ARMS_ATK_1, 2.0F, 1.0F, windows(window(30, 45, armsDownRight()), window(55, 70, armsUpCenter())));
        put(SpecialAnimationId.ARMS_ATK_2, 2.15F, 1.0F, windows(window(30, 45, armsUpCenter()), window(45, 60, armsDownLeft())));
        put(SpecialAnimationId.ARMS_ATK_3, 1.85F, 1.0F, windows(window(30, 45, concat(armsDownRight(), armsDownLeft(), armsUpCenter()))));
        put(SpecialAnimationId.ARMS_RUN_ATK, 2.0F, 1.0F, windows(window(25, 40, concat(armsDownRight(), armsDownLeft()))));
        put(SpecialAnimationId.ARMS_AIR_ATK, 1.65F, 1.0F, windows(window(20, 35, concat(armsDownRight(), armsDownLeft(), armsUpCenter()))));
        put(SpecialAnimationId.ARMS_GUARD_TRANSFORM, 0.5F, 0.0F, windows());
        SPECS.put(SpecialAnimationId.ARMS_GUARD, new SpecialAnimationSpec(SpecialAnimationId.ARMS_GUARD, 72_000, 0.0F, windows(), List.of()));
        put(SpecialAnimationId.ARMS_GUARD_FINISH, 0.5F, 0.0F, windows());
    }

    private static void put(SpecialAnimationId id, float seconds, float damageMultiplier, SpecialAttackWindow[] windows) {
        put(id, seconds, damageMultiplier, windows, List.of());
    }

    private static void put(SpecialAnimationId id, float seconds, float damageMultiplier, SpecialAttackWindow[] windows, List<SpecialAnimationSpec.TimedHook> hooks) {
        SPECS.put(id, new SpecialAnimationSpec(id, Math.max(1, Math.round(seconds * 20.0F)), damageMultiplier, windows, hooks));
    }

    private static SpecialAttackWindow[] windows(SpecialAttackWindow... windows) {
        return windows;
    }

    private static SpecialAttackWindow window(int startFrame, int endFrame, SpecialCollider... colliders) {
        int start = Math.max(0, (int)Math.ceil(startFrame / 3.0D));
        int end = Math.max(start + 1, (int)Math.ceil(endFrame / 3.0D));
        return new SpecialAttackWindow(start, end, colliders);
    }

    private static int frameToTick(int frame) {
        return Math.max(0, (int)Math.ceil(frame / 3.0D));
    }

    @SafeVarargs
    private static List<SpecialAnimationSpec.TimedHook> hooks(List<SpecialAnimationSpec.TimedHook>... groups) {
        return java.util.Arrays.stream(groups).flatMap(List::stream).toList();
    }

    private static List<SpecialAnimationSpec.TimedHook> chaseTarget(int startFrame, int endFrame, double speed, double minDistance) {
        int startTick = frameToTick(startFrame);
        int endTick = Math.max(startTick + 1, frameToTick(endFrame));
        java.util.ArrayList<SpecialAnimationSpec.TimedHook> hooks = new java.util.ArrayList<>();
        for (int tick = startTick; tick <= endTick; tick++) {
            hooks.add(SpecialAnimationSpec.TimedHook.at(tick, mob -> {
                if (mob.getTarget() == null || !mob.getTarget().isAlive()) return;
                Vec3 delta = mob.getTarget().position().subtract(mob.position());
                Vec3 horizontal = new Vec3(delta.x, 0.0D, delta.z);
                if (horizontal.lengthSqr() <= minDistance * minDistance || horizontal.lengthSqr() < 1.0E-6D) return;
                Vec3 movement = horizontal.normalize().scale(speed);
                mob.move(MoverType.SELF, new Vec3(movement.x, 0.0D, movement.z));
                mob.getLookControl().setLookAt(mob.getTarget(), 5.0F, 5.0F);
            }));
        }
        return hooks;
    }

    private static List<SpecialAnimationSpec.TimedHook> slam(int frame, double forwardOffset, double fractureRadius) {
        return slam(frame, forwardOffset, fractureRadius, true);
    }

    private static List<SpecialAnimationSpec.TimedHook> slam(int frame, double forwardOffset, double fractureRadius, boolean separateByEntityType) {
        return List.of(SpecialAnimationSpec.TimedHook.at(frameToTick(frame), mob -> {
            if (!(mob.level() instanceof ServerLevel serverLevel)) return;
            Vec3 look = mob.getLookAngle();
            Vec3 forward = new Vec3(look.x, 0.0D, look.z);
            if (forward.lengthSqr() < 1.0E-6D) forward = Vec3.directionFromRotation(0.0F, mob.getYRot());
            if (forward.lengthSqr() > 1.0E-6D) forward = forward.normalize();
            Vec3 impact = mob.position().add(forward.scale(forwardOffset));
            CommonUtil.spawnGroundSlamFracture(mob, serverLevel, impact, 0.8D, 35, 0.6D, fractureRadius);
            dealGroundSplitDamage(mob, impact, fractureRadius, separateByEntityType);
        }));
    }

    private static void dealGroundSplitDamage(net.minecraft.world.entity.Mob mob, Vec3 center, double radius, boolean separateByEntityType) {
        float damage = (float)mob.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE) * 0.5F;
        if (damage <= 0.0F || radius <= 0.0D) return;
        net.minecraft.world.phys.AABB area = new net.minecraft.world.phys.AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);
        for (LivingEntity target : mob.level().getEntitiesOfClass(LivingEntity.class, area, target -> target.isAlive() && target != mob && target.distanceToSqr(center) <= radius * radius && (separateByEntityType ? target.getType() != mob.getType() : target.getType().getCategory() != mob.getType().getCategory()))) {
            target.invulnerableTime = 0;
            target.hurtOrSimulate(mob.damageSources().mobAttack(mob), damage);
            target.invulnerableTime = 0;
        }
    }

    private static SpecialCollider[] golemLeft() {
        return boxes(GOLEM_LEFT, 0.5D, 0.5D, 0.5D, 0.0D, 0.0D, 0.0D);
    }

    private static SpecialCollider[] golemRight() {
        return boxes(GOLEM_RIGHT, 0.5D, 0.5D, 0.5D, 0.0D, 0.0D, 0.0D);
    }

    private static SpecialCollider wardenLeft() {
        return SpecialCollider.box("arm_down_L", 0.8D, 0.8D, 0.8D, 0.0D, 0.3D, 0.0D);
    }

    private static SpecialCollider wardenRight() {
        return SpecialCollider.box("arm_down_R", 0.8D, 0.8D, 0.8D, 0.0D, 0.3D, 0.0D);
    }

    private static SpecialCollider toolSwordR() {
        return SpecialCollider.box("Tool_R", 0.25D, 0.25D, 1.0D, 0.0D, 0.0D, -0.75D);
    }

    private static SpecialCollider toolSwordL() {
        return SpecialCollider.box("Tool_L", 0.25D, 0.25D, 1.0D, 0.0D, 0.0D, -0.75D);
    }

    private static SpecialCollider toolAxeR() {
        return SpecialCollider.box("Tool_R", 0.55D, 0.55D, 0.9D, 0.0D, 0.0D, -0.55D);
    }

    private static SpecialCollider toolAxeL() {
        return SpecialCollider.box("Tool_L", 0.55D, 0.55D, 0.9D, 0.0D, 0.0D, -0.55D);
    }

    private static SpecialCollider toolSpearR() {
        return SpecialCollider.box("Tool_R", 0.22D, 0.22D, 1.8D, 0.0D, 0.0D, -1.35D);
    }

    private static SpecialCollider[] armsUpCenter() {
        return boxes(ARMS_UP_CENTER, 0.5D, 0.5D, 0.5D, 0.0D, 0.0D, 0.0D);
    }

    private static SpecialCollider[] armsDownLeft() {
        return boxes(ARMS_DOWN_LEFT, 0.5D, 0.5D, 0.5D, 0.0D, 0.0D, 0.0D);
    }

    private static SpecialCollider[] armsDownRight() {
        return boxes(ARMS_DOWN_RIGHT, 0.5D, 0.5D, 0.5D, 0.0D, 0.0D, 0.0D);
    }

    private static SpecialCollider[] boxes(String[] names, double halfX, double halfY, double halfZ, double centerX, double centerY, double centerZ) {
        SpecialCollider[] result = new SpecialCollider[names.length];
        for (int i = 0; i < names.length; i++) result[i] = SpecialCollider.box(names[i], halfX, halfY, halfZ, centerX, centerY, centerZ);
        return result;
    }

    private static SpecialCollider[] concat(SpecialCollider[]... arrays) {
        int size = 0;
        for (SpecialCollider[] array : arrays) size += array.length;
        SpecialCollider[] result = new SpecialCollider[size];
        int index = 0;
        for (SpecialCollider[] array : arrays) {
            System.arraycopy(array, 0, result, index, array.length);
            index += array.length;
        }
        return result;
    }
}
