package com.example.doodleblue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PriceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static String TAG=PriceFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

@BindView(R.id.gallery_list)
RecyclerView mRecyclerView;

@BindView(R.id.price_progress)
    ProgressBar mProgrssbar;
@BindView(R.id.search_text)
    EditText mSearch;
@BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshlayout;

@BindView(R.id.starttext)
    TextView mTotal;

@BindView(R.id.gallery_error)
TextView mGalleryError;
private PriceJsonResponse mNewlist;
private ArrayList<PriceJsonResponse.datalist> mDataList= new ArrayList<>();
private DataListAdapter mAdapter;


    public PriceFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PriceFragment newInstance(String param1, String param2) {
        PriceFragment fragment = new PriceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  rootView= inflater.inflate(R.layout.fragment_price, container, false);

        ButterKnife.bind(this,rootView);

        loadSubscriberPackage();

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    filter1(s.toString());


            }
        });

        mRefreshlayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mRefreshlayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRefreshlayout.setRefreshing(true);
loadSubscriberPackage();

                                    }
                                }
        );



    }

    private void filter1(String searchtext) {

        ArrayList<PriceJsonResponse.datalist> mFilteredResponse = new ArrayList<>();

        for (PriceJsonResponse.datalist response : mDataList) {
            if (response.getName().toLowerCase().startsWith(searchtext.toLowerCase())) {
                mFilteredResponse.add(response);
            }
        }
        mAdapter.filterList(mFilteredResponse);

    }


    public void loadSubscriberPackage() {

         String alteredString="https://api.coincap.io/";

        Constants.getInstance().setBASE_URL(alteredString.trim(),getContext());
        String base_url = Constants.getInstance().getBASE_URL(getContext());
        ApiClient client = ServiceGenerator.createService(ApiClient.class, base_url);
        Call<PriceJsonResponse> call = client.getPriceDetails();
        mProgrssbar.setVisibility(View.GONE);
        mRefreshlayout.setRefreshing(true);
        call.enqueue(new Callback<PriceJsonResponse>() {
            @Override
            public void onResponse(Call<PriceJsonResponse> call, Response<PriceJsonResponse> response) {
                Log.i(TAG, "ResponseCode: " + response.code());

                mProgrssbar.setVisibility(View.GONE);
                mRefreshlayout.setRefreshing(false);


                if (response.body() != null) {

                  handleResponse2( response.body());


                } else {
                    Log.i(TAG, "Response is null");
                }
            }
            @Override
            public void onFailure(Call<PriceJsonResponse> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }

    private void handleResponse2(PriceJsonResponse responses) {


mNewlist=responses;
mDataList=mNewlist.getDatalist();


        if (mDataList.isEmpty()) {

            mRecyclerView.setVisibility(View.GONE);
mGalleryError.setVisibility(View.VISIBLE);
        } else {

mGalleryError.setVisibility(View.GONE);
            Double value1=0.0;

            for(int i=0;i<mDataList.size();i++)
            {

                Double value =Double.valueOf(mDataList.get(i).getPrice());

                value1=value1+value;


            }

            mTotal.setText(String.valueOf("$ "  +  value1));
            Log.i("datasize", String.valueOf(value1));

            mRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(manager);
            mAdapter = new DataListAdapter(getContext(),mDataList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onRefresh() {

        loadSubscriberPackage();
    }
}
