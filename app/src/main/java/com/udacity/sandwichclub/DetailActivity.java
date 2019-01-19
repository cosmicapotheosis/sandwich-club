package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Text views to display sandwich details
    //private TextView mAlsoKnownAs;
    //private TextView mPlaceOfOrigin;
    //private TextView mDescription;
    //private TextView mIngredients;
    @BindView(R.id.also_known_tv) TextView mAlsoKnownAs;
    @BindView(R.id.origin_tv) TextView mPlaceOfOrigin;
    @BindView(R.id.description_tv) TextView mDescription;
    @BindView(R.id.ingredients_tv) TextView mIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // Find the text views for each of the details
        //mAlsoKnownAs = findViewById(R.id.also_known_tv);
        //mPlaceOfOrigin = findViewById(R.id.origin_tv);
        //mDescription = findViewById(R.id.description_tv);
        //mIngredients = findViewById(R.id.ingredients_tv);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI();
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .placeholder(R.drawable.cool_rick)
                    .error(R.drawable.cool_rick)
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());

            // Set the text views to display retrieved JSON data
            // Need to iterate through list for alsoKnownAs and Ingredients
            // and add it all into a single string
            StringBuilder alsoKnownAsString = new StringBuilder();
            int alsoKnownAsListLength = sandwich.getAlsoKnownAs().size();
            for (int i = 0; i < alsoKnownAsListLength; i++) {
                alsoKnownAsString.append(sandwich.getAlsoKnownAs().get(i));
                if (i < alsoKnownAsListLength - 1) {
                    alsoKnownAsString.append(", ");
                }
            }
            mAlsoKnownAs.setText(alsoKnownAsString);

            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());

            mDescription.setText(sandwich.getDescription());

            StringBuilder ingredientsString = new StringBuilder();
            int ingredientsListLength = sandwich.getIngredients().size();
            for (int i = 0; i < ingredientsListLength; i++) {
                ingredientsString.append(sandwich.getIngredients().get(i));
                if (i < ingredientsListLength - 1) {
                    ingredientsString.append(", ");
                }
            }
            mIngredients.setText(ingredientsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
