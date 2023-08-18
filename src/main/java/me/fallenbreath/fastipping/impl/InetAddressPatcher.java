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

package me.fallenbreath.fastipping.impl;

import com.google.common.net.InetAddresses;
import me.fallenbreath.fastipping.FastIpPingMod;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressPatcher
{
	@SuppressWarnings("UnstableApiUsage")
	public static InetAddress patch(String hostName, InetAddress addr) throws UnknownHostException
	{
		if (InetAddresses.isInetAddress(hostName))
		{
			InetAddress patched = InetAddress.getByAddress(addr.getHostAddress(), addr.getAddress());
			FastIpPingMod.LOGGER.debug("Patching ip-only InetAddresses from {} to {}", addr, patched);
			addr = patched;
		}
		return addr;
	}
}
