# THIRD_PARTY_NOTICES.md
## Third-Party Notices - AnnoyingVillagers

This repository contains a mix of original work and third-party materials.

### Default rule
Files not listed here (and not marked with an SPDX header for a third-party license) are treated as **Original Materials**
and are licensed under **AV-RO-1.0** (see `LICENSE`).

### Rule for third-party materials
If a file or path is listed below, the **upstream license applies** to that file/path (or the described portions).
All required upstream notices must be preserved.

> Tip: Prefer listing **exact file paths**. Use folders only when every file inside shares the same upstream license.

---

## A) GPL / Copyleft Components

### A1) EpicACG - dfdyz - GPL-3.0
- Type: assets adapted from upstream
- License text: `third_party/licenses/GPL-3.0.md`
- Source: https://github.com/dfdyz/epicacg-1.20

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epic_agc

### A2) Explosive Enhancement: Reforged - Xylonity - GPL-3.0
- Type: code/assets adapted (notably particle implementations)
- License text: `third_party/licenses/GPL-3.0.md`
- Source: https://github.com/Xylonity/Explosive-Enhancement-Reforged

**Paths / Files**
- com/pla/annoyingvillagers/client/particle/FireballParticle.java
- assets/annoyingvillagers/textures/particle/fireball1.png
- assets/annoyingvillagers/textures/particle/fireball2.png
- assets/annoyingvillagers/textures/particle/fireball3.png
- assets/annoyingvillagers/textures/particle/fireball4.png
- assets/annoyingvillagers/textures/particle/fireball5.png
- assets/annoyingvillagers/textures/particle/fireball6.png
- assets/annoyingvillagers/textures/particle/fireball7.png
- assets/annoyingvillagers/textures/particle/fireball8.png
- assets/annoyingvillagers/textures/particle/fireball9.png

### A3) Dragon Mounts: Legacy - Nico Bergemann (BarracudaATA), Kay9, contributors - GPL-3.0-or-later
- Type: code/assets adapted
- License text: `third_party/licenses/GPL-3.0.md`
- Source: https://github.com/MWall541/Dragon-Mounts-Legacy

**Paths / Files**
- com/pla/annoyingvillagers/entity/HerobrineDragonEntity.java
- com/pla/annoyingvillagers/accessors/ModelPartAccess.java
- com/pla/annoyingvillagers/client/engine/ModelPartProxy.java
- com/pla/annoyingvillagers/client/engine/MountCameraManager.java
- com/pla/annoyingvillagers/client/animation/DragonAnimator.java
- com/pla/annoyingvillagers/client/engine/MountControlsMessenger.java
- com/pla/annoyingvillagers/client/model/ModelHerobrineDragon.java
- com/pla/annoyingvillagers/client/renderer/HerobrineDragonRenderer.java
- assets/annoyingvillagers/textures/entities/herobrine_dragon/body.png
- assets/annoyingvillagers/textures/entities/herobrine_dragon/glow.png
- assets/annoyingvillagers/textures/entities/herobrine_dragon/dissolve.png

### A4) Epic Fight - Infernal Gainer - reascer - GPL-3.0
- Type: assets adapted from upstream
- License text: `third_party/licenses/GPL-3.0.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-infernal-gainer

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epicfight_infernal_gainer

### A5) EpicFight - Awaken - ShelMarow - GPL-3.0
- Type: assets adapted from upstream
- License text: `third_party/licenses/GPL-3.0.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/epicfight-awaken

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epicfight_awaken

### A6) Epic Fight - Guandao Moveset - namelesslk - GPL-3.0
- Type: code/assets adapted from upstream
- License text: `third_party/licenses/GPL-3.0.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-guandao-moveset

**Paths / Files**
- com/pla/annoyingvillagers/gameasset/AnimsEpicFightGuandao.java
- assets/annoyingvillagers/animmodels/animations/biped/falchion

---

## B) LGPL Components

### B1) Epic Fight - Valour Guard - namelesslk - LGPL-2.1
- Type: assets/data derived (e.g., animations) and/or LGPL-governed parts
- License text: `third_party/licenses/LGPL-2.1.md`
- Source: https://github.com/Cyber2049/valour-guard

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epicfight_valour_guard

### B2) Epic Fight - Impactful - namelesslk - LGPL-2.1
- Type: code adapted
- License text: `third_party/licenses/LGPL-2.1.md`
- Source: https://github.com/Cyber2049/Epic-Fight-Impactful

**Paths / Files**
- com/pla/annoyingvillagers/network/CPApplyShake
- com/pla/annoyingvillagers/client/CameraEngine

---

## C) MIT Components

### C1) Sprite Arrows - iliandev - MIT
- Type: code adapted
- License text: `third_party/licenses/MIT.md`
- Source: https://github.com/justliliandev/arrow-sprites

**Paths / Files**
- com/pla/annoyingvillagers/client/renderer/SpriteArrowRenderer.java
- com/pla/annoyingvillagers/client/renderer/SpriteArrowRenderer.java

### C2) Epic Fight x Iron's Spells: Enhanced Animations - YukamiNeeSan - MIT
- Type: derived assets/data with attribution
- License text: `third_party/licenses/MIT.md`
- Source: https://github.com/domanhthang2110/efiscompat

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epicfight_ironspell

### C3) Epic Fight - Dual GreatSword - reascer - MIT
- Type: code/assets adapted
- License text: `third_party/licenses/MIT.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/epicfight-dual-greatsword

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epicfight_dual_greatsword

### C4) Tactical Imbuements - M3tte - MIT
- Type: code/assets adapted
- License text: `third_party/licenses/MIT.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/tactical-imbuements

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/tactical_imbuements

### C5) Epic Sanji - reascer - MIT
- Type: code/assets adapted
- License text: `third_party/licenses/MIT.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/epic-sanji

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/epicsanji

---

## D) Apache-2.0 Components

### D1) Skyfall: Meteorites - Yoshi01111 - Apache-2.0
- Type: code adapted
- License text: `third_party/licenses/Apache-2.0.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/skyfall-meteorites

**Paths / Files**
- com/pla/annoyingvillagers/entity/DragonMeteoriteEntity.java
- com/pla/annoyingvillagers/client/model/ModelDragonMeteorite.java
- com/pla/annoyingvillagers/client/particle/BigSplashParticle.java
- com/pla/annoyingvillagers/client/renderer/DragonMeteoriteRenderer.java
- com/pla/annoyingvillagers/client/renderer/ObsidianSledgehammerProjectileRenderer.java
- com/pla/annoyingvillagers/entity/ObsidianSledgehammerProjectileEntity.java
- com/pla/annoyingvillagers/client/particle/MeteoriteTrailParticle.java
- assets/annoyingvillagers/textures/particle/meteorite_trail.png
- assets/annoyingvillagers/textures/particle/meteorite_trail.png.mcmeta
- assets/annoyingvillagers/textures/particle/meteorite_trail_1.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_2.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_3.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_4.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_5.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_6.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_7.png
- assets/annoyingvillagers/textures/particle/meteorite_trail_8.png
- assets/annoyingvillagers/textures/particle/bigsplash.png
- assets/annoyingvillagers/textures/particle/bigsplash.png.mcmeta
- assets/annoyingvillagers/textures/particle/big_splash_1.png
- assets/annoyingvillagers/textures/particle/big_splash_2.png
- assets/annoyingvillagers/textures/particle/big_splash_3.png
- assets/annoyingvillagers/textures/particle/big_splash_4.png
- assets/annoyingvillagers/textures/particle/big_splash_5.png
- assets/annoyingvillagers/textures/particle/big_splash_6.png
- assets/annoyingvillagers/textures/particle/big_splash_7.png
- assets/annoyingvillagers/textures/particle/big_splash_8.png
- assets/annoyingvillagers/textures/particle/big_splash_9.png
- assets/annoyingvillagers/textures/particle/big_splash_10.png
- assets/annoyingvillagers/textures/particle/big_splash_11.png

**NOTICE**
- If the upstream project provides an Apache NOTICE file, include it as `NOTICE` in your repo
  and preserve required attributions.

---

## E) AFL (Academic Free License) Components

### E1) Annoying Villagers - Pugilist Steve - AFL-3.0
- Type: assets
- License text: `third_party/licenses/AFL-3.0.md`
- Source: https://space.bilibili.com/1337039598/dynamic

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/pugilist_steve

---

## F) Creative Commons Components

### F1) Quark - Vazkii and contributors - CC BY-NC-SA 3.0
- Type: code adapted (colored enchantment rendering)
- License text: `third_party/licenses/CC-BY-NC-SA-3.0.md`
- Source: https://github.com/VazkiiMods/Quark

**Paths / Files**
- com/pla/annoyingvillagers/mixin/ItemRendererMixin.java
- com/pla/annoyingvillagers/client/renderer/ColoredGlintRenderTypes.java
- com/pla/annoyingvillagers/mixin/RenderBuffersMixin.java

### F2) EpicFight - Battle Arts - Forixaim - CC-BY-SA
- Type: assets adapted from upstream
- License text: `third_party/licenses/CC-BY-SA.md`
- Source: https://github.com/Forixaim/Epic-Fight-Battle-Arts

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/battle_style

### F3) Visitors from Omneria - Forixaim - CC-BY-NC-SA
- Type: assets adapted from upstream
- License text: `third_party/licenses/CC-BY-NC-SA-4.0.md`
- Source: https://github.com/Forixaim/Visitors-from-Omneria

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/omneria

---

## G) Proprietary / All Rights Reserved (Dependency Assets)

### G1) Weapon Of Miracle (dependency mod) - reacer - Proprietary / All Rights Reserved
- Type: cloned/copied assets (animations) from a dependency mod for reuse
- License text: `third_party/licenses/LicenseRef-WOM-Proprietary.md`
- Source: https://www.curseforge.com/minecraft/mc-mods/weapons-of-miracles-epicfight

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/wom_clone

**Notes**
- These files are NOT AnnoyingVillagers Original Materials (AV-RO does not apply).
- Copyright remains with the reacer and contributors.
- Redistribution of these assets may require explicit permission from the reacer.
- Users should obtain Weapon Of Miracle via its official distribution channel and comply with its terms.

### G2) P1nero Epic Bow - P1nero - Proprietary / All Rights Reserved
- Type: cloned/copied animations used for registration/compatibility
- Source: https://github.com/P1neapplell0/Epic-Bow

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/p1nero_bow_clone

**Notes**
- These files are NOT AnnoyingVillagers Original Materials (AV-RO does not apply).
- Copyright remains with P1nero and contributors.
- The cloned animation path is included so AnnoyingVillagers can register compatible animations and allow mobs to play them.
- These animations are only activated when the P1nero Epic Bow mod is installed.
- Redistribution of these assets may require explicit permission from P1nero.
- Users should obtain P1nero Epic Bow via its official distribution channel and comply with its terms.

---

## H) Assets authorized by another author (Owned by the Original Author, you should contact the original author if you want to use it)

### H1) Sculk Steve
- Type: assets authorized by original author
- License text: `third_party/licenses/LicenseRef-Author-Permission.md`
- Source: Authorized by original author; no public source URL recorded.

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/sculk_steve

### H2) Yonchi Chikito
- Type: assets authorized by original author
- License text: `third_party/licenses/LicenseRef-Author-Permission.md`
- Source: Authorized by original author; no public source URL recorded.

**Paths / Files**
- assets/annoyingvillagers/animmodels/animations/biped/yonchi_chikito

---

## I) Credits - Annoying Villagers (textures / models / languages)

This section credits contributors for original work and contributions used in AnnoyingVillagers.
Unless otherwise stated in this document, these credited contributions are part of the project’s distribution and
may be subject to the repository’s licensing policy (see `LICENSE` and this `THIRD_PARTY_NOTICES.md`).

### Contributors
- **MrFudgeMonkeyz** - original animation author
- **MrLech** - model, texture
- **Blue_Hair030** - model, texture
- **Motschen** - model, texture
- **Unitypusheen** - model, texture
- **Hivane** - model, texture
- **Maximino** - model, texture
- **TheArix** - model, texture
- **DanielhackerXD** - Chinese language, texture
- **stelkardmc** - Russian language, texture

### Notes
- This credits list does not override any upstream third-party licenses listed above.
- If any contributor’s work has a separate license or permission terms, list it explicitly in this document (recommended).

---

## J) Compliance Notes (Important)
- Keep upstream license texts in `/third_party/licenses/`.
- Keep upstream copyright and permission notices where required (MIT/Apache/CC).
- GPL/LGPL portions may require additional obligations if you distribute binaries or modified source for those portions.
- AV-RO applies only to Original Materials and cannot restrict rights granted by upstream licenses for third-party portions.
