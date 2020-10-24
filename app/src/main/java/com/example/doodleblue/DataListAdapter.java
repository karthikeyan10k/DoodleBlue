package com.example.doodleblue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder> {


        private Context context;
        private ArrayList<PriceJsonResponse.datalist> mDatList;
        private List<PriceJsonResponse> mResponseList;


    public DataListAdapter(Context priceFragment, ArrayList<PriceJsonResponse.datalist> mDataList) {

 this.context=priceFragment;
 this.mDatList=mDataList;


    }


    @Override
        public DataListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(context)
                    .inflate(R.layout.price_adapter, parent, false);

            return new MyViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            DecimalFormat df2 = new DecimalFormat(".##");
            final PriceJsonResponse.datalist getlist = mDatList.get(position);

            holder.mName.setText(String.valueOf(getlist.name));

            double price=Double.valueOf(getlist.getPrice());
            holder.mPrice.setText("$"+String.valueOf(df2.format(price)));
            double changepercent=Double.valueOf(getlist.getChangepercent());
            holder.mChange.setText(String.valueOf(df2.format(changepercent))+"%");




            TextView tv = new TextView(context);
            tv.setText(getlist.symbol);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(4f);
            tv.setBackgroundColor(Color.GRAY);


            Bitmap testB;

            testB = Bitmap.createBitmap(100, 35, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(testB);
            tv.setGravity(Gravity.CENTER);
            tv.layout(0, 0, 100, 100);
            tv.draw(c);


        //    holder.mCircularView.setLayoutParams(layoutParams);
            holder.mCircularView.setBackgroundColor(Color.WHITE);
         //   holder.mCircularView.setImageBitmap(testB);
       /*     holder.mCircularView.setMaxHeight(80);
            holder.mCircularView.setMaxWidth(80);*/
            holder.mSerialNumber.setText(String.valueOf(position+1));



            RequestOptions options = new RequestOptions()

                      .centerCrop()
                    .centerInside()
                    .fitCenter()

                .transform(new CircleImageTransform( context));


            Glide.with(context).load(testB).apply(options).into(holder.mCircularView);







        }



    private Bitmap getTransparentBitmapCopy(Bitmap source)
    {
        int width =  300;
        int height = 400;
        Bitmap copy = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] pixels = new int[width * height];
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        copy.setPixels(pixels, 0, width, 0, 0, width, height);
        return copy;
    }





        @Override
        public int getItemCount() {

            return mDatList.size();

        }

    public void filterList(ArrayList<PriceJsonResponse.datalist> mFilteredResponse) {
        this.mDatList=mFilteredResponse;
        notifyDataSetChanged();
    }


/*
    public void filterList(ArrayList<channeljsonresponse> mFilteredResponse, String name) {
        this.mResponse = mFilteredResponse;
        this.name=name;

        notifyDataSetChanged();
    }*/


        public class MyViewHolder extends RecyclerView.ViewHolder {


           @BindView(R.id.serialNumber)
           TextView mSerialNumber;

            @BindView(R.id.name)
            TextView mName;

            @BindView(R.id.price)
            TextView mPrice;

            @BindView(R.id.change)
            TextView mChange;


            @BindView(R.id.imageview)
            ImageView mCircularView;

/*
        @BindView(R.id.manage_Channelname)
        TextView mchannelname;
*/

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);


            }
        }
    }












