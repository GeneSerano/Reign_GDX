package com.southcentralpositronics.reign_gdx.entity.spawner;

import com.southcentralpositronics.reign_gdx.entity.Entity;
import com.southcentralpositronics.reign_gdx.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Spawner extends Entity {

	private final List<Entity> entities = new ArrayList<Entity>();
	private final Type         type;

	public Spawner(double x, double y, Type type, int amount, Level level) {
		init(level);
		this.x    = x;
		this.y    = y;
		this.type = type;
	}

	public enum Type {
		MOD, PARTICLE
	}
}
