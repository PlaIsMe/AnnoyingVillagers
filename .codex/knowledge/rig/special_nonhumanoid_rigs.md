# AvWarden, AvGolem, and GolemArms special rigs

This project has a second vanilla `ModelPart` animation/combat path for large non-humanoid rigs. It is deliberately separate from the humanoid `ModelRig` / `RigAnimationId` system.

## Naming and entities

- `AvWarden` is registered as `annoyingvillagers:av_warden`. Do not reintroduce the old Herobrine-Warden class/id or Super-Warden naming in Annoying Villagers code.
- `AvGolem` is the reusable Iron Golem-derived base. `GolemWarriors` is the registered concrete entity `annoyingvillagers:golem_warriors`.
- `GolemArms` is the attached visual/combat entity `annoyingvillagers:golem_arms` used by Destruction Eye. Do not use Golem Heart naming in Annoying Villagers code.
- `DestructionEyeItem` is registered as `annoyingvillagers:destruction_eye`.

Client models are `ModelAvWarden`, `ModelAvGolem`, and `ModelGolemArm`. Their exported `AnimationDefinition` holders live under `client/animation/rig_special_animation/` and are split by role/family to keep the very large generated Java files manageable. `SpecialAnimationResolver` is the authoritative client mapping from common `SpecialAnimationId` to those holders.

These models preserve the original segmented authored joint chains. Never retarget them back to the Steve/humanoid rig.

## Special animation registry

Common/server code uses:

`SpecialAnimationId -> SpecialAnimationSpecs -> SpecialAnimationController -> SpecialColliderSystem`

Client code maps the common id through `SpecialAnimationResolver` to an `AnimationDefinition`. `ClientboundSpecialAnimation` only sends the id and duration. The client must not decide hit results.

`SpecialAnimationId` is network ordinal-facing. Append ids; do not reorder existing ids after release.

## Server pose/collider data

`tools/generate_special_pose_clips.py` parses the exact Blockbench-exported Java model hierarchy and Java `AnimationDefinition` holders, then writes server-safe Java pose data under:

```text
src/main/java/com/pla/annoyingvillagers/rig/pose/generated/special/
```

This mirrors the existing humanoid `rig/pose/generated` workflow but keeps the non-humanoid data in its own `special` subpackage. `SpecialGeneratedPoseRegistry` registers the generated skeletons and clips directly into `SpecialPoseLibrary`; no JSON resources are loaded at runtime. Regenerate this package whenever a combat animation or one of these model bind hierarchies changes.

The special collider system samples eight transforms across each active server tick. This mirrors the eight-segment sampling used by the original Epic Fight/Avalon `MultiOBBCollider` closely enough to prevent fast arm motion from tunneling through targets.

Collider contracts preserved from the reference mods:

- Warden arm: half extents `0.8, 0.8, 0.8`, local center `0, 0.3, 0`, on `arm_down_L/R`.
- Golem arm: half extents `0.5, 0.5, 0.5`, on `arm_1`, `arm_3`, `arm_5`, `arm_7` for the attacking side.
- GolemArms: half extents `0.5, 0.5, 0.5`, on all four joints of the selected visible arm chain. The current three-arm rig has bottom-left `garm_down_1_L..4_L`, top-center `garm_up_1_R..4_R`, and bottom-right `garm_down_1_R..4_R`. There is no `garm_up_*_L` chain anymore.

Do not replace these with one large entity AABB. The segmented authored animation is the hitbox source of truth.

## AvWarden combat

`AvWarden` keeps the vanilla Warden Brain for target acquisition/activity, but removes the FIGHT priority-14 vanilla `SonicBoom` and priority-15 vanilla melee `OneShot`. `tickAvCombat()` ports the Super Warden behavior series using the original target-grid predicates, weights, combo continuation grids, and cooldowns.

Converted visual states include idle, walk, chase, fall, death, sniff, emerge, digging, four basic attack chains, skill 1, skill 2, and sonic boom. Server target presence is mirrored through the synced `DATA_AV_CHASING` flag and exposed by `AvWarden.isAvChasing()`, so the client CHASE/WALK choice does not depend on an unsynced `Mob#getTarget()` value.

Skill 2 and sonic-boom timed hooks call `AvWarden.fireAvSonicBoom(15.0F)`. The `15` from the Avalon reference is damage, not a 15-block range cap. Selection of the standalone sonic-boom series remains gated to 5..30 blocks.

## AvGolem combat and weapons

`AvGolemCombatGoal` ports both the original Super Golem behavior series and the Modular Golem weapon series. It preserves target-grid selection, weights, series cooldowns, combo distances, and the dual-axe skill chase phase.

Weapon style is selected by overridable methods receiving both hand stacks:

```java
canUseSword(mainHand, offHand)
canUseDualSword(mainHand, offHand)
canUseAxe(mainHand, offHand)
canUseDualAxe(mainHand, offHand)
canUseSpear(mainHand, offHand)
```

Defaults:

- single sword: main hand is `SwordItem`, off hand is not `SwordItem`;
- dual sword: both hands are `SwordItem`;
- axe, dual axe, spear: `false` by default so each concrete AV golem can explicitly opt into the desired project-specific items by overriding the methods.

This is intentional. Do not globally map every vanilla/modded AxeItem or TridentItem to the special families unless requested.

Weapon animation families are `SWORD_`, `DUAL_SWORD_`, `AXE_`, `DUAL_AXE_`, and `SPEAR_`. `IDLE_TEST` exists in the imported holder but is not a runtime state.

`ModelAvGolem` renders normal locomotion/death/fall using the active weapon family where matching clips exist. `AvGolemCombatGoal` sets sprinting only while it is actively chasing a target between attacks; the model therefore classifies normal RUN from `isSprinting()` plus current horizontal movement, never from `isAggressive()`. `AvGolemItemInHandLayer` renders held items at animated `Tool_L/R` anchors.

`AvGolemArmorLayer` supports vanilla/modded `ArmorItem` helmet and chest slots using a separate articulated attachment model. Shinguard/custom Modular-Golem slots are not part of this port.

## GolemArms / Destruction Eye

Destruction Eye is a normal item; the giant arm rig is a separate attached `GolemArms` entity because an ordinary item model cannot run the full articulated entity animation/collider pipeline.

While Destruction Eye is held in the player's main hand, server `inventoryTick` maintains one bound `GolemArms` entity. The arms follow the player's body yaw and position and discard themselves after the item is no longer held.

Client locomotion selection:

- stationary: `IDLE`
- stationary + shift: `KNEEL`
- moving + shift: `SNEAK`
- moving: `WALK`
- sprinting + moving: `RUN`

Movement detection uses very small per-tick horizontal displacement/delta thresholds so short navigation steps do not visually slide in the idle pose. `ModelAvGolem` and `ModelAvWarden` combine `limbSwingAmount`, actual `current - previous` horizontal displacement, and delta movement so even tiny path corrections still play WALK/CHASE instead of translating the whole static model.

Attack-key selection is server-authoritative through `ServerboundDestructionEyeAttack`:

- airborne: `AIR_ATK`
- sprinting + moving: `RUN_ATK`
- otherwise cycle `ATK_1 -> ATK_2 -> ATK_3`

The client Minecraft attack mixin suppresses the ordinary player hit while Destruction Eye is in the main hand, sends the request packet, and plays only the local hand swing. `SpecialAnimationController` performs actual arm collision/damage on the server.

`ModelGolemArm` intentionally contains hidden `Root -> Torso -> Chest` driver parts plus 12 visible joints across three arm chains. Do not remove the hidden parents: the imported arm animation is authored relative to them. The top-center arm intentionally keeps the historical `garm_up_*_R` names because the exported animations and server pose generator are name-based.

The attached entity is visually anchored to the owner twice: `GolemArms` mirrors both the owner's tick-history (`xo/yo/zo`) and render-history (`xOld/yOld/zOld`) plus current transform every tick, and `GolemArmsRenderer#getRenderOffset` compensates using the same `xOld -> current` interpolation path as vanilla entity rendering. This avoids the one-entity-network-interpolation delay that otherwise makes the arms trail behind a moving player.

The base arm texture is the vanilla Iron Golem texture. `textures/entities/golem_arms_emissive.png` is the renamed luminous overlay copied from the original asset. `GolemArmsRedGlintLayer` adds the project's red entity enchantment glint using `ColoredGlintRenderTypes.ENTITY_GLINT_RED`. Destruction Eye uses its own provided item texture/model.

### Destruction Eye guard

Holding right click with Destruction Eye in the main hand uses vanilla `UseAnim.BLOCK` / long use duration, so vanilla player movement slowdown applies. `GolemArms` drives a server-side guard animation state machine:

`GUARD_TRANSFORM` (one shot) -> `GUARD` (loop while held) -> `GUARD_FINISH` (one shot after release).

`DestructionEyeGuardEvent` blocks shield-blockable positional damage from every direction; unlike a vanilla shield there is no front-facing dot-product test. Bypass-shield damage and piercing arrows remain unblocked. A successful block calls `CommonUtil.damageBlocked(...)` and damages Destruction Eye by one durability. Successful server-authoritative GolemArms attack hits also damage the bound Destruction Eye by one durability per successfully damaged target.

GolemArms swing audio follows the original Golem Heart intent: the Epic Fight reference uses `WHOOSH_BIG`; Annoying Villagers uses its closest local equivalent `AnnoyingVillagersModSounds.WHOOSH`. For GolemArms the sound is scheduled at each attack-window start so multi-phase arm attacks whoosh when each authored swing begins; guard/non-damaging animations stay silent. Existing AvWarden/AvGolem special-attack WHOOSH timing remains at animation start.

### Collider debug rendering

`RigColliderRenderer` renders both humanoid `RigColliderSystem` boxes and `SpecialColliderSystem` boxes when vanilla hitbox rendering is enabled. That means AvWarden, AvGolem, and GolemArms attack colliders use the same cyan-before-active / red-during-active debug convention. These are the same pose-driven OBBs used by server damage, not decorative client-only boxes.

## Reference timing and damage notes

Avalon animation event frame numbers are authored on a 60 FPS timeline. Convert a reference event/phase frame to a Minecraft server tick with `ceil(frame / 3)`, not with a 20-FPS frame assumption. For example frame 30 is tick 10 / 0.5 seconds and frame 60 is tick 20 / 1.0 second. `SpecialAnimationSpecs.frameToTick()` and `window()` are the shared conversion points.

The `AvalonAttackAnimation` damage multiplier is preserved per attack. The standalone Warden sonic-boom constructor contains both a `0.7` play-speed value and a `0.4` damage multiplier; server melee collision for `WARDEN_SONIC_BOOM` therefore uses `0.4`, while its timed sonic event independently deals 15 sonic-boom damage.

Original `simpleGroundSplit(...)` events are represented by a fracture visual plus explicit server damage equal to 50% of the caster's attack-damage attribute. Super Golem/Modular Golem variants use the reference's entity-type separation behavior; Warden variants use mob-category separation behavior.

Modular Golem's weapon attack phases pass `null` as the Epic Fight collider, meaning the original runtime resolves collision from the held weapon capability. Annoying Villagers has no Epic Fight weapon capability layer, so `Tool_R/Tool_L` use project-local OBB approximations for sword, axe, and spear. These presets are intentionally centralized in `SpecialAnimationSpecs`; tune them there for custom AV weapon geometry rather than changing pose data.
