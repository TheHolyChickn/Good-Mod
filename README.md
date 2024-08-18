<h1>good mod</h1>

a good mod

Contributors:
- [TheHolyChickn (main developer)](https://github.com/TheHolyChickn/)
- [bonsai (made the STUPID GUI WORK)](https://github.com/freebonsai)
- [odtheking (helped with random stuff)](https://github.com/odtheking)
- [AzuredBlue (told me to google something)](https://github.com/AzuredBlue)
- [this tutorial and template odtheking told me to ~~steal~~ look at (u guys rlly thought i could set this all up on my own with no past experience??? lolllll)](https://moddev.nea.moe/)
- [ChatGPT (all of the code)](https://chatgpt.com/)
- kikias22 (artist)
- [Intellij Idea (nvim still better)](https://www.jetbrains.com/idea/)
- [Linux (it just makes doing things easier)](https://en.wikipedia.org/wiki/Linux)

<h2><font color=#00FF99>Features</font></h2>
good mod offers two features I've wanted for a long time, but are not present in <a href="https://github.com/Skytils/SkytilsMod/tree/dev">Skytils</a> or <a href="https://skyblockextras.com/">SBE</a>.

1. **Dungeon Drops Tracker**<p>
<a href="https://skyblockextras.com/">SBE</a> actually has this option, but it's extremely outdated and randomly resets itself sometimes. This solution has both a GUI and a chat command element. At some point I will make a good Croesus scanner too because honestly it's not hard.</p>

2. **Hoppity Reminder** (THIS FEATURE IS NOT YET AVAILABLE)<p>
I have lost way too many eggs due to being in a dungeon run and just forgetting about Hoppity. This solution creates an impossible to miss Hoppity alert, and also has the option to automatically send <tt>!dt Hoppity</tt> in chat.

In the future, I may implement anti-ironman dark auction alerts, so you can outbid any time an ironman player is winning in the dark auction.

<h2><font color=#00FF99>Installation</font></h2>

good mod is a [Forge](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) mod, so if you don't already have Forge, [download the latest 1.8.9 release](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.8.9.html) here, and follow the installer's instructions. If you've never downloaded forge before, notice that the download button redirects you to an ad, and you have to wait 5 seconds (see top right corner) before you can proceed to download. **ONLY** click the button in the top right corner, or you could get a virus. After you install forge, find the <tt>1.8.9-forge1.8.9-11.15.1.2318-1.8.9</tt> version in your Minecraft launcher (you may have to go over to Installations and create a new one), and run it. After it loads, close your game. I still have to set up Minecraft on my laptop's Windows partition, so I may record a video of installing forge and post it here later.
<h3>Normal Install</h3>

Download the <b><font color=#FF0000>.jar</font></b> file in the [latest release](https://google.com). Locate your <tt>.minecraft</tt> folder. On windows, you can find this by opening File Explorer, and searching for <tt>%appdata%</tt>. On Linux, your <tt>.minecraft</tt> is probably located at <tt>~/.minecraft</tt>. On MacOS, I have no idea. Once inside your <tt>.minecraft</tt> folder, find and open your <tt>mods</tt> folder. Now, open another File Explorer window and navigate to your <tt>Downloads</tt> folder. Locate the **good mod .jar file**, and drag it into your <tt>mods</tt> folder.</p>

<h3>Wannabe Hacker Install</h3>

Alternatively if you're a fan of using the terminal, just copy one of these commands for your respective system:

Windows Powershell:
```shell
mv .\Downloads\goodmod-*.jar .\AppData\Roaming\.minecraft\mods
```
Linux:
```bash
mv Downloads/goodmod-*.jar .minecraft/mods
```
MacOS: lol idk

If you would like to compile from source, I have included instructions at the bottom of this page.

<h2><font color=#00FF99>Usage</font></h2>

<p>
good mod is a mod for Hypixel SkyBlock, which provides two features that I was tired of waiting for <a href="https://github.com/Skytils/SkytilsMod/tree/dev">Skytils</a> or <a href="https://skyblockextras.com/">SBE</a> to have, so I made them myself. The good mod configuration menu is accessible by running <code>/nicepb</code>. Incase of commands conflicting with other mods, any good mod command with default <code>{command_name}</code> can also be run via the alias <code>/goodmod:{command_name}</code>. For example, if another mod has <code>/nicepb</code> already set, you can open the configuration menu with <code>/goodmod:nicepb</code>.
</p>
<p>
Every command is configurable. This means if you don't like the command for the config menu being <code>/nicepb</code>, you can change it directly in the config menu. You cannot change the alias <code>/goodmod:{command_name}</code>, you can only change the command name itself. If at any time you forget the command names you've set, run <code>/goodmod:commands</code> to return a list of commands you can call. You can also manually edit the commands in the configuration file.
</p>

In the config menu, you will see three things: two input bars, and a bar called "stuff display".

![alt text](https://github.com/TheHolyChickn/Good-Mod/blob/readme-assets/2024-08-18_01.53.30.png?raw=true)

The top input bar is where you can change the command alias for the <tt>/nicepb</tt> command. The bottom input bar is where you change the alias for the <tt>/owo</tt> command, who's functionality I will explain in a second. There are no other commands.

Clicking the "stuff display" button will display all of your dungeon drops in a "neat" display (i will improve it in the future dw).

![alt text](https://github.com/TheHolyChickn/Good-Mod/blob/readme-assets/2024-08-18_01.55.18.png)

If you want a chat readout of this, use the <tt>/owo</tt> command.

<h2>Known Bugs</h2>
<p>If you see a bug, report it, and I'll add it here until I fix it!</p>

- The "stuff display" GUI: This display will likely break or have items cropped out if you do not play in fullscreen, or if you play in a resolution below 1080p. Additionally, you might find the display too small on some large monitors. Automatic scaling will be implemented in the future.
- in the configuration menu, the blinking cursor will never go away on the top bar. While this does not affect functionality, it's very weird to have both cursors blinking. I have no idea how to fix this but I'll probably figure it out

<h2>Future Plans</h2>

I plan to do the following:

- Fix bugs
- Clean up the stuff display and make it look nicer
- Implement toggleable options for the stuff display: either the current display, or display drops on each floor (like split up the recombs you drop per floor)
  - this will require making a new configuration file, so I will move both the drops.json and goodmod.json to their own folder in the config file when I do this, and will make sure to copy the drops.json file contents over if they exist. On the version I release this functionality, the copying mechanism will be run every startup, but in the following version this will be deprecated, and you will need to run a command ingame to copy it over.
- Clean up the config files by shoving them into their own subdirectory of the config directory
- finish the readme
- implement an option to export dungeon drops to a nicely formatted image file you can share with your friends to flex
- customizability of what items are sent on /owo
- a toggleable gui element which tracks a list of items you can customize
- tracking kismets per floor
- Croesus chest highlights/profit calc based on lowest bin. I know this exist but all the mods suck, and mine will also tell you whether to use a dungeon key/reroll or not
  - this feature will allow you to choose to use the Public API, which after I set it up, will update Bin/Bazaar prices of all dungeon items once per minute, or you can opt to use your own Developer API Key, which will default to updating once per 10 seconds, but you can customize the delay if you choose this option

<h2>Contributing</h2>

See the section on compiling from source for detailed instructions on how to compile the mod yourself.

If you want to contribute, feel free to make a pull request with your changes. However, I would like to warn you that my file structure and every single class/variable name has been intentionally screwed. So good luck actually contributing, because you might not have the easiest time figuring out what the "stopOWOing" function does, or the "Fuwwy" class, etc.

If anyone out there is insane enough to fix all of the variable names and file structure, I will keep the changes and credit you in the top section. Consider this a challenge, I guess.

Also even the parts of my code that weren't intentionally screwed up are probably still bad, because I'm not a developer, I'm a random math & physics nerd who plays skyblock and I unironically used ChatGPT a lot to help me get started or for debugging. So improvements to the code would definitely be appreciated because I honestly don't know how.

<h2>Compiling from Source</h2>
<p>If you want to modify the source code of the mod, or you just want to be edgy and cool, you will need to compile the mod directly from the source code.</p>

<h3>Windows:</h3>
<p>I've included some commentary on what everything is/what it does, since many people (like I used to be) are scared of the terminal and think they're going to break their operating system somehow. And I have no idea how else to build a mod. But I'm sure there's a way to do it inside of Intellij or whatever u use idk</p>

The first step is to make sure we have the correct dependencies. You will need [Git](https://git-scm.com/), Java JDK for versions [8](https://adoptium.net/temurin/releases/?version=8) and [17](https://adoptium.net/temurin/releases/?version=17), and [gradle](https://gradle.org/releases/?_gl=1*5x1gva*_gcl_au*MjEzMjE0Nzg3OC4xNzIzNzMwNzk1*_ga*MjY4NDUyNjEzLjE3MjM3MzA3OTU.*_ga_7W7NC6YNPT*MTcyMzczMDc5NS4xLjEuMTcyMzczMDg2Ny41OC4wLjA.). For Git, you can open Powershell and run
```
winget install --id Git.Git -e --source winget
```
<tt>winget</tt> is (from what I understand) a "package manager" for PowerShell. You can think of it as a sort of "app store"; it downloads "packages", which are like apps, but oftentimes they provide additional functionality to a terminal like PowerShell. Here, we're downloading "Git", which allows us to work with various "git repositories", like github pages.

**THIS SECTION IS NOT FINISHED ILL WRITE IT WHEN I WAKE UP OK**

<h3>Linux:</h3>

 I will not be explaining anything, because if you're on Linux I trust you to know most of this stuff already.

You will need [Git](https://git-scm.com/), Java JDK for versions [8](https://adoptium.net/temurin/releases/?version=8) and [17](https://adoptium.net/temurin/releases/?version=17), and [gradle](https://gradle.org/releases/?_gl=1*5x1gva*_gcl_au*MjEzMjE0Nzg3OC4xNzIzNzMwNzk1*_ga*MjY4NDUyNjEzLjE3MjM3MzA3OTU.*_ga_7W7NC6YNPT*MTcyMzczMDc5NS4xLjEuMTcyMzczMDg2Ny41OC4wLjA.).

Debian/Ubuntu
```bash
sudo apt install git openjdk-8-jdk openjdk-17-jdk gradle
```
Arch
```bash
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

<h3>MacOS:</h3> lol idk</p>

<h2>Licensing</h2>

This mod is licensed under the Unlicense (license copy present in this repository), or alternatively under [Creative Commons 1.0 Universal (CC0 1.0)](https://creativecommons.org/publicdomain/zero/1.0/), and all contributions and PR to this mod are expected to follow this. I decided on this licensing because this is what the template I copied said I am required to use, but honestly idc what u do with the mod as long as u dont put a rat in it and pass it off as legit
