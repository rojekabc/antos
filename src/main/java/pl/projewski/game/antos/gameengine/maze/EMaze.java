package pl.projewski.game.antos.gameengine.maze;

public enum EMaze {
	ROUNDED_MAZE(RoundedMaze.class);

	Class<?> clazz;

	EMaze(Class<?> clazz) {
		this.clazz = clazz;
	}

	public static IMaze getMazeFromName(String name) throws InstantiationException, IllegalAccessException {
		if (name == null) {
			throw new IllegalArgumentException(name);
		}
		EMaze[] values = values();
		Class<?> clazz = null;
		for (EMaze eMaze : values) {
			if (eMaze.name().equals(name.toUpperCase()) || eMaze.clazz.getSimpleName().equals(name)) {
				clazz = eMaze.clazz;
				break;
			}
		}
		if (clazz == null) {
			throw new IllegalArgumentException(name);
		}
		return (IMaze) clazz.newInstance();
	}
}
