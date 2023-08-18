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
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.network.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.net.InetAddress;
import java.net.UnknownHostException;

// used in mc < 1.17
@Mixin(MultiplayerServerListPinger.class)
public abstract class MultiplayerServerListPingerMixin
{
	@Unique
	private final ThreadLocal<ServerAddress> currentServerAddress = ThreadLocal.withInitial(() -> null);

	@ModifyVariable(
			method = "add",
			at = @At(
					value = "INVOKE_ASSIGN",
					target = "Lnet/minecraft/network/ServerAddress;parse(Ljava/lang/String;)Lnet/minecraft/network/ServerAddress;",
					shift = At.Shift.AFTER
			)
	)
	private ServerAddress setHostnameToIpAddressToAvoidReversedDnsLookupOnGetHostname_ping(ServerAddress serverAddress)
	{
		this.currentServerAddress.set(serverAddress);
		return serverAddress;
	}

	@ModifyArg(
			method = "add",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/ClientConnection;connect(Ljava/net/InetAddress;IZ)Lnet/minecraft/network/ClientConnection;"
			)
	)
	private InetAddress setHostnameToIpAddressToAvoidReversedDnsLookupOnGetHostname_ping(InetAddress inetAddress) throws UnknownHostException
	{
		return InetAddressPatcher.patch(this.currentServerAddress.get().getAddress(), inetAddress);
	}
}
