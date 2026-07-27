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

package com.pla.annoyingvillagers.accessors;

public interface ModelPartAccess
{
    float getXScale();

    float getYScale();

    float getZScale();

    void setXScale(float x);

    void setYScale(float y);

    void setZScale(float z);

    default void setRenderScale(float x, float y, float z)
    {
        setXScale(x);
        setYScale(y);
        setZScale(z);
    }
}