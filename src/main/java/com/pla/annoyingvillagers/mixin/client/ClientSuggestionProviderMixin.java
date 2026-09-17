package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.util.PersistentPlayerNpcManager;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ClientSuggestionProvider.class)
public abstract class ClientSuggestionProviderMixin {
    @Redirect(
            method = "getOnlinePlayerNames",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
    )
    private boolean av$excludeNpcTabProfileBeforeSuggestionFilters(List<Object> names, Object candidate) {
        // Filter while vanilla builds the list. SmartNpc also filters this method
        // at RETURN and may cancel there, which previously bypassed AV's RETURN
        // injection whenever both mods had synthetic tab profiles.
        if (candidate instanceof String profileName
                && PersistentPlayerNpcManager.isTabProfileName(profileName)) {
            return false;
        }
        return names.add(candidate);
    }
}
