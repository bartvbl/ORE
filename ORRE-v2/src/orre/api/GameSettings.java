package orre.api;

public class GameSettings {
	public final String gameName;
	public final PropertyTypeProvider propertyTypeProvider;

	public GameSettings(String gameName, PropertyTypeProvider propertyTypes) {
		this.gameName = gameName;
		this.propertyTypeProvider = propertyTypes;
	}
}
