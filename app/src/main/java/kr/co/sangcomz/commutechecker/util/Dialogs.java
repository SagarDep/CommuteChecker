package kr.co.sangcomz.commutechecker.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.sangcomz.commutechecker.R;
import kr.co.sangcomz.commutechecker.adapter.CommuteTimeAdapter;
import kr.co.sangcomz.commutechecker.bean.CommuteTimeBean;
import kr.co.sangcomz.commutechecker.db.DBAdapter;

/**
 * Created by sangc on 2015-09-15.
 */
public class Dialogs {


    public static void showDeleteDialog(final Context context, String title, final String content,
                                        String txtPositive, String txtNegativ, final RecyclerView recyclerView) {
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
                new DBAdapter(context).deleteAllCommuteTime();
                ArrayList<CommuteTimeBean> commuteTimeBeans = new DBAdapter(context).getCommuteTime();
                recyclerView.setAdapter(new CommuteTimeAdapter(commuteTimeBeans));
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//                }).start();

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
