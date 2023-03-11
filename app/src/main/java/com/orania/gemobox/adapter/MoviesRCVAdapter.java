package com.orania.gemobox.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orania.gemobox.R;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.relations.MovieDirectorWithPerson;
import com.orania.gemobox.relations.MovieWithDirectors;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesRCVAdapter extends RecyclerView.Adapter<MoviesRCVAdapter.ViewHolder> {
    private OnMovieClickListener movieClickListener;
    private List<MovieWithDirectors> moviesList;
    private Context context;

    public MoviesRCVAdapter(List<MovieWithDirectors> moviesList, Context context, OnMovieClickListener onMovieClickListener) {
        this.moviesList = moviesList;
        this.context = context;
        this.movieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view, movieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieWithDirectors movieWithDirectors = Objects.requireNonNull(moviesList.get(position));
        holder.movieTitle.setText(movieWithDirectors.movie.getMovieTitle());

        StringBuilder directorsBldr = new StringBuilder();
        for(MovieDirectorWithPerson mdwp: movieWithDirectors.movieWithDirectors){
            for(Person p: mdwp.personList){
                Log.d("MOVIE", "MovieRCVAdapter Directors " + p.getPersonFirstName() + " " + p.getPersonLastName());
                directorsBldr.append(p.getPersonFirstName()).append(" ").append(p.getPersonLastName());
            }
        }

        holder.movieDirectors.setText(directorsBldr);
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(moviesList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnMovieClickListener onMovieClickListener;

        public TextView movieTitle;
        public TextView movieDirectors;

        public ViewHolder(@NonNull View itemView, OnMovieClickListener onMovieClickListener) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.row_movie_title_textview);
            movieDirectors = itemView.findViewById(R.id.row_novie_directors_textview);
            this.onMovieClickListener = onMovieClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMovieClickListener.onMovieClick(getAdapterPosition());
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(int position);
    }


}
