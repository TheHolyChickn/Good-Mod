<h1><font color=#00FFFF size=30>good mod</font></h1>

a good mod

Contributors:
- [TheHolyChickn (main developer)](https://github.com/TheHolyChickn/)
- [odtheking (helped with random stuff)](https://github.com/odtheking)
- [AzuredBlue (told me to google something)](https://github.com/AzuredBlue)
- [this tutorial and template odtheking told me to ~~steal~~ look at](https://moddev.nea.moe/)
- kikias22 (artist)

<h2><font color=#00FF99>Features</font></h2>
good mod offers two features I've wanted for a long time, but are not present in <a href="https://github.com/Skytils/SkytilsMod/tree/dev">Skytils</a> or <a href="https://skyblockextras.com/">SBE</a>.

1. **Dungeon Drops Tracker**<p>
<a href="https://skyblockextras.com/">SBE</a> actually has this option, but it's extremely outdated and randomly resets itself sometimes. This solution has both a GUI and a chat command element. </p>

2. **Hoppity Reminder**<p>
I have lost way too many eggs due to being in a dungeon run and just forgetting about Hoppity. This solution creates an impossible to miss Hoppity alert, and also has the option to automatically send <tt>!dt Hoppity</tt> in chat.

<h2><font color=#00FF99>Installation</font></h2>

good mod is a [Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) mod, so if you don't already have Forge, [download the latest 1.8.9 release](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) here, and follow the installer's instructions. If you've never downloaded forge before, notice that the download button redirects you to an ad, and you have to wait 5 seconds (see top right corner) before you can proceed to download. **ONLY** click the button in the top right corner, or you could get a virus. After you install forge, find the <tt>1.8.9-forge1.8.9-11.15.1.2318-1.8.9</tt> version in your Minecraft launcher (you may have to go over to Installations and create a new one), and run it. After it loads, close your game.
<h3>Normal Install</h3>

Download the <b><font color=#FF0000>.jar</font></b> file in the [latest release](https://google.com). Locate your <tt>.minecraft</tt> folder. On windows, you can find this by opening File Explorer, and searching for <tt>%appdata%</tt>. On Linux, your <tt>.minecraft</tt> is probably located at <tt>~/.minecraft</tt>. On MacOS, I have no idea. Once inside your <tt>.minecraft</tt> folder, find and open your <tt>mods</tt> folder. Now, open another File Explorer window and navigate to your <tt>Downloads</tt> folder. Locate the **good mod .jar file**, and drag it into your <tt>mods</tt> folder.</p>

Alternatively if you're a fan of using the terminal, just copy one of these commands for your respective system:

Windows Powershell:
```shell
mv .\Downloads\goodmod-*.jar .\AppData\Roaming\.minecraft\mods
```
Linux:
```bash
mv Downloads/goodmod-*.jar .minecraft/mods
```

<h3>Compile from Source</h3>
To avoid scaring people by talking about the terminal, I have moved the guide on compiling from source to the bottom of this page.

<h2><font color=#00FF99>Usage</font></h2>

<p>
good mod is a mod for Hypixel SkyBlock, which provides two features that I was tired of waiting for <a href="https://github.com/Skytils/SkytilsMod/tree/dev">Skytils</a> or <a href="https://skyblockextras.com/">SBE</a> to have, so I made them myself. The configuration menu is accessible by running <code>/nicepb</code>. Incase of commands conflicting with other mods, any good mod command with default <code>{command_name}</code> can also be run via the alias <code>/goodmod:{command_name}</code>. For example, if another mod has <code>/nicepb</code> already set, you can open the configuration menu with <code>/goodmod:nicepb</code>.
</p>
<p>
Every command is configurable. This means if you don't like the command for the config menu being <code>/nicepb</code>, you can change it directly in the config menu. You cannot change the alias <code>/goodmod:{command_name}</code>, you can only change the command name itself. If at any time you forget the command names you've set, run <code>/goodmod:commands</code> to return a list of commands you can call. You can also manually edit the commands in the configuration file.
</p>

<h2>Compiling from Source</h2>
<p>If you want to modify the source code of the mod, or you just want to be edgy and cool, you will need to compile the mod directly from the source code.</p>

<p><b><font color=#008FFF>Windows:</font></b></p>

The first step is to make sure we have the correct dependencies. You will need [Git](https://git-scm.com/), Java JDK for versions [8](https://adoptium.net/temurin/releases/?version=8) and [17](https://adoptium.net/temurin/releases/?version=17), and [gradle](https://gradle.org/releases/?_gl=1*5x1gva*_gcl_au*MjEzMjE0Nzg3OC4xNzIzNzMwNzk1*_ga*MjY4NDUyNjEzLjE3MjM3MzA3OTU.*_ga_7W7NC6YNPT*MTcyMzczMDc5NS4xLjEuMTcyMzczMDg2Ny41OC4wLjA.). For Git, you can open Powershell and run
```
winget install --id Git.Git -e --source winget
```

<p><b><font color=#E0A900>Linux:</font></b></p>

You will still need [Git](https://git-scm.com/), Java JDK for versions [8](https://adoptium.net/temurin/releases/?version=8) and [17](https://adoptium.net/temurin/releases/?version=17), and [gradle](https://gradle.org/releases/?_gl=1*5x1gva*_gcl_au*MjEzMjE0Nzg3OC4xNzIzNzMwNzk1*_ga*MjY4NDUyNjEzLjE3MjM3MzA3OTU.*_ga_7W7NC6YNPT*MTcyMzczMDc5NS4xLjEuMTcyMzczMDg2Ny41OC4wLjA.). However, installing them is much easier.

Debian/Ubuntu
```bash
sudo apt update
sudo apt install git openjdk-8-jdk openjdk-17-jdk gradle
```
Arch
```bash
sudo pacman -Syu
sudo pacman -S git jdk8-openjdk jdk17-openjdk gradle
```
After this, clone the git repository to a folder of your choice:
```bash
git clone https://github.com/TheHolyChickn/goodmod
cd goodmod
```
Now, you just need to build the mod.
```bash
./gradlew build
```
You will be able to find the mod at <tt>./build/libs/goodmod-VERSION.jar</tt>. For convenience, you can
```bash
mv build/libs/goodmod-*.jar ~/.minecraft/mods
```

<p><b><font color=#FF5555>MacOS:</font></b> lol idk</p>

<h2>Licensing</h2>

This template is licensed under the Unlicense (license copy present in this repository), or alternatively under [Creative Commons 1.0 Universal (CC0 1.0)](https://creativecommons.org/publicdomain/zero/1.0/), and all contributions and PR to this template are expected to follow this. This means your mod, based on this template can be licensed whatever way you want, and does not need to reference back to this template in any way.
[Awoo class](./src/main/java/com/github/theholychickn/theholychicknaddons/owo/Awoo.java)