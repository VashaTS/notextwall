package net.vsh33.notextwall.mixin;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import net.vsh33.notextwall.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
@Environment(EnvType.CLIENT)
public class ExampleMixin {

	private static final ModConfig CONFIG = ModConfig.load();

	//for single player
	@Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
	private void onInjectChatMessage(ChatMessageS2CPacket packet, CallbackInfo ci) {
//		debugMessage("onChatMessage invoked");
		String messageContent = packet.body().content();

		if (messageContent != null && messageContent.length() > CONFIG.getMaxMessageLength()) {
			ci.cancel(); // Cancel the message if it's too long
			debugMessage("Long message canceled");
		}
	}
	//for server
	@Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
	private void onInjectGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
		String message = packet.content().getString();

		// Cancel the chat message if it exceeds 100 characters
		if (message.length() > CONFIG.getMaxMessageLength()) {
			ci.cancel();
			debugMessage("Long message canceled");
		}
	}
	// Utility method to display a debug message only to the player
	private void debugMessage(String message) {
		// Check if the MinecraftClient instance and the player are available
		if (MinecraftClient.getInstance().player != null) {
			MinecraftClient.getInstance().player.sendMessage(Text.literal("[noTextWall] " + message), false);
		}
	}
}