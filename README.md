## fast-ip-ping

[![License](https://img.shields.io/github/license/Fallen-Breath/fast-ip-ping.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![workflow](https://github.com/Fallen-Breath/fast-ip-ping/actions/workflows/gradle.yml/badge.svg)](https://github.com/Fallen-Breath/fast-ip-ping/actions/workflows/gradle.yml)
[![MC Versions](https://cf.way2muchnoise.eu/versions/For%20MC_904356_all.svg)](https://legacy.curseforge.com/minecraft/mc-mods/fast-ip-ping)
[![CurseForge](https://cf.way2muchnoise.eu/full_904356_downloads.svg)](https://legacy.curseforge.com/minecraft/mc-mods/fast-ip-ping)
[![Modrinth](https://img.shields.io/modrinth/dt/9mtu0sUO?label=Modrinth%20Downloads)](https://modrinth.com/mod/fast-ip-ping)

![pinging](pinging.gif)

Yeet the laggy reversed DNS lookup for pure IP server addresses

Extracted from the `yeetServerIpReversedDnsLookup` option in [TweakerMore](https://github.com/Fallen-Breath/tweakermore) Mod

### What & Why & How

For servers whose addresses are represented solely by IP, e.g. `192.168.2.10:25565`, disable reverse DNS lookups in the corresponding `InetAddress` object

Many non-loopback IPs lack associated domain names, which makes reverse lookups time-consuming

```java
// java.net.InetAddress.getHostName(boolean)
String getHostName(boolean check) {
    if (holder().getHostName() == null) {  // It will be null if InetAddress.getByName() received a pure IP 
        holder().hostName = InetAddress.getHostFromNameService(this, check);  // <-- takes forever
    }
    return holder().getHostName();
}
```

This option sets the domain of those servers directly to their IP, bypassing the reverse DNS check

This results in a 1s ~ 5s reduction in time for servers with pure IP address. Affects the following environments:

- Pinging the server in the server list screen 
- Connecting to the server

### Environment

- Client-side only
- Fabric / Forge mod loader. No extra requirement is needed
