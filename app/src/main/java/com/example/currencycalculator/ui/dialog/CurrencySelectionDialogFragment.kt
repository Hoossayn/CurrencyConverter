package com.example.currencycalculator.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.currencycalculator.R
import com.example.currencycalculator.data.model.Currency


class CurrencySelectionDialogFragment : DialogFragment(), DialogInterface.OnClickListener {

    var onCurrencySelected : ((Currency) -> Unit)? = null

    private val currencies = Currency.values()
    override fun onClick(p0: DialogInterface?,  selectedCurrency: Int) {
       onCurrencySelected?.invoke(currencies[selectedCurrency])
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity()).setTitle(getString(R.string.select_currency))
            .setItems(createItems(), this).setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    private fun createItems(): Array<String> {
        return currencies.map { currency -> currency.description }.toTypedArray()
    }

    companion object {
        fun newInstance() = CurrencySelectionDialogFragment()
    }
}