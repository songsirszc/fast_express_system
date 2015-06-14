package com.szc.fast_express_system.ui.dialog;


import com.szc.fast_express_system.R;
import com.szc.fast_express_system.common.util.ProgressDialogUtil;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.service.AppController;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class EditDialogFragment extends DialogFragment {
	 private EditText article;
	private EditText weight;
	private AppController controller;

	public Dialog  onCreateDialog(Bundle savedInstanceState)  
	    {  
	    	controller = AppController.getController(getActivity());
		 	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 	LayoutInflater inflater = getActivity().getLayoutInflater(); 
	        View view = inflater.inflate(R.layout.dialog_view, null); 
	        article = (EditText) view.findViewById(R.id.editText1);  
	        weight = (EditText) view.findViewById(R.id.editText2);  
	        builder.setView(view)  
            // Add action buttons 
            .setPositiveButton("Submit",  
                    new DialogInterface.OnClickListener()  
                    {  
                        @Override  
                        public void onClick(DialogInterface dialog, int id)  
                        {  
                        	String a=article.getText().toString();
                        	String w=weight.getText().toString();
                        	
                        	OrderData s=new OrderData();
                        	s.setArticle(a);
                        	s.setWeight(w);
                        	controller.getContext().addBusinessData("UserChange.Data", s);
                        	userorderchange();
                        }  
                    }).setNegativeButton("Cancel", null);  
	        	return builder.create();  
	    }

	protected void userorderchange() {
		// TODO Auto-generated method stub
		new Thread(new Runnable(){
			public void run() {
				controller.orderchange();
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}  
}
