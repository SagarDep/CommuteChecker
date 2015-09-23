package kr.co.sangcomz.commutechecker.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import kr.co.sangcomz.commutechecker.R;

/**
 * Created by sangc on 2015-09-15.
 */
public class Dialogs {


    public static void showBasicDialog(final Context context, String title, String content,
                                       String txtPositive, String txtNegativ) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.basic_dialog);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.content);
        Button btnPositive = (Button) dialog.findViewById(R.id.btn_positive);
        Button btnNegative = (Button) dialog.findViewById(R.id.btn_negative);

        tvTitle.setText(title);
        tvContent.setText(content);
        btnPositive.setText(txtPositive);
        btnNegative.setText(txtNegativ);

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
//        TextView tvContent = (TextView) dialog.findViewById(R.id.alert_title);
////				TextView dContent = (TextView)dialog.findViewById(R.id.alert_content);
//        if (title != null) {
//            dTitle.setText(title);
//        }
//        TextView dBtn = (TextView) dialog.findViewById(R.id.done);
//        dBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }
}
