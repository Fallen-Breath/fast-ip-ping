/*
 * This file is part of the Fast IP Ping project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * Fast IP Ping is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fast IP Ping is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Fast IP Ping.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.fallenbreath.fastipping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//#if FORGE
//$$ import net.minecraftforge.fml.common.Mod;
//#elseif NEOFORGE
//$$ import net.neoforged.fml.common.Mod;
//#endif

//#if FORGE_LIKE
//$$ @Mod("fastipping")
//#endif
public class FastIpPingMod
		//#if FABRIC
		implements net.fabricmc.api.ModInitializer
		//#endif
{
	public static final Logger LOGGER = LogManager.getLogger();

	//#if FABRIC
	@Override public void onInitialize()
	//#elseif FORGE_LIKE
	//$$ public FastIpPingMod()
	//#endif
	{
		LOGGER.info("ping & connect fast!");
	}
}
