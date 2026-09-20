package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Function;

/** Registers the mod's 1.21.1 custom payloads without changing their wire data. */
public final class NetworkRegister {
    private static final String PROTOCOL_VERSION = "1";

    private NetworkRegister() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        client(registrar, ClientboundMuteExplosionAtPos.class, ClientboundMuteExplosionAtPos::encode, ClientboundMuteExplosionAtPos::decode, ClientboundMuteExplosionAtPos::handle);
        client(registrar, ClientboundHerobrinePortalFx.class, ClientboundHerobrinePortalFx::encode, ClientboundHerobrinePortalFx::decode, ClientboundHerobrinePortalFx::handle);
        client(registrar, ClientboundWoopieSwordWindFx.class, ClientboundWoopieSwordWindFx::encode, ClientboundWoopieSwordWindFx::decode, ClientboundWoopieSwordWindFx::handle);
        client(registrar, ClientboundBlackFireFx.class, ClientboundBlackFireFx::encode, ClientboundBlackFireFx::decode, ClientboundBlackFireFx::handle);
        client(registrar, CPApplyShake.class, (message, buffer) -> message.encode(buffer), CPApplyShake::new, CPApplyShake::handle);
        client(registrar, ClientboundDiamondAttractorFx.class, ClientboundDiamondAttractorFx::encode, ClientboundDiamondAttractorFx::decode, ClientboundDiamondAttractorFx::handle);
        client(registrar, ClientboundHerobrineAssistanceFx.class, ClientboundHerobrineAssistanceFx::encode, ClientboundHerobrineAssistanceFx::decode, ClientboundHerobrineAssistanceFx::handle);
        client(registrar, ClientboundEnderAegisSparkFx.class, ClientboundEnderAegisSparkFx::encode, ClientboundEnderAegisSparkFx::decode, ClientboundEnderAegisSparkFx::handle);
        client(registrar, ClientboundEliteHerobrineFx.class, ClientboundEliteHerobrineFx::encode, ClientboundEliteHerobrineFx::decode, ClientboundEliteHerobrineFx::handle);
        client(registrar, ClientboundBlueDemonEffectFx.class, ClientboundBlueDemonEffectFx::encode, ClientboundBlueDemonEffectFx::decode, ClientboundBlueDemonEffectFx::handle);
        client(registrar, ClientboundTeleportPortalFx.class, ClientboundTeleportPortalFx::encode, ClientboundTeleportPortalFx::decode, ClientboundTeleportPortalFx::handle);
        client(registrar, ClientboundPlayerGroundTransitionPosition.class, ClientboundPlayerGroundTransitionPosition::encode, ClientboundPlayerGroundTransitionPosition::decode, ClientboundPlayerGroundTransitionPosition::handle);
        client(registrar, ClientboundRigAnimation.class, ClientboundRigAnimation::encode, ClientboundRigAnimation::decode, ClientboundRigAnimation::handle);
        client(registrar, ClientboundSpecialAnimation.class, ClientboundSpecialAnimation::encode, ClientboundSpecialAnimation::decode, ClientboundSpecialAnimation::handle);
        client(registrar, ClientboundObsidianArmorAnimation.class, ClientboundObsidianArmorAnimation::encode, ClientboundObsidianArmorAnimation::decode, ClientboundObsidianArmorAnimation::handle);
        client(registrar, ClientboundBetterCombatAnimation.class, ClientboundBetterCombatAnimation::encode, ClientboundBetterCombatAnimation::decode, ClientboundBetterCombatAnimation::handle);
        client(registrar, ClientboundGroundFracture.class, ClientboundGroundFracture::encode, ClientboundGroundFracture::decode, ClientboundGroundFracture::handle);
        client(registrar, ClientboundGroundStuckKnockoutFx.class, ClientboundGroundStuckKnockoutFx::encode, ClientboundGroundStuckKnockoutFx::decode, ClientboundGroundStuckKnockoutFx::handle);

        server(registrar, ServerboundActivateArmor.class, ServerboundActivateArmor::encode, ServerboundActivateArmor::decode, ServerboundActivateArmor::handle);
        server(registrar, ServerboundDestructionEyeAttack.class, ServerboundDestructionEyeAttack::encode, ServerboundDestructionEyeAttack::decode, ServerboundDestructionEyeAttack::handle);
        server(registrar, VanillaAttackKeyMessage.class, VanillaAttackKeyMessage::buffer, VanillaAttackKeyMessage::new, VanillaAttackKeyMessage::handler);
        server(registrar, ThrowingEnderPearlMessage.class, ThrowingEnderPearlMessage::buffer, ThrowingEnderPearlMessage::new, ThrowingEnderPearlMessage::handler);
        server(registrar, SpecialAttackMessage.class, SpecialAttackMessage::buffer, SpecialAttackMessage::new, SpecialAttackMessage::handler);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(Class<?> payloadClass) {
        String path = payloadClass.getSimpleName().replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase(Locale.ROOT);
        return new CustomPacketPayload.Type(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, path));
    }

    private static <T extends AnnoyingVillagersPayload> void client(
            PayloadRegistrar registrar,
            Class<T> payloadClass,
            BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, IPayloadContext> handler
    ) {
        registrar.playToClient(type(payloadClass), codec(encoder, decoder), handler::accept);
    }

    private static <T extends AnnoyingVillagersPayload> void server(
            PayloadRegistrar registrar,
            Class<T> payloadClass,
            BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, IPayloadContext> handler
    ) {
        registrar.playToServer(type(payloadClass), codec(encoder, decoder), handler::accept);
    }

    private static <T extends AnnoyingVillagersPayload> StreamCodec<RegistryFriendlyByteBuf, T> codec(
            BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder
    ) {
        return StreamCodec.of((buffer, message) -> encoder.accept(message, buffer), decoder::apply);
    }
}
