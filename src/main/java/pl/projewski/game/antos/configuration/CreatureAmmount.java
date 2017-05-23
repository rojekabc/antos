package pl.projewski.game.antos.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatureAmmount {
	private String creature;
	private int minAmmount;
	private int randAmmount;
}
