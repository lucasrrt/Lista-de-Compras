package io.github.lucasrrt.listadecompra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by bruno on 20/11/16.
 */

public class CompraFormDialog extends DialogFragment {
    interface CompraCallback{
        public void call();
    }
    private CompraCallback onCancel;
    private CompraCallback onConfirm;

    public CompraFormDialog(CompraCallback confirm,CompraCallback cancel){
       this.onCancel = cancel;
        this.onConfirm = confirm;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Inserir este item à lista de compras")
                .setPositiveButton("Comprar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onConfirm.call();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onCancel.call();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
