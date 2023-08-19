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

package me.fallenbreath.fastipping.mixins;

import me.fallenbreath.fastipping.impl.InetAddressPatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.InetAddress;
import java.net.UnknownHostException;

// used in mc < 1.17

// architectury-loom does not correctly maintain the name of the anonymous class in mc1.16.5 forge,
// it uses ConnectScreen$1 in development env, but it's ConnectingScreen$1 in compiling/production env.
// we have to manually fix that :(

//#if 11600 <= MC && MC < 11700 && FORGE
//$$ @SuppressWarnings("unresolvable-target")
//#endif
@Mixin(
		targets = {
				//#if 11600 <= MC && MC < 11700 && FORGE
				//$$ "net.minecraft.client.gui.screen.ConnectingScreen$1",
				//#endif
				"net.minecraft.client.gui.screen.ConnectScreen$1"
		}
)
public abstract class ConnectScreenThreadMixin
{
	@Redirect(
			method = "run",
			at = @At(
					value = "INVOKE",
					target = "Ljava/net/InetAddress;getByName(Ljava/lang/String;)Ljava/net/InetAddress;"
			),
			remap = false
	)
	private InetAddress setHostnameToIpAddressToAvoidReversedDnsLookupOnGetHostname_connect(String address) throws UnknownHostException
	{
		// vanilla
		InetAddress inetAddress = InetAddress.getByName(address);

		// patch it
		inetAddress = InetAddressPatcher.patch(address, inetAddress);

		return inetAddress;
	}
}
