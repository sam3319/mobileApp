package com.example.chapter12codingchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.movie_list_item, movies);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.movie_list_item, parent, false);

            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.textViewTitle);
            holder.directorYearTextView = convertView.findViewById(R.id.textViewDirectorYear);
            holder.ratingCountryTextView = convertView.findViewById(R.id.textViewRatingCountry);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie currentMovie = movies.get(position);

        holder.titleTextView.setText(currentMovie.getTitle());
        holder.directorYearTextView.setText("감독: " + currentMovie.getDirector() + " | " + currentMovie.getYear() + "년");
        holder.ratingCountryTextView.setText("평점: " + currentMovie.getRating() + " | " + currentMovie.getCountry());

        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView directorYearTextView;
        TextView ratingCountryTextView;
    }
}
