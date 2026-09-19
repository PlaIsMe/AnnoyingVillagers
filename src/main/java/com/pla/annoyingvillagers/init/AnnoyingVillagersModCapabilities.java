package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.capabilities.SnakeBladeCapability;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;

public final class AnnoyingVillagersModCapabilities {
    public static final DeferredRegister<AttachmentType<?>> REGISTRY =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AnnoyingVillagers.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SnakeBladeCapability.ISnakeBladeCapability>> SNAKE_BLADE_CAPABILITY =
            REGISTRY.register("snake_blade_cap", () -> AttachmentType
                    .builder(() -> (SnakeBladeCapability.ISnakeBladeCapability) new SnakeBladeCapability.SnakeBladeCapabilityImp())
                    .serialize(new IAttachmentSerializer<CompoundTag, SnakeBladeCapability.ISnakeBladeCapability>() {
                        @Override
                        public SnakeBladeCapability.ISnakeBladeCapability read(
                                IAttachmentHolder holder,
                                CompoundTag tag,
                                HolderLookup.Provider provider
                        ) {
                            SnakeBladeCapability.SnakeBladeCapabilityImp data = new SnakeBladeCapability.SnakeBladeCapabilityImp();
                            data.deserializeNBT(tag);
                            return data;
                        }

                        @Override
                        public CompoundTag write(
                                SnakeBladeCapability.ISnakeBladeCapability attachment,
                                HolderLookup.Provider provider
                        ) {
                            return ((SnakeBladeCapability.SnakeBladeCapabilityImp) attachment).serializeNBT();
                        }
                    })
                    .copyOnDeath()
                    .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> END_FIRE =
            REGISTRY.register("end_fire", () -> AttachmentType.builder(() -> false)
                    .serialize(com.mojang.serialization.Codec.BOOL)
                    .sync(ByteBufCodecs.BOOL)
                    .build());

    private AnnoyingVillagersModCapabilities() {
    }

    @Nullable
    public static <T> T getCapability(@Nullable Entity entity, DeferredHolder<AttachmentType<?>, AttachmentType<T>> attachment) {
        if (entity == null || !entity.isAlive()) {
            return null;
        }
        return entity.getData(attachment);
    }
}
