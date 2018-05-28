package com.cocos.cocosapp.presentation.slide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocos.cocosapp.R;
import com.cocos.cocosapp.core.BaseFragment;
import com.cocos.cocosapp.presentation.auth.LoginActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TOTTUS on 21/06/2016.
 */
public class SlideFragment extends BaseFragment {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.titles)
    CirclePageIndicator titles;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    private InitAdapter initAdapter;

    public static SlideFragment newInstance() {
        return new SlideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slide, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter = new InitAdapter(getActivity());
        pager.setAdapter(initAdapter);
        titles.notifyDataSetChanged();
        titles.setViewPager(pager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        newActivityClearPreview(getActivity(),null, LoginActivity.class);
    }


    public static class InitAdapter extends PagerAdapter {

        Context context;
        ArrayList<Drawable> list;
        TextView textTitleMenuStart;
        @BindView(R.id.im_slide)
        ImageView imSlide;
        private LayoutInflater layoutInflater;
        Drawable item;


        public InitAdapter(Context context) {
            this.context = context;
            this.list = new ArrayList<>();
            this.layoutInflater = LayoutInflater.from(context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                list.add(context.getDrawable(R.drawable.sp_02));
            }else{
                list.add(context.getResources().getDrawable(R.drawable.sp_02));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                list.add(context.getDrawable(R.drawable.sp_03));
            }else{
                list.add(context.getResources().getDrawable(R.drawable.sp_03));
            }

        }

        public Object instantiateItem(final ViewGroup collection, final int position) {
            item = list.get(position);
            final View view = layoutInflater.inflate(R.layout.item_slide_splash, collection, false);
            ButterKnife.bind(this, view);
            imSlide.setImageDrawable(item);
            collection.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void destroyItem(ViewGroup collection, int position,
                                Object view) {
            collection.removeView((View) view);
        }
    }
}

