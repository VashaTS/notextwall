package net.vsh33.notextwall;

import net.fabricmc.api.ModInitializer;

import net.vsh33.notextwall.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

public class NoTextWall implements ModInitializer {
	public static final String MOD_ID = "notextwall";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("NoTextWall: Hello Fabric world!");
		ModConfig config = ModConfig.load();
		LOGGER.info("NoTextWall: Config loaded");


	}
}