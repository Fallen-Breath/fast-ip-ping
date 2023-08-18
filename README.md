## fast-ip-ping

[![License](https://img.shields.io/github/license/Fallen-Breath/fast-ip-ping.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
![workflow](https://github.com/Fallen-Breath/fast-ip-ping/actions/workflows/gradle.yml/badge.svg)

Yeet the laggy reversed DNS lookup for pure IP server addresses

Extracted from the `yeetServerIpReversedDnsLookup` option in [TweakerMore](https://github.com/Fallen-Breath/tweakermore) Mod

### What & Why & How

For servers whose addresses are represented solely by IP, disable reverse DNS lookups in the InetAddress object

Many non-loopback IPs lack associated domain names, which makes reverse lookups time-consuming

This option sets the domain of those servers directly to their IP, bypassing the reverse DNS check

This results in a 1s ~ 5s reduction in time for servers with pure IP address in the following environments:

- Pinging the server in the server list screen 
- Connecting to the server

### Environment

- Client-side only
- Fabric / Forge mod loader. No extra requirement is needed
