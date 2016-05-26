package com.gambition.recorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DESIGN_PROTOTYPE_WIDTH = 1500;
    public static float PROPORTION;

    private boolean isStartStyle = true;
    private boolean isFinishEnableStyle = false;

    private static final String START_RECORD = "开始录音";
    private static final String PAUSE_RECORD = "暂停录音";
    private static final String FINISH_RECORD = "结束录音";

    @Inject
    FFmpeg ffmpeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        ObjectGraph.create(new DaggerDependencyModule(this)).inject(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        PROPORTION = (float) width / (float) DESIGN_PROTOTYPE_WIDTH;

        loadFFMpegBinary();
        initUI();
    }

    private void initUI() {
        RelativeLayout operationRelativeLayout = (RelativeLayout) findViewById(R.id.main_activity_operation_relativelayout);
        LinearLayout.LayoutParams operationRelativeLayoutParams = (LinearLayout.LayoutParams) operationRelativeLayout.getLayoutParams();
        operationRelativeLayoutParams.height = (int) (600 * PROPORTION);

        final RelativeLayout operationHolderRelativeLayout = (RelativeLayout) findViewById(R.id.main_activity_operation_holder_relativelayout);
        RelativeLayout.LayoutParams operationHolderRelativeLayoutParams = (RelativeLayout.LayoutParams) operationHolderRelativeLayout.getLayoutParams();
        operationHolderRelativeLayoutParams.width = (int) (240 * PROPORTION);
        operationHolderRelativeLayoutParams.height = (int) (240 * PROPORTION);
        operationHolderRelativeLayoutParams.leftMargin = (int) (200 * PROPORTION);

        final RelativeLayout finishHolderRelativeLayout = (RelativeLayout) findViewById(R.id.main_activity_finish_holder_relativelayout);
        RelativeLayout.LayoutParams finishHolderRelativeLayoutParams = (RelativeLayout.LayoutParams) finishHolderRelativeLayout.getLayoutParams();
        finishHolderRelativeLayoutParams.width = (int) (240 * PROPORTION);
        finishHolderRelativeLayoutParams.height = (int) (240 * PROPORTION);
        finishHolderRelativeLayoutParams.rightMargin = (int) (200 * PROPORTION);

        setStartStyle();

        operationHolderRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStartStyle) {
                    setPauseStyle();
                    setFinishEnableStyle();
                    finishHolderRelativeLayout.setClickable(true);
                    Toast.makeText(MainActivity.this, START_RECORD, Toast.LENGTH_SHORT).show();

                    execFFmpegBinary(new String[]{"ffmpeg -version"});
                } else {
                    setStartStyle();
                    Toast.makeText(MainActivity.this, PAUSE_RECORD, Toast.LENGTH_SHORT).show();
                }
            }
        });

        finishHolderRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFinishDisableStyle();
                setStartStyle();
                finishHolderRelativeLayout.setClickable(false);
                Toast.makeText(MainActivity.this, FINISH_RECORD, Toast.LENGTH_SHORT).show();
            }
        });

        setFinishDisableStyle();

        finishHolderRelativeLayout.setClickable(false);

        List<VideoRecord> recordList = new ArrayList<>();
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));
        recordList.add(new VideoRecord("123", new Date(), 100));

        VideoRecordListAdapter recordListAdapter = new VideoRecordListAdapter(this, recordList);
        ListView recordListView = (ListView) findViewById(R.id.main_activity_records_listview);
        recordListView.setAdapter(recordListAdapter);
    }

    private void loadFFMpegBinary() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    showUnsupportedExceptionDialog();
                }
            });
        } catch (FFmpegNotSupportedException e) {
            showUnsupportedExceptionDialog();
        }
    }

    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.i(TAG, "FAILED with output : " + s);
                }

                @Override
                public void onSuccess(String s) {
                    Log.i(TAG, "SUCCESS with output : " + s);
                }

                @Override
                public void onProgress(String s) {
                    Log.d(TAG, "Started command : ffmpeg " + command);
                }

                @Override
                public void onStart() {
                    Log.d(TAG, "Started command : ffmpeg " + command);
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "Finished command : ffmpeg " + command);
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
        }
    }

    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.device_not_supported))
                .setMessage(getString(R.string.device_not_supported_message))
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .create()
                .show();

    }

    private void setStartStyle() {
        isStartStyle = true;

        ImageView operationImageView = (ImageView) findViewById(R.id.main_activity_operation_imageview);
        operationImageView.setImageResource(R.mipmap.ic_play_arrow);

        RelativeLayout.LayoutParams operationImageViewParams = (RelativeLayout.LayoutParams) operationImageView.getLayoutParams();
        operationImageViewParams.width = (int) (112 * PROPORTION);
        operationImageViewParams.height = (int) (116 * PROPORTION);
    }

    private void setPauseStyle() {
        isStartStyle = false;

        ImageView operationImageView = (ImageView) findViewById(R.id.main_activity_operation_imageview);
        operationImageView.setImageResource(R.mipmap.ic_pause);

        RelativeLayout.LayoutParams operationImageViewParams = (RelativeLayout.LayoutParams) operationImageView.getLayoutParams();
        operationImageViewParams.width = (int) (90 * PROPORTION);
        operationImageViewParams.height = (int) (106 * PROPORTION);
    }

    private void setFinishEnableStyle() {
        isFinishEnableStyle = true;

        ImageView finishImageView = (ImageView) findViewById(R.id.main_activity_finish_imageview);
        finishImageView.setImageResource(R.mipmap.ic_pause_hl);

        RelativeLayout.LayoutParams finishImageViewParams = (RelativeLayout.LayoutParams) finishImageView.getLayoutParams();
        finishImageViewParams.width = (int) (90 * PROPORTION);
        finishImageViewParams.height = (int) (90 * PROPORTION);
    }

    private void setFinishDisableStyle() {
        isFinishEnableStyle = false;

        ImageView finishImageView = (ImageView) findViewById(R.id.main_activity_finish_imageview);
        finishImageView.setImageResource(R.mipmap.ic_pause_nr);

        RelativeLayout.LayoutParams finishImageViewParams = (RelativeLayout.LayoutParams) finishImageView.getLayoutParams();
        finishImageViewParams.width = (int) (90 * PROPORTION);
        finishImageViewParams.height = (int) (90 * PROPORTION);
    }
}
