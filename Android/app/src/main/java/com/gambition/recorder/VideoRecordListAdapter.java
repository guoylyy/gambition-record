package com.gambition.recorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.gambition.recorder.MainActivity.PROPORTION;

import java.util.ArrayList;
import java.util.List;

public class VideoRecordListAdapter extends BaseAdapter {

    private Context context;
    private List<VideoRecord> records = new ArrayList<>();
    private LayoutInflater inflater;

    public VideoRecordListAdapter(Context context, List<VideoRecord> records) {
        this.context = context;
        this.records = records;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int i) {
        return records.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.record_item, null);

        RelativeLayout contentRelativeLayout = (RelativeLayout) view.findViewById(R.id.record_item_content_relativelayout);
        LinearLayout.LayoutParams contentRelativeLayoutParams = (LinearLayout.LayoutParams) contentRelativeLayout.getLayoutParams();
        contentRelativeLayoutParams.height = (int) (220 * PROPORTION);

        TextView nameTextView = (TextView) view.findViewById(R.id.record_item_name_textview);
        nameTextView.setTextSize(DisplayUtility.px2sp(context, 60 * PROPORTION));
        RelativeLayout.LayoutParams nameTextViewParams = (RelativeLayout.LayoutParams) nameTextView.getLayoutParams();
        nameTextViewParams.leftMargin = (int) (30 * PROPORTION);
        nameTextViewParams.topMargin = (int) (40 * PROPORTION);

        TextView dateTextView = (TextView) view.findViewById(R.id.record_item_date_textview);
        dateTextView.setTextSize(DisplayUtility.px2sp(context, 40 * PROPORTION));
        RelativeLayout.LayoutParams dateTextViewParams = (RelativeLayout.LayoutParams) dateTextView.getLayoutParams();
        dateTextViewParams.leftMargin = (int) (30 * PROPORTION);
        dateTextViewParams.bottomMargin = (int) (20 * PROPORTION);

        TextView secondsTextView = (TextView) view.findViewById(R.id.record_item_seconds_textview);
        secondsTextView.setTextSize(DisplayUtility.px2sp(context, 40 * PROPORTION));
        RelativeLayout.LayoutParams secondsTextViewParams = (RelativeLayout.LayoutParams) secondsTextView.getLayoutParams();
        secondsTextViewParams.rightMargin = (int) (30 * PROPORTION);
        secondsTextViewParams.bottomMargin = (int) (20 * PROPORTION);

        nameTextView.setText(records.get(i).getName());
        dateTextView.setText(records.get(i).getName());
        secondsTextView.setText(records.get(i).getName());

        final RelativeLayout operationRelativeLayout = (RelativeLayout) view.findViewById(R.id.record_item_operation_relativelayout);
        LinearLayout.LayoutParams operationRelativeLayoutParams = (LinearLayout.LayoutParams) operationRelativeLayout.getLayoutParams();
        operationRelativeLayoutParams.height = (int) (140 * PROPORTION);

        ImageView operationImageView = (ImageView) view.findViewById(R.id.record_item_operation_imageview);
        RelativeLayout.LayoutParams operationImageViewParams = (RelativeLayout.LayoutParams) operationImageView.getLayoutParams();
        operationImageViewParams.width = (int) (58 * PROPORTION);
        operationImageViewParams.height = (int) (70 * PROPORTION);
        operationImageViewParams.leftMargin = (int) (30 * PROPORTION);

        ImageView deleteImageView = (ImageView) view.findViewById(R.id.record_item_delete_imageview);
        RelativeLayout.LayoutParams deleteImageViewParams = (RelativeLayout.LayoutParams) deleteImageView.getLayoutParams();
        deleteImageViewParams.width = (int) (50 * PROPORTION);
        deleteImageViewParams.height = (int) (66 * PROPORTION);
        deleteImageViewParams.rightMargin = (int) (30 * PROPORTION);

        operationRelativeLayout.setVisibility(View.GONE);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operationRelativeLayout.getVisibility() == View.GONE) {
                    operationRelativeLayout.setVisibility(View.VISIBLE);
                } else {
                    operationRelativeLayout.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }
}
