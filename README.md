# Bungee Whitelist Plugin

This repository contains the code for a Bungee-wide whitelist. This whitelist comes in the form of
a Bungee plugin. This plugin was mainly developed to be used by Fair Square Games' own private
network to restrict access and any functionality in this plugin helps to facilitate this.

## Installation instructions

In order to install this plugin on your own Bungee, you can follow the steps below.

1. Download the latest version from the [releases](https://github.com/fairsquare-games/bungee-whitelist/releases)
section.
2. Stop your Bungee server.
3. Put the downloaded .jar file into the Bungee plugins directory.
4. Start your Bungee server.

**IMPORTANT: In order for this plugin to be effective, all of your servers within your network
must not be accessible from the outside. This plugin only controls access on the Bungee proxy,
not the access of every individual server. A misconfiguration of your Bungee could cause your
servers to be exposed.**