package com.pla.annoyingvillagers.client.gui;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.inventory.InventoryViewerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class InventoryViewerScreen extends AbstractContainerScreen<InventoryViewerMenu> {
    private static final ResourceLocation ARMOR_EDITOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID , "textures/gui/armor.png");

    public InventoryViewerScreen(InventoryViewerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = InventoryViewerMenu.IMAGE_WIDTH;
        this.imageHeight = InventoryViewerMenu.IMAGE_HEIGHT;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = this.leftPos;
        int top = this.topPos;

        guiGraphics.blit(ARMOR_EDITOR_TEXTURE, left, top, 0, 0, this.imageWidth, InventoryViewerMenu.TOP_PANEL_HEIGHT);
        guiGraphics.fill(left, top + InventoryViewerMenu.TOP_PANEL_HEIGHT, left + this.imageWidth, top + this.imageHeight, 0xFFC6C6C6);
        guiGraphics.fill(left, top + this.imageHeight - 1, left + this.imageWidth, top + this.imageHeight, 0xFF555555);
        guiGraphics.fill(left, top + InventoryViewerMenu.TOP_PANEL_HEIGHT, left + 1, top + this.imageHeight, 0xFF555555);
        guiGraphics.fill(left + this.imageWidth - 1, top + InventoryViewerMenu.TOP_PANEL_HEIGHT, left + this.imageWidth, top + this.imageHeight, 0xFFFFFFFF);

        drawSlotGrid(guiGraphics, InventoryViewerMenu.CUSTOM_INVENTORY_X, InventoryViewerMenu.CUSTOM_INVENTORY_Y, 9, 3);
        drawSlotGrid(guiGraphics, InventoryViewerMenu.PLAYER_INVENTORY_X, InventoryViewerMenu.PLAYER_INVENTORY_Y, 9, 3);
        drawSlotGrid(guiGraphics, InventoryViewerMenu.PLAYER_INVENTORY_X, InventoryViewerMenu.HOTBAR_Y, 9, 1);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(
                this.font,
                this.title,
                (this.imageWidth - this.font.width(this.title)) / 2,
                6,
                0x404040,
                false
        );
    }

    private void drawSlotGrid(GuiGraphics guiGraphics, int startX, int startY, int columns, int rows) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                drawSlot(guiGraphics, startX + column * 18, startY + row * 18);
            }
        }
    }

    private void drawSlot(GuiGraphics guiGraphics, int slotX, int slotY) {
        int x = this.leftPos + slotX;
        int y = this.topPos + slotY;
        guiGraphics.fill(x - 1, y - 1, x + 17, y + 17, 0xFF555555);
        guiGraphics.fill(x, y, x + 16, y + 16, 0xFF8B8B8B);
        guiGraphics.fill(x + 1, y + 1, x + 15, y + 15, 0xFFC6C6C6);
    }
}
