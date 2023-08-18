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

import net.minecraft.client.network.AddressResolver;
import me.fallenbreath.fastipping.impl.InetAddressPatcher;
import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.net.InetAddress;
import java.net.UnknownHostException;

//#if FORGE
//$$ import net.minecraft.client.network.Address;
//$$ import org.spongepowered.asm.mixin.Overwrite;
//$$ import java.net.InetSocketAddress;
//$$ import java.util.Optional;
//#endif

// used in mc >= 1.17

//#if FABRIC
@Mixin(AddressResolver.class)
public interface AddressResolverMixin
{
	@ModifyVariable(
			method = "method_36903",
			at = @At(
					value = "INVOKE_ASSIGN",
					target = "Ljava/net/InetAddress;getByName(Ljava/lang/String;)Ljava/net/InetAddress;",
					shift = At.Shift.AFTER
			),
			remap = false
	)
	private static InetAddress setHostnameToIpAddressToAvoidReversedDnsLookupOnGetHostname(InetAddress inetAddress, ServerAddress address) throws UnknownHostException
	{
		return InetAddressPatcher.patch(address.getAddress(), inetAddress);
	}
}
//#endif

//#if FORGE
//$$ @Mixin(value = AddressResolver.class, priority = 200)
//$$ public interface AddressResolverMixin
//$$ {
//$$ 	/**
//$$ 	 * @author Fallen_Breath
//$$ 	 * @reason Forge does not support interface mixin. Here's a vanilla copy one, with manual "@ModifyVariable"
//$$ 	 */
//$$ 	@Overwrite
//$$	@SuppressWarnings("target")
//$$ 	static Optional<Address> method_36903(ServerAddress address)
//$$ 	{
//$$ 		try
//$$ 		{
//$$ 			InetAddress inetAddress = InetAddress.getByName(address.getAddress());
//$$
//$$ 			// @ModifyVariable
//$$ 			inetAddress = InetAddressPatcher.patch(address.getAddress(), inetAddress);
//$$
//$$ 			return Optional.of(Address.create(new InetSocketAddress(inetAddress, address.getPort())));
//$$ 		}
//$$ 		catch (UnknownHostException var2)
//$$ 		{
//$$ 			AddressResolver.LOGGER.debug("Couldn't resolve server {} address", address.getAddress(), var2);
//$$ 			return Optional.empty();
//$$ 		}
//$$ 	}
//$$ }
//#endif
