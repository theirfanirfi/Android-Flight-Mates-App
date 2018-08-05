package com.example.samad.flightmates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Irfan Irfi on 8/23/2017.
 */

public class MyExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    JSONArray fnrrs,frm,too,takeoff,pnrr,ldate;
    List<JSONArray> lst;

    MyExpandableAdapter(Context ctx, final JSONArray fnrs, JSONArray pnr, JSONArray from, JSONArray to, JSONArray tkoff, JSONArray landing)
    {
        this.context = ctx;
        this.fnrrs = fnrs;
        this.frm = from;
        this.too = to;
        this.takeoff = tkoff;
        this.pnrr = pnr;
        this.ldate = landing;
//        lst = new List<JSONArray>() {
//            @Override
//            public int size() {
//                return fnrs.length();
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public boolean contains(Object o) {
//                return false;
//            }
//
//            @NonNull
//            @Override
//            public Iterator<JSONArray> iterator() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public Object[] toArray() {
//                return new Object[0];
//            }
//
//            @NonNull
//            @Override
//            public <T> T[] toArray(@NonNull T[] ts) {
//                return null;
//            }
//
//            @Override
//            public boolean add(JSONArray jsonArray) {
//                return false;
//            }
//
//            @Override
//            public boolean remove(Object o) {
//                return false;
//            }
//
//            @Override
//            public boolean containsAll(@NonNull Collection<?> collection) {
//                return false;
//            }
//
//            @Override
//            public boolean addAll(@NonNull Collection<? extends JSONArray> collection) {
//                return false;
//            }
//
//            @Override
//            public boolean addAll(int i, @NonNull Collection<? extends JSONArray> collection) {
//                return false;
//            }
//
//            @Override
//            public boolean removeAll(@NonNull Collection<?> collection) {
//                return false;
//            }
//
//            @Override
//            public boolean retainAll(@NonNull Collection<?> collection) {
//                return false;
//            }
//
//            @Override
//            public void clear() {
//
//            }
//
//            @Override
//            public JSONArray get(int i) {
//                return null;
//            }
//
//            @Override
//            public JSONArray set(int i, JSONArray jsonArray) {
//                return null;
//            }
//
//            @Override
//            public void add(int i, JSONArray jsonArray) {
//
//            }
//
//            @Override
//            public JSONArray remove(int i) {
//                return null;
//            }
//
//            @Override
//            public int indexOf(Object o) {
//                return 0;
//            }
//
//            @Override
//            public int lastIndexOf(Object o) {
//                return 0;
//            }
//
//            @Override
//            public ListIterator<JSONArray> listIterator() {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public ListIterator<JSONArray> listIterator(int i) {
//                return null;
//            }
//
//            @NonNull
//            @Override
//            public List<JSONArray> subList(int i, int i1) {
//                return null;
//            }
//        };
//        lst.add(fnrs);
//        lst.add(from);
//        lst.add(to);
//        lst.add(tkoff);
//        lst.add(pnr);
//        lst.add(landing);


    }
    @Override
    public int getGroupCount() {
        return this.fnrrs.length();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.frm.length();
    }

    @Override
    public Object getGroup(int i) {
        try {
            return this.pnrr.get(i);
        } catch (JSONException e) {
            return i;
        }
        //return i;
    }

    @Override
    public Object getChild(int i, int i1) {
        return i;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater myInflator = LayoutInflater.from(this.context);
        View CustomView = myInflator.inflate(R.layout.grouplayout,viewGroup,false);
        ImageView iv = (ImageView) CustomView.findViewById(R.id.imageView2);
        iv.setImageResource(R.drawable.flighticons);
        TextView myTxt = (TextView) CustomView.findViewById(R.id.groupTextView);
        try {
            myTxt.setText("PNR: "+this.pnrr.getString(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return CustomView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater myInflator = LayoutInflater.from(this.context);
        View CustomView = myInflator.inflate(R.layout.childlayout,viewGroup,false);

        TextView fmyTxt = (TextView) CustomView.findViewById(R.id.fromTextView);
        TextView tomyTxt = (TextView) CustomView.findViewById(R.id.takeoffTextView);
        TextView tmyTxt = (TextView) CustomView.findViewById(R.id.toTextView);
        TextView lmyTxt = (TextView) CustomView.findViewById(R.id.landingTextView);

        try {
            fmyTxt.setText("From: "+this.frm.getString(i));
            tomyTxt.setText("To: "+this.too.getString(i));
            tmyTxt.setText("Take Off: "+this.takeoff.getString(i));
            lmyTxt.setText("Landing: "+this.ldate.getString(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return CustomView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
