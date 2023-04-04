package com.orania.gemobox.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orania.gemobox.R;
import com.orania.gemobox.entities.Person;
import com.orania.gemobox.util.ItemsDetail;
import com.orania.gemobox.util.ViewHolderWithDetails;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public class PersonsRCVAdapter extends RecyclerView.Adapter<PersonsRCVAdapter.ViewHolder> {
    private OnPersonClickListener personClickListener;
    //private OnPersonLongClickListener personLongClickListener;
    private List<Person> personList;
    private Context context;
    private SelectionTracker selectionTracker;

    public PersonsRCVAdapter(List<Person> personList, Context context, OnPersonClickListener onPersonClickListener) {
        this.personList = personList;
        this.context = context;
        this.personClickListener = onPersonClickListener;
        //this.personLongClickListener = onPersonLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_row, parent, false);

        return new ViewHolder(view, personClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person = Objects.requireNonNull(personList.get(position));
        holder.fName.setText(person.getPersonFirstName());
        holder.lName.setText(person.getPersonLastName());

        holder.bind(person, selectionTracker.isSelected(person));

        Log.d("PERSONSADAP", "getItemId "+holder.getItemId()+"");
        Log.d("PERSONSADAP", "getItemViewType "+holder.getItemViewType()+"");
        Log.d("PERSONSADAP", "getClass "+holder.getClass().toString());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(personList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ViewHolderWithDetails {
        OnPersonClickListener onPersonClickListener;
        //OnPersonLongClickListener onPersonLongClickListener;
        public TextView fName;
        public TextView lName;

        public ViewHolder(@NonNull View itemView, OnPersonClickListener onPersonClickListener) {
            super(itemView);

            fName = itemView.findViewById(R.id.row_fname_textview);
            lName = itemView.findViewById(R.id.row_lname_textview);
            this.onPersonClickListener = onPersonClickListener;
            //this.onPersonLongClickListener = onPersonLongClickListener;

            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("MOVIE", "onClick: inside");
            onPersonClickListener.onPersonClick(getAdapterPosition());
        }

        //@Override
        //public boolean onLongClick(View v) {
            //Log.d("MOVIE", "onLongClick: inside");
            //onPersonLongClickListener.onPersonLongClick(getAdapterPosition());
            //return true; //or false
        //}

        public final void bind(Person person, boolean isActive) {
            itemView.setActivated(isActive);
            Log.d("DUPAKWAS", "isActive " + isActive);
        }

        @Override
        public ItemDetailsLookup.ItemDetails getItemDetails() {
            return new ItemsDetail(getAdapterPosition(), personList.get(getAdapterPosition()));
        }
    }

    public interface OnPersonClickListener {
        void onPersonClick(int position);
    }

    //public interface OnPersonLongClickListener {
        //void onPersonLongClick(int position);
    //}

    public void setSelectionTracker(SelectionTracker selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @Override
    public long getItemId(int position) {
        Log.d("DUPAKWAS", "getIdPerson " + personList.get(position).getIdPerson());
        return personList.get(position).getIdPerson();
    }


}
