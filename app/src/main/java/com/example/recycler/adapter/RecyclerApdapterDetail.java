package com.example.recycler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycler.entity1.Content;
import com.example.recycler.R;
import com.example.recycler.State;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerApdapterDetail extends RecyclerView.Adapter {
    private ArrayList<Content> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnclickListener onclickListener;
    DateFormat dateFormat;
    public RecyclerApdapterDetail(Context context, ArrayList<Content> list,OnclickListener onclickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.dateFormat = new SimpleDateFormat("dd-MM yyyy HH:mm");
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case State.STATE_TEXT_DETAIL:
                view = layoutInflater.inflate(R.layout.item_text, viewGroup, false);
                return new ViewHorderText(view);
            case State.STATE_IMAGE:
                view = layoutInflater.inflate(R.layout.item_image, viewGroup, false);
                return new ViewHoderImage(view);
            case State.STATE_TEXT_DESCRIPTION:
                view = layoutInflater.inflate(R.layout.item_text, viewGroup, false);
                return new ViewHorderText(view);
            case State.STATE_TEXT_TITLE:
            case State.STATE_TEXT_TIME:
                view = layoutInflater.inflate(R.layout.item_text, viewGroup, false);
                return new ViewHorderText(view);
            case State.STATE_VERTICAL:
                view = layoutInflater.inflate(R.layout.item_vertical, viewGroup, false);
                return new ViewHoderList(view);
            case State.STATE_PAGING:
                view = layoutInflater.inflate(R.layout.item_paging, viewGroup,false);
                return new ViewHoderPaging(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHorder, int i) {
        switch (list.get(i).getState()) {
            case State.STATE_TEXT_DETAIL:
                ViewHorderText viewHorderText = (ViewHorderText) viewHorder;
                viewHorderText.textView.setText(Html.fromHtml(list.get(i).getText()));
                break;
            case State.STATE_IMAGE:
                ViewHoderImage viewHoderImage = (ViewHoderImage) viewHorder;
                viewHoderImage.textView.setText(list.get(i).getTextImage());
                String link = list.get(i).getLinkImage();
                if (link.lastIndexOf("/") != -1) {
                    Glide.with(context).load(link).into(viewHoderImage.imageView);
                } else {
                    Bitmap bitmap = loadImageBitmap(context.getApplicationContext(), link);
                    Glide.with(context.getApplicationContext()).load(bitmap).into(viewHoderImage.imageView);
                }
                break;
            case State.STATE_TEXT_DESCRIPTION:
                ViewHorderText textDescroption = (ViewHorderText) viewHorder;
                textDescroption.textView.setTextSize(18);
                textDescroption.textView.setTypeface(Typeface.DEFAULT_BOLD);
                textDescroption.textView.setText(list.get(i).getText());
                break;
            case State.STATE_TEXT_TITLE:
                ViewHorderText textTitle = (ViewHorderText) viewHorder;
                textTitle.textView.setTextSize(22);
                textTitle.textView.setTextColor(Color.rgb(165, 0, 25));
                textTitle.textView.setTypeface(Typeface.DEFAULT_BOLD);
                textTitle.textView.setText(list.get(i).getText());
                break;
            case State.STATE_TEXT_TIME:
                ViewHorderText textTime = (ViewHorderText) viewHorder;
                textTime.textView.setTextSize(12);
                textTime.textView.setTextColor(Color.rgb(145, 145, 145));
                textTime.textView.setText(list.get(i).getText());
                break;
            case State.STATE_VERTICAL:
                ViewHoderList horderList = (ViewHoderList) viewHorder;
                horderList.textView.setText(list.get(i).getTextImage());
                if(list.get(i).getDate()!=null){
                    horderList.date.setText(dateFormat.format(list.get(i).getDate()));
                }

                Glide.with(context).load(list.get(i).getLinkImage()).into(horderList.imageView);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        return list.get(position).getState();
    }

    public class ViewHorderText extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHorderText(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_noidung);
        }
    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream = context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    public class ViewHoderImage extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHoderImage(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_conten_image);
            textView = itemView.findViewById(R.id.tv_conten_image);

        }
    }

    public class ViewHoderList extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        ImageView imageView;
        TextView textView, date;

        public ViewHoderList(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_vertical_item_img);
            textView = itemView.findViewById(R.id.tv_vertical_item_title);
            date = itemView.findViewById(R.id.tv_vertical_item_date);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            onclickListener.clickItem(getAdapterPosition(),list.get(getAdapterPosition()));
        }
    }
    public class ViewHoderPaging extends RecyclerView.ViewHolder{
        public ViewHoderPaging(@NonNull View itemView) {
            super(itemView);
        }
    }
    public interface OnclickListener{
       public void clickItem(int poisition,Content content);
    }
}