package com.dailyhero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by song on 2015/11/8.
 */
public class ExplainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);


        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

           //內容
            private String[] explainTitle = new String[]{
                    "這是什麼","主線任務是什麼","支線任務是什麼","緊急任務是什麼","粗心者是什麼","Point是什麼","如何取得稱號",
                   "為什麼沒有魔王","可以設定任務完成日期嗎","如何查看已完成任務","可以許願功能嗎","我想贊助","我想參與","作者是誰","我想分享"
            };
            private String[][] explainText = new String[][]{
                    {"這是一個任務管理的App，你可以自定義任務並完成它"},
                    {"必須要做的事"},
                    {"可做可不做的事"},
                    {"很緊急的事"},
                    {"你的稱號，玩久了就可以獲得其他稱號"},
                    {"你的點數，透過完成任務獲得，未來可以做很多事，在未來"},
                    {"獲得越多的Point可以獲得其他稱號"},
                    {"難產中"},
                    {"難產中"},
                    {"難產中"},
                    {"請mail作者:sooon1g1@gmail.com"},
                    {"大歡迎!!請一定要聯絡作者:sooon1g1@mail.com"},
                    {"大歡迎!!請直接到作者Github"},
                    {"Hi!我是Song :)"},
                    {"請點我 :)"}
            };

            @Override
            public int getGroupCount() {
                return explainTitle.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return explainText[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return explainTitle[groupPosition];
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return explainText[groupPosition][childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) ExplainActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.list_group, null);
                }

                TextView lblListHeader = (TextView) convertView
                        .findViewById(R.id.lblListHeader);
                lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(explainTitle[groupPosition]);

                return convertView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) ExplainActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.explain_list_item, null);
                }
                TextView item = (TextView)convertView.findViewById(R.id.explain_item);
                item.setText(explainText[groupPosition][childPosition]);

                if(groupPosition==14) {
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("text/*");
                            intent.putExtra(Intent.EXTRA_TEXT,"我想跟你分享一個很有趣的任務管理App，但是還沒上架 :(");
                            startActivity(intent);
                        }
                    });
                }

                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        };
        ExpandableListView expandListView = (ExpandableListView) this.findViewById(R.id.explain);
        expandListView.setAdapter(adapter);


    }



}
