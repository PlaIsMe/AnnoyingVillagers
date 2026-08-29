# Rig Model System

## Model hierarchy

`ModelRig<T extends Mob>` is the segmented humanoid rig for non-villager rig mobs. `ModelRigVillager` mirrors the same articulated structure with villager proportions and its head/nose differences.

The rig keeps vanilla-compatible top-level humanoid parts and adds child hand, lower-leg, and empty tool-anchor parts. The important articulated chains are:

```text
right_arm → right_hand → right_tool
left_arm  → left_hand  → left_tool
right_leg → right_lower_leg
left_leg  → left_lower_leg
```

These child parts are not just visual helpers: generated common-side pose sampling uses the equivalent hierarchy so combat colliders can follow the authored hand/tool/leg motion on the server without importing client `ModelPart` classes.

## Client pose priority

During `setupAnim`, the model resets the authored rig tree and resolves the final client pose from high-priority one-shot/death playback before normal locomotion. Locomotion falls back through sneak/run/walk/idle according to the project's movement selectors.

The model flattens animated root transform into humanoid top-level parts where required, then applies render-side compensation for server-authoritative generated-pose movement. This prevents the same authored translation from moving both the entity and the rendered model twice.

Attack one-shots may restore vanilla target-facing head yaw/pitch after the authored body pose is applied so combat keyframes do not permanently pull aim away from the mob's target. The hat copies the final head pose.

## One-shot blending

`RigClientAnimationState` stores packet-driven active animation state by entity id. `ClientboundRigAnimation` carries the common animation id and duration, not a second position stream. Normal Minecraft entity tracking handles the real server position.

`RigAnimationSpec.playbackType()` is a render mask. Whole-body playback uses the full captured one-shot pose; hand/upper-body masks can restore locomotion as the base and blend only the intended arm subtree for utility actions.

`RigAnimationResolver` is the client boundary that maps common `RigAnimationId` values to generated/authored `AnimationDefinition` fields. Because the project contains many animation holder classes, maintain the resolver mechanically from the source registry rather than documenting a large holder/moveset list here.

## Generated pose data

`generate_rig_pose_clips.py` converts authored client animation channels into common/server-safe `RigPoseClip` data. The generator should be run against the animation directories being integrated rather than relying on a fixed set of filenames.

Generated clips can sample body/head, arm/hand/tool, and upper/lower-leg channels. They support:
- exact rig-part-following attack colliders
- common-side root/body motion sampling
- attack-start reach estimation
- render-side root-motion compensation using the same authored movement source

The animation itself is the source of truth. Do not create a second hand-maintained table of per-animation root-motion keys or per-move fake collider sweeps.

Generated pose classes belong in the common rig pose package and must not import client-only rendering types.

## Run selection

`AnimationUtil.shouldUseRunAnimation` must use sprinting/current horizontal entity movement for RUN classification. It must not use `Mob#isAggressive()` and must not classify RUN from a stale/damped `limbSwingAmount` threshold.

The damped limb-swing value may still decide whether locomotion is visually active at all, but walk-vs-run classification should reflect current movement. This prevents a completed attack/roll/step from briefly forcing RUN after its server movement stops.

## Armor rig

`ModelRigArmor` is the segmented armor model used by `RigArmorLayer`. The base rig copies child hand/lower-leg poses into the armor rig so armor follows articulation. Slot visibility decides which upper/lower segments are rendered for chest, leg, and boot equipment.


## Timed held-item visibility

`RigItemInHandLayer` and `VillagerRigItemInHandLayer` may suppress a rendered left/right tool according to the active rig spec. `RigClientAnimationState` reads `RigTimedAnimationHook` visibility metadata and the item layer skips rendering that arm while hidden.

This is visual-only. The mob keeps its actual main-hand/off-hand `ItemStack`, so gameplay logic, projectile creation, durability/state data, AI weapon checks, and later hooks still see the real weapon. Tool visibility automatically returns when the one-shot animation ends unless a spec explicitly schedules a show hook earlier.

Keep throw/spawn behavior independent from visibility. A throw animation should normally schedule both a projectile hook and a `hideLeftToolAt(...)` / `hideRightToolAt(...)` hook at the throw timestamp rather than making the projectile helper manipulate rendering state.

## Generated animation source style

Files under `client/animation/rig_animation/**` are generated/user-authored animation holders and can be very large. Preserve their generated inline `public static final AnimationDefinition ... = AnimationDefinition.Builder...build();` style. Do not mechanically split those fields into private creator methods unless explicitly requested.

Project knowledge should describe the registry and playback flow rather than enumerate hundreds of animation constants or holder classes. When integrating new holders, the required path is `AnimationDefinition → RigAnimationId → RigAnimationResolver → RigAnimationSpecs → generated pose refresh when common-side sampling is needed`.
