package com.gambition.recorder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.gambition.recorder.MainActivity.PROPORTION;

public class GambitionNotifyDialog extends Dialog {

    public GambitionNotifyDialog(Context context) {
        super(context, R.style.GambitionDialog);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private int height = 544;
        private int titleHeight = 380;
        private EditText inputEditText;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public String getInputName() {
            return inputEditText.getText().toString();
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setTitleHeight(int height) {
            this.titleHeight = height;
        }

        public GambitionNotifyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme  
            final GambitionNotifyDialog dialog = new GambitionNotifyDialog(context);
            View layout = inflater.inflate(R.layout.gambition_dialog, null);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            dialog.addContentView(layout, params);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = (int) (1280 * PROPORTION);
            lp.height = (int) (height * PROPORTION);

            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.rgb(255, 255, 255));
            gradientDrawable.setCornerRadius(18 * PROPORTION);
            layout.setBackground(gradientDrawable);

            RelativeLayout titleRelativeLayout = (RelativeLayout) layout.findViewById(R.id.gambition_dialog_title_relativelayout);
            titleRelativeLayout.getLayoutParams().height = (int) (titleHeight * PROPORTION);
            // set the dialog title  
            TextView titleTextView = (TextView) layout.findViewById(R.id.gambition_dialog_title_textview);
            titleTextView.setTextSize(DisplayUtility.px2sp(context, 68 * PROPORTION));
            titleTextView.setText(title);

            inputEditText = (EditText) layout.findViewById(R.id.gambition_dialog_input_edittext);
            inputEditText.setTextSize(DisplayUtility.px2sp(context, 68 * PROPORTION));
            RelativeLayout.LayoutParams inputEditTextParams = (RelativeLayout.LayoutParams) inputEditText.getLayoutParams();
            inputEditTextParams.leftMargin = (int) (100 * PROPORTION);
            inputEditTextParams.rightMargin = (int) (100 * PROPORTION);
            inputEditTextParams.bottomMargin = (int) (100 * PROPORTION);

            inputEditText.setVisibility(View.GONE);

            // set the confirm button  
            if (positiveButtonText != null) {
                TextView positiveTextView = (TextView) layout.findViewById(R.id.gambition_dialog_positive_textview);
                positiveTextView.setText(positiveButtonText);
                positiveTextView.setTextSize(DisplayUtility.px2sp(context, 58 * PROPORTION));
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.gambition_dialog_positive_textview).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.gambition_dialog_positive_textview).setVisibility(View.GONE);
            }
            // set the cancel button  
            if (negativeButtonText != null) {
                TextView negativeTextView = (TextView) layout.findViewById(R.id.gambition_dialog_negative_textview);
                negativeTextView.setText(negativeButtonText);
                negativeTextView.setTextSize(DisplayUtility.px2sp(context, 58 * PROPORTION));
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.gambition_dialog_negative_textview).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.gambition_dialog_negative_textview).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
} 