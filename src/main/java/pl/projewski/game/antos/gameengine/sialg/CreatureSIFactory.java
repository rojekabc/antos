package pl.projewski.game.antos.gameengine.sialg;

public final class CreatureSIFactory {
	public static ICreatureSIAlgorithm getCreatureSI(final String name) {
		return getCreatureSI(ECreatureSI.valueOf(name));
	}

	public static ICreatureSIAlgorithm getCreatureSI(final ECreatureSI creatureSI) {
		switch (creatureSI) {
		case RANDOM:
			return new RandomCreatureSI();
		default:
			throw new IllegalStateException();
		}
	}
}
