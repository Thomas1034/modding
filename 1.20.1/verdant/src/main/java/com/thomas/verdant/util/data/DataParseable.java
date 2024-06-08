package com.thomas.verdant.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;

public interface DataParseable<T> {
	public abstract T parse(Gson gson, JsonElement element);
}
