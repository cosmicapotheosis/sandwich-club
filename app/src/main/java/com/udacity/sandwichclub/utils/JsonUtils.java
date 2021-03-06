package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /**
     * Parses the JSON that represents the data for a given sandwich
     *
     * @param json read from resources
     * @return Sandwich class
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        // The main JSON object
        JSONObject sandwich = new JSONObject(json);
        // Name sub JSON
        JSONObject name = sandwich.getJSONObject("name");

        String mainName = name.optString("mainName");
        // alsoKnownAs is an array. Needs to be converted to String list
        JSONArray alsoKnownAsJson = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<String>();
        for (int i = 0; i < alsoKnownAsJson.length(); i++)
            alsoKnownAs.add(alsoKnownAsJson.optString(i));

        String placeOfOrigin = sandwich.optString("placeOfOrigin");

        String description = sandwich.optString("description");

        String image = sandwich.optString("image");

        JSONArray ingredientsJson = sandwich.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<String>();
        for (int i = 0; i < ingredientsJson.length(); i++)
            ingredients.add(ingredientsJson.optString(i));
        // Make a good sandwich
        Sandwich parsedSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                description, image, ingredients);

        return parsedSandwich;
    }
}
