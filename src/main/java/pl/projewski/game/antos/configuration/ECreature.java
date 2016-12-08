package pl.projewski.game.antos.configuration;

import lombok.Getter;
import lombok.Setter;

public enum ECreature {
	PLAYER, GHOST;

	@Getter
	@Setter
	private int hp;

	@Getter
	@Setter
	private String imageResource;

	@Getter
	@Setter
	private int minAttack;

	@Getter
	@Setter
	private int rndAttack;

	@Getter
	@Setter
	private String ripBlockName;
}
