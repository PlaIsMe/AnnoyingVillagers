/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Upstream: Dragon Mounts: Legacy - Nico Bergemann (BarracudaATA), Kay9, contributors
 * Source: https://github.com/MWall541/Dragon-Mounts-Legacy
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/GPL-3.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;

public class MountCameraManager
{
    private static CameraType previousPerspective = CameraType.FIRST_PERSON;

    public static void onDragonMount()
    {
        previousPerspective = Minecraft.getInstance().options.getCameraType();
        Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
    }

    public static void onDragonDismount()
    {
        Minecraft.getInstance().options.setCameraType(previousPerspective);
    }

    @SuppressWarnings("ConstantConditions") // player should never be null at time of calling
    public static void setMountCameraAngles(Camera camera)
    {
        if (Minecraft.getInstance().player.getVehicle() instanceof HerobrineDragonEntity && !Minecraft.getInstance().options.getCameraType().isFirstPerson())
        {
            camera.move(0, 4, 0);
            camera.move(-camera.getMaxZoom(6), 0, 0); // do distance calcs AFTER our new position is set
        }
    }
}
