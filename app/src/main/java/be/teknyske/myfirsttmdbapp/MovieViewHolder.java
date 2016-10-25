package be.teknyske.myfirsttmdbapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


// Stelt een "fiche" voor één film voor !!
public class MovieViewHolder extends RecyclerView.ViewHolder

{
    private CardView cardView;
    private ImageView imageView;
    private TextView titleTextView;
    private TextView descriptionTextview;
    private TextView releaseDateView;


    public MovieViewHolder(View view)
        {
        super(view);
        cardView = (CardView) view.findViewById(R.id.cardView);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        titleTextView = (TextView) view.findViewById(R.id.title);
        descriptionTextview = (TextView) view.findViewById(R.id.description);
        releaseDateView = (TextView) view.findViewById(R.id.releasedate);
        }

    public CardView getCardView()
        {
        return cardView;
        }

    public void setCardView(CardView cardView)
        {
        this.cardView = cardView;
        }

    public ImageView getImageView()
        {
        return imageView;
        }

    public void setImageView(ImageView imageView)
        {
        this.imageView = imageView;
        }

    public TextView getTitleTextView()
        {
        return titleTextView;
        }

    public void setTitleTextView(TextView titleTextView)
        {
        this.titleTextView = titleTextView;
        }

    public TextView getDescriptionTextview()
        {
        return descriptionTextview;
        }

    public void setDescriptionTextview(TextView descriptionTextview)
        {
        this.descriptionTextview = descriptionTextview;
        }

    public TextView getReleaseDateView()
        {
        return releaseDateView;
        }

    public void setReleaseDateView(TextView releaseDateView)
        {
        this.releaseDateView = releaseDateView;
        }


}
