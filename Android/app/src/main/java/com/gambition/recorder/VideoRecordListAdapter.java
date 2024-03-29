package com.gambition.recorder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.gambition.recorder.MainActivity.PROPORTION;

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

        final VideoRecord record = records.get(i);

        nameTextView.setText(record.getName());
        long current = record.getDate();
        dateTextView.setText(MainActivity.CN_DATE_FULL_FORMAT.format(new Date(current)));
        // TODO duration re-count
        secondsTextView.setText(Utility.covertToTimeString(record.getDuration() / 5));

        final RelativeLayout operationRelativeLayout = (RelativeLayout) view.findViewById(R.id.record_item_operation_relativelayout);
        LinearLayout.LayoutParams operationRelativeLayoutParams = (LinearLayout.LayoutParams) operationRelativeLayout.getLayoutParams();
        operationRelativeLayoutParams.height = (int) (140 * PROPORTION);

        ImageView operationImageView = (ImageView) view.findViewById(R.id.record_item_operation_imageview);
        RelativeLayout.LayoutParams operationImageViewParams = (RelativeLayout.LayoutParams) operationImageView.getLayoutParams();
        operationImageViewParams.width = (int) (58 * PROPORTION);
        operationImageViewParams.height = (int) (70 * PROPORTION);
        operationImageViewParams.leftMargin = (int) (30 * PROPORTION);

        RelativeLayout leftOperationRelativeLayout = (RelativeLayout) view.findViewById(R.id.record_item_left_operation_relativelayout);

        leftOperationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(record.getPath())), "video/mp4");
                context.startActivity(intent);
            }
        });

        ImageView renameImageView = (ImageView) view.findViewById(R.id.record_item_rename_imageview);
        RelativeLayout.LayoutParams renameImageViewParams = (RelativeLayout.LayoutParams) renameImageView.getLayoutParams();
        renameImageViewParams.width = (int) (64 * PROPORTION);
        renameImageViewParams.height = (int) (58 * PROPORTION);
        renameImageViewParams.rightMargin = (int) (60 * PROPORTION);

        ImageView deleteImageView = (ImageView) view.findViewById(R.id.record_item_delete_imageview);
        RelativeLayout.LayoutParams deleteImageViewParams = (RelativeLayout.LayoutParams) deleteImageView.getLayoutParams();
        deleteImageViewParams.width = (int) (50 * PROPORTION);
        deleteImageViewParams.height = (int) (66 * PROPORTION);
        deleteImageViewParams.rightMargin = (int) (30 * PROPORTION);

        RelativeLayout deleteOperationRelativeLayout = (RelativeLayout) view.findViewById(R.id.record_item_delete_operation_relativelayout);

        deleteOperationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GambitionNotifyDialog.Builder builder = new GambitionNotifyDialog.Builder(context);
                builder.setTitle("确定要删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        File videoFile = new File(record.getPath());
                        if (videoFile.exists()) {
                            videoFile.delete();
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(videoFile)));
                        }

                        record.delete();

                        List<VideoRecord> recordList = DataSupport.findAll(VideoRecord.class);
                        Collections.reverse(recordList);

                        VideoRecordListAdapter recordListAdapter = new VideoRecordListAdapter(context, recordList);
                        ListView recordListView = (ListView) ((Activity) context).findViewById(R.id.main_activity_records_listview);
                        recordListView.setAdapter(recordListAdapter);
                    }
                });
                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        RelativeLayout renameOperationRelativeLayout = (RelativeLayout) view.findViewById(R.id.record_item_rename_operation_relativelayout);

        renameOperationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GambitionInputDialog.Builder builder = new GambitionInputDialog.Builder(context);
                builder.setTitle("请输入名字");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        record.setName(builder.getInputName());
                        record.save();

                        List<VideoRecord> recordList = DataSupport.findAll(VideoRecord.class);
                        Collections.reverse(recordList);

                        VideoRecordListAdapter recordListAdapter = new VideoRecordListAdapter(context, recordList);
                        ListView recordListView = (ListView) ((Activity) context).findViewById(R.id.main_activity_records_listview);
                        recordListView.setAdapter(recordListAdapter);
                    }
                });
                builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                builder.setInputName(record.getName());
            }
        });

        operationRelativeLayout.setVisibility(View.GONE);

        contentRelativeLayout.setOnClickListener(new View.OnClickListener() {
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
