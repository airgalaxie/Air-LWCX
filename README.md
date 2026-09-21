# Air-LWCX

An updated version of the LWC block protection plugin for Paper, based on
[pop4959/LWCX](https://github.com/pop4959/LWCX) (licensed under a modified BSD-2-Clause,
Copyright 2011 Tyler Blair).

LWC protects both the blocks themselves and their contents. Originally designed in 2010 for
locking chests (hence the name "Lightweight Chests"), it can be configured to lock any block,
including doors, signs, trapdoors, shelves and supported entities.

### Differences from upstream

Air-LWCX tracks the `paper` branch of pop4959/LWCX and adds support for newer Minecraft
versions, including the Poplar (26.3) block set. Changes are kept local to this repository.

### Contributors

* Hidendra - Original LWC author
* pop4959 - LWCX maintainer
* Me_Goes_Rawr - Previous maintainer
* airgalaxie - Air-LWCX fork
* [Contributors to LWC](https://github.com/Hidendra/LWC/graphs/contributors)
* [Contributors to LWCX](https://github.com/pop4959/LWCX/graphs/contributors)

### Support

For reporting problems with the plugin, please make an issue here on GitHub. Before asking for
support, make sure you are running the latest version of the plugin first. Note: this fork is not
maintained on the upstream LWCX Discord server, so please report issues here on GitHub instead.

### Building

Air-LWCX targets Paper and uses the included Gradle wrapper:

```shell
./gradlew build
```

Generated JAR files are written to `target/`. Dependency, plugin, Java and project versions are
maintained in `gradle/libs.versions.toml`.

### Contributing

Fork the repository and submit a pull request explaining the change. GitHub Actions builds each
pull request with the configured Java toolchain.