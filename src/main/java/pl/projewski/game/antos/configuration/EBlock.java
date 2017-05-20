package pl.projewski.game.antos.configuration;

import lombok.Getter;
import lombok.Setter;

public enum EBlock {
	RIP, SIMPLE;

	@Getter
	@Setter
	private String imageResource;
}
