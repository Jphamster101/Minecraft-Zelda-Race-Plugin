package me.Johnny.PersistentTeamDataType;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;

//Video referenced: https://www.youtube.com/watch?v=vswQvReadrc

public class SkinGrabber {
	public static void changeSkin(Player player, String team) {
		GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(
				PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
				((CraftPlayer) player).getHandle()));
		
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", getSkin(team));
		
		connection.sendPacket(new PacketPlayOutPlayerInfo(
				PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
				((CraftPlayer) player).getHandle()));
	}
	
	private static Property getSkin(String team) {
		if (team.equalsIgnoreCase("red")) {
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjA4Nzk0MzU1MCwKICAicHJvZmlsZUlkIiA6ICI5OTdjZjFlMmY1NGQ0YzEyOWY2ZjU5ZTVlNjU1YjZmNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJpbzEyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQzZDZjMjA4YjFmZGY5MzQ5NGRjOGJkMTdhOTI4MWM0MjEwNmY1Y2FiNjYxZTM3MmZlNzgyZWZjMzJmM2EyNCIKICAgIH0KICB9Cn0=", "PNa9PxXNWzaAaGf8ov5+be6ItErnA+K1+N01nmvxSYd2HaeKsLiRPX8wbN/cxmS0GECZXB/qLY8KLdTDvmzqSXc06W9HiJwMuBINUTX4WaeEGBWm7MwLN1xPWYpcskW+B3Tyja3iFHjMstTzBw8LaLlK+E1+X14ee5k8L4LcxN3ocdH5aK0NKOx2W1KwB1nj9lUh2KUF0OfOl5YpEdo9gfDeKNv3LgR+fKvxFsKB/kyWiat3HMkStG3fTqlu0WTZQj6jRX1NrjaJ/pTsz5qJtFCg+bRkONkTcQnnY2ut/B8oZ93JHvwCwZHRzkPfCtrd5PDB4k+EN9nDUj1eFYEoGX2GKbzkBTIZMgAh5fY2l/I4GgUDQerMqntT6njXUB4yKrFjl6/+e4EZJ9iBnJVIdEPd6Wt+tUvJjWslBOOK/+eHM2v3rEeVj8jk97/QosisWf/Yub6ytwDM46O9zemotfSSczgn1MoNrY7uoHMuqOCz/7KnENJ5Q9UzSi1C1UjCIc71aRCVHGAd4kdfJS8V0BMZkiBUpZcHaTywNt1d16+0EUdJkUtlyqMU4M/cXZpAJQ7+2syuEurmfTe3wgJLRunoKusqYBmM1Zkr63ipxhgqehUdXDEpj3Of38EXgg54D+wEtQQh6qZ/+85BiYV4Dj0uWESNu2ZXvgawyUKAMRk=");
		}
		else if (team.equalsIgnoreCase("blue")) {
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjA4OTkwNTk5NCwKICAicHJvZmlsZUlkIiA6ICI2MWVhMDkyM2FhNDQ0OTEwYmNlZjViZmQ2ZDNjMGQ1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVEYXJ0aEZhdGhlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mOGRkYTRiMDk2MzRmNmFlZDQxMjA5ZGUzNjY0NDkzOGU5OWUyNjU4MjUzMDA0OGVjNGFlYzQ3NDFiNDIxNzcwIgogICAgfQogIH0KfQ==", "Suoo6PwjFCrioopNRylA4RKizdTasVHN7LdIofqvb0mROLir7Ey0WbtawX/ELNag04m5CFnjI2pzv/pygR8J8SRKPCvp6AmbmMfnLw5pDMX5O2uPps896357gRB2N+9BhrJWAiON3bYmyxifIVCTFQlKKsviL632qT4cJLtPUmeKWBPcO8xt44AFxM9VyZZLbl0ug/ZDWps3Z+Bb1QWfvSZCEI/mmhupyVRl7RkXyKuU/rY1kwrAG//pZBmq9DhjcPlguB3YJByuEKmt9FH8UqfBZ1OKgQjXlr2SLiDPIQ2wRc8ZSmbxrr74oTyqhWD86i3GFO3BhfKgNizVtm1s1r26EaW4+jI5FUchAdKagBr4UAUX2AFw0EnwI+v9fJloal1KS+8t04YwddyRXEDDGcWCXpbEq8qgrCV+T5tMPNRyYWfoN72Jz2qvRFHKk8tHKPAGVx3D91hbwUku1Y+UDwnokCTTUWo/q2gKN/w+Ghkzw1+ax+cLduhPUh/bYpztUtJ2/d62J0IQLPvpn9X2yDOEeFdVOyu1mYEr/1gsQqi2GrI2rf9zRU9jhhsieNNKrizSEay1jSRQLTdQOj591eMeUxvbc9ASD5wQSwnF7tSjbrAZS9wKM2Hok/nTFj5OPG6thuWVkOUTDLb2Ziwils9EYC3lDQznXEjrNlolmFk=");
		}
		else if (team.equalsIgnoreCase("neutral")) {
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjA5MDMzOTk0MSwKICAicHJvZmlsZUlkIiA6ICIxZDUyMzNkMzg4NjI0YmFmYjAwZTMxNTBhN2FhM2E4OSIsCiAgInByb2ZpbGVOYW1lIiA6ICIwMDAwMDAwMDAwMDAwMDBKIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJkMmMxMmI3ZDM2ODkxYjY1ZjgxNWIzM2MwZmNlZTA4NDcyMjY1NTdkNmE3ZmI0OGQ1NDZhNDJhNzVhMDFhMDUiCiAgICB9CiAgfQp9", "TB9Nf1pwIsvzRnAVgDpiiJdBSXKz7WLsJx13z4KW1za335SVfu+70njLk3XZ2BMbYRvABOMLRnxhNIIei9ID9Yr9Oca9CQrjJh/wh2XEgjjtTOqT6Zd5Uw8DiY8kq3R8oLj5NLncWKFE3Crlhg4TPeaRalInbtntNKNQtlxVTMcshp6YgU5d8udxroYlRR5CLmklc51lxYoJsPt86M2306dntoUmW7ECl0WoJBKjKOM2ZXAlAJEigtTwGhmx6ES+wmT23bIOptoXQS6MC6YruKMPF3M+hTkWQZ6UOFQtVjwz1VJSXOeAcdjWXEB87K8baIRGzjH6zvYybZo5kPJNtouch4ZJr4U3CBEF5BERDowdEMNk1DNgw5FctCTgXuCN/Ep/2tacW2cDME9I0h3lWK5BQ1tk8eL2n/wuAq4DzD6jVz8zSwpHSb7Fy0KIFQMIbFmvLmeE734h4/z2ovHcWu+yhWOLXzK65/DA55XUJ/ohorDKKEYBHuZlrO52FWJRkts276fPBzNwijoYDs5XtwYsJop0vAr+Xxko1jorMiOj4Gb/r8E3SU4Nk2XMRpso8xa+0U0zkP+gShlH9EmlqXsOS20ELweeoI1+vhpZMcI6SxnGYyQ6nw1vYwoY0rIq+UNDq4wOMwlqfZDa5sQheBi9Tj/8IMvYR05j9wdJ0/M=");
		}
		else {
			return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjA5MDMzOTk0MSwKICAicHJvZmlsZUlkIiA6ICIxZDUyMzNkMzg4NjI0YmFmYjAwZTMxNTBhN2FhM2E4OSIsCiAgInByb2ZpbGVOYW1lIiA6ICIwMDAwMDAwMDAwMDAwMDBKIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJkMmMxMmI3ZDM2ODkxYjY1ZjgxNWIzM2MwZmNlZTA4NDcyMjY1NTdkNmE3ZmI0OGQ1NDZhNDJhNzVhMDFhMDUiCiAgICB9CiAgfQp9", "TB9Nf1pwIsvzRnAVgDpiiJdBSXKz7WLsJx13z4KW1za335SVfu+70njLk3XZ2BMbYRvABOMLRnxhNIIei9ID9Yr9Oca9CQrjJh/wh2XEgjjtTOqT6Zd5Uw8DiY8kq3R8oLj5NLncWKFE3Crlhg4TPeaRalInbtntNKNQtlxVTMcshp6YgU5d8udxroYlRR5CLmklc51lxYoJsPt86M2306dntoUmW7ECl0WoJBKjKOM2ZXAlAJEigtTwGhmx6ES+wmT23bIOptoXQS6MC6YruKMPF3M+hTkWQZ6UOFQtVjwz1VJSXOeAcdjWXEB87K8baIRGzjH6zvYybZo5kPJNtouch4ZJr4U3CBEF5BERDowdEMNk1DNgw5FctCTgXuCN/Ep/2tacW2cDME9I0h3lWK5BQ1tk8eL2n/wuAq4DzD6jVz8zSwpHSb7Fy0KIFQMIbFmvLmeE734h4/z2ovHcWu+yhWOLXzK65/DA55XUJ/ohorDKKEYBHuZlrO52FWJRkts276fPBzNwijoYDs5XtwYsJop0vAr+Xxko1jorMiOj4Gb/r8E3SU4Nk2XMRpso8xa+0U0zkP+gShlH9EmlqXsOS20ELweeoI1+vhpZMcI6SxnGYyQ6nw1vYwoY0rIq+UNDq4wOMwlqfZDa5sQheBi9Tj/8IMvYR05j9wdJ0/M=");
		}
		
	}
}
