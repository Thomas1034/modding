package com.thomas.turbulent.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public interface DataParseable<T> {
	public abstract T parse(Gson gson, JsonElement element);
}
