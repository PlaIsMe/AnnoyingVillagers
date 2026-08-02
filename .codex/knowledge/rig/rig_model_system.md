# Rig Model System

## Main rig model

`src/main/java/com/pla/annoyingvillagers/client/model/ModelRig.java` is the humanoid rig model for non-villager rig mobs. It extends `HumanoidModel<T extends Mob>` and defines a segmented biped:
- top-level `head`, `hat`, `body`, `right_arm`, `left_arm`, `right_leg`, `left_leg`
- child segments `right_hand`, `left_hand`, `right_lower_leg`, `left_lower_leg`
- empty tool anchors `right_tool` and `left_tool`

`ModelRig.setupAnim` resets the model root, calls vanilla humanoid setup, then resets body/limb trees so rig keyframes control the final pose. Priority order:
1. death animation from `RigDeathAnimations.DEATH`
2. active one-shot rig animation from `RigClientAnimationState`
3. sneak animation from `RigSneakAnimations.SNEAK`
4. run animation from `AnimationUtil.getRunAnimation`
5. walk animation from `AnimationUtil.getWalkAnimation`
6. idle animation from `AnimationUtil.getIdleAnimation`

After applying animation, `flattenAnimatedRootIntoTopLevelParts` pushes any animated root transform into the humanoid top-level parts, then resets the root. `compensateServerRootMotion` then subtracts the active animation's `RigRootMotion.modelOffset(...)` from the top-level `head`, `body`, arms, and legs so the rendered rig does not apply the same root movement that the server already applied to the entity position. For active attack one-shots, the model restores vanilla `netHeadYaw` and `headPitch` after root compensation so generated attack keyframes cannot pull the head away from the mob's target. `hat.copyFrom(head)` keeps the hat aligned.

## Villager rig model

`src/main/java/com/pla/annoyingvillagers/client/model/ModelRigVillager.java` mirrors the main rig model but uses villager head/body proportions and a nose child on the head. It uses the same one-shot rig animation priority, the same render-side root-motion compensation, and the same speed-sensitive run predicate from `AnimationUtil.shouldUseRunAnimation`.

## Run selection

`AnimationUtil.shouldUseRunAnimation(Mob mob, float limbSwingAmount)` returns true when the mob is sprinting, aggressive, or moving faster than regular walking speed. The speed-sensitive path checks:
- limb swing amount at or above `0.52`
- horizontal delta movement above `max(0.09, movement_speed_attribute * 0.38)`

This allows high-speed AI navigation goals to show run animation even if they do not call `setSprinting(true)` and the mob is not currently aggressive.

## Armor rig

`src/main/java/com/pla/annoyingvillagers/client/model/ModelRigArmor.java` is the armor model used by `RigArmorLayer`. It extends `HumanoidModel<T extends Mob>`, creates inner and outer layer definitions, and manually creates open-arm model parts for arm armor so segmented rig poses fit better.

`ModelRig.copyPropertiesTo` and `ModelRigVillager.copyPropertiesTo` call `ModelRigArmor.copySegmentPoses` when the target armor model is `ModelRigArmor`. This copies:
- `right_hand`
- `left_hand`
- `right_lower_leg`
- `left_lower_leg`

`ModelRigArmor.setVisibleForSlot` controls slot rendering. Chest armor shows upper arms and hand segments, leggings show body/legs/lower legs, and boots skip upper-leg drawing while showing lower-leg segments.

## Client one-shot playback

`RigClientAnimationState` stores active packet-driven rig animations by entity id on the client. `ClientboundRigAnimation` starts a state for a tracked entity with an animation id and duration. The rig models query that state during `setupAnim` and call `RigAnimationResolver` to map the common id to the client-side `AnimationDefinition`.

Rig one-shot packets do not carry position. Server-authoritative root motion moves the real entity on the server through `RigRootMotion` and normal entity tracking syncs that position to clients. The client model only compensates the visual pose by subtracting the sampled model-space root offset.

The current animation holder classes are:
- `RigSwordAnimations` for `SWEEPING_EDGE`, `SWORD_AIRSLASH`, `SWORD_AUTO1`, `SWORD_AUTO2`, `SWORD_AUTO3`, `SWORD_AUTO4`, and `SWORD_DASH`
- `RigDualSwordAnimations` for `DANCING_EDGE`, `SWORD_DUAL_AIRSLASH`, `SWORD_DUAL_AUTO1`, `SWORD_DUAL_AUTO2`, `SWORD_DUAL_AUTO3`, and `SWORD_DUAL_DASH`
- `RigRollAnimation` for `ROLL_FORWARD` and `ROLL_BACKWARD`
- `RigStepAnimations` for `STEP_FORWARD`, `STEP_BACKWARD`, `STEP_LEFT`, and `STEP_RIGHT`

Generated animation holder classes should preserve the user-authored generated style. Do not mechanically split inline `AnimationDefinition.Builder...build()` fields into private creator methods unless the user explicitly asks for that refactor.
