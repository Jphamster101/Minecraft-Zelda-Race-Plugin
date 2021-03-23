package me.Johnny.minecraftZeldaStuff;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.apache.commons.lang.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import me.Johnny.minecraftZeldaStuff.Models.TeamType;

// Custom PersistentDataType
// Converts Custom Data Type into an array of bytes, serialize and converted
// to an object input stream to be read and utilized as a custom PersistentDataType

public class TeamDataType implements PersistentDataType<byte[], TeamType>{

	@Override
	public Class<TeamType> getComplexType() {
		return TeamType.class;
	}

	@Override
	public Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@Override
	public byte[] toPrimitive(TeamType complex, PersistentDataAdapterContext arg1) {
		return SerializationUtils.serialize(complex);
	}
	
	@Override
	public TeamType fromPrimitive(byte[] primitive, PersistentDataAdapterContext arg1) {
		try {
			InputStream is = new ByteArrayInputStream(primitive);
			ObjectInputStream o = new ObjectInputStream(is);
			return (TeamType) o.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
