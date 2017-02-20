package pl.projewski.game.antos.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ECreature {
	PLAYER, GHOST;

	@Setter
	private int hp;
	@Setter
	private String imageResource;
	@Setter
	private int minAttack;
	@Setter
	private int rndAttack;
	@Setter
	private String ripBlockName;
	@Setter
	private String creatureSI;
}
