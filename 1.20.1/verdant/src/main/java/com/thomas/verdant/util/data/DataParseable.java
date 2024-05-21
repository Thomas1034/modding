package com.thomas.verdant.util.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public interface DataParseable<T> {
	abstract T parse(Gson gson, JsonElement element);

}
