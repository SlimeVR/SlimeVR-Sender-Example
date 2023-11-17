# SlimeVR Sender Example

Kotlin multiplatform desktop application that shows the basis of sending data to the [SlimeVR Server](https://github.com/SlimeVR/SlimeVR-Server).

## References

- [SlimeVR Server](https://github.com/SlimeVR/SlimeVR-Server)
- [Slimevr-node](https://github.com/SlimeVR/slimevr-node)
- [SlimeVR Wrangler](https://github.com/carl-anders/slimevr-wrangler)
- ["SlimeVR Client Minimal for prototyping"](https://gist.github.com/abb128/ec7ba822114508a92fc0156de655e6c7)
- [MoSlime](https://github.com/moslime/moslime)

## Integrations
- [UDP](https://github.com/SlimeVR/SlimeVR-Server/tree/main/server/core/src/main/java/dev/slimevr/tracking/trackers/udp)
- ~~[SolarXR](https://github.com/SlimeVR/SolarXR-Protocol)~~
- ~~[VMC](https://protocol.vmc.info/english)~~

## [UDP](https://github.com/SlimeVR/SlimeVR-Server/tree/main/server/core/src/main/java/dev/slimevr/tracking/trackers/udp)
- UDP Port: 6969
- Big-endian
- For rotations: X+ right; Y+ forward; Z+ up
- You can discover the server in most cases by sending a UDP broadcast packet (255.255.255.255:6969) and waiting for a response that contains the handshake packet response. SlimeVR responds with "Hey OVR =D 5"

## ~~[SolarXR](https://github.com/SlimeVR/SolarXR-Protocol)~~

A hardware-agnostic serialization protocol for FBT in VR used by SlimeVR.

Not implemented here.

## ~~[VMC](https://protocol.vmc.info/english)~~

SlimeVR supports the basic VMC protocol (bones and trackers).

Not implemented here.

## Build

* `./gradlew run` to run the application
* `./gradlew package` to build a .msi installer into build/compose/binaries

## License clarification
**SlimeVR software** (including server, firmware, drivers, installer, documents, and others - see
licence for each case specifically) **is distributed under a dual MIT/Apache 2.0 License
([LICENSE-MIT] and [LICENSE-APACHE]). The software is the copyright of the SlimeVR
contributors.**

**However, these licenses have some limits, and if you wish to distribute software based
on SlimeVR, you need to be aware of them:**

* When distributing any software that uses or is based on SlimeVR, you have to provide
  to the end-user at least one of the original, unmodified [LICENSE-MIT] or
  [LICENSE-APACHE] files from SlimeVR. It is insufficient to use a generic MIT
  or Apache-2.0 License, **it must be the original license file**.
* This applies even if you distribute software without the source code. In this case,
  one way to provide it to the end-user is to have a menu in your application that lists
  all the open source licenses used, including SlimeVR's.

Please refer to the [LICENSE-MIT] and [LICENSE-APACHE] files if you are at any point
uncertain what the exact requirements are.

## Contributions
Any contributions submitted for inclusion in this repository will be dual-licensed under
either:

- MIT License ([LICENSE-MIT])
- Apache License, Version 2.0 ([LICENSE-APACHE])

Unless you explicitly state otherwise, any contribution intentionally submitted for
inclusion in the work by you, as defined in the Apache-2.0 license, shall be dual
licensed as above, without any additional terms or conditions.

You also certify that the code you have used is compatible with those licenses or is
authored by you. If you're doing so on your work time, you certify that your employer is
okay with this and that you are authorized to provide the above licenses.

[LICENSE-MIT]: LICENSE-MIT
[LICENSE-APACHE]: LICENSE-APACHE