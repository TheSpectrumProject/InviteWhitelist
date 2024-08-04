# InviteWhitelist
InviteWL is a Bukkit plugin for Minecraft servers that manages player invitations. It allows players to invite others and enforces invitation-based access control during player login.
For spigot 1.20+
Features
Invite Players: Use the /invite <name> command to invite players to the server. The plugin records the inviter's name and the time of invitation.
Inspect Invitations: Use the /inviteinspect <name> command (requires OP permissions) to view the invitation record of a specified player, including who invited them and when, as well as any players they have invited.
Login Check: Players must have a valid invitation to log in. If a player is not on the invitation list, they will be denied access with a custom message.
Commands
/invite <name>
Description: Invites a player to the server.
Usage: /invite <name>
Permissions: None (available to all players)
/inviteinspect <name>
Description: Inspects the invitation record of a player.
Usage: /inviteinspect <name>
Permissions: OP only
Configuration
The plugin uses a configuration file to store invitation data. The file is automatically created with default settings when the plugin is first enabled. You can modify the configuration file to adjust settings as needed.

Installation
Download the InviteWL.jar file.
Place the InviteWL.jar file in the plugins folder of your Minecraft server.
Restart the server or use the /reload command to load the plugin.
Dependencies
Requires Bukkit or a compatible server implementation (e.g., Spigot, Paper).
Development
Feel free to contribute to the development of InviteWL! For feature requests, bug reports, or contributions, please open an issue or a pull request.
