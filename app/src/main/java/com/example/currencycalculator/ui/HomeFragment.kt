package com.example.currencycalculator.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencycalculator.R
import com.example.currencycalculator.data.Status
import com.example.currencycalculator.data.model.Currency
import com.example.currencycalculator.ui.dialog.CurrencySelectionDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var currencyToConvertTo: Currency? = null
    private var currencyToConvertFrom: Currency? = null
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val convertFromTextViewSpinner = text_currency_to_convert_from
        convertFromTextViewSpinner.setOnClickListener {
            showCurrencySelection(it)
        }
        val convertToTextViewSpinner = text_currency_to_convert_to
        convertToTextViewSpinner.setOnClickListener {
            showCurrencySelection(it)
        }

        img_swap_currency.setOnClickListener {
            swapCurrency(currencyToConvertTo, currencyToConvertFrom, convertToTextViewSpinner, convertFromTextViewSpinner)
        }

        btn_submit.setOnClickListener {
            val currencyConvertToAmount = txt_input_currency_convert_to.editText
            val currencyToConvertFromAmount = txt_input_currency_to_convert_from?.editText

            homeViewModel.getLatestRates(currencyToConvertTo?.code!!, currencyToConvertFrom?.code!!)
            homeViewModel.getRatingsLiveData().observe(requireActivity(), Observer {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {

                    }
                    Status.LOADED -> {
                    }
                    Status.SUCCESS -> {
                        it.data?.let { exchange ->
                                    currencyConvertToAmount?.setText("${currencyToConvertFromAmount?.text.toString().toDouble() *  exchange}")
                        }
                    }
                }
            })


        }
    }

    private fun showCurrencySelection(textView: View) {
        val currencySelectionDialogFragment = CurrencySelectionDialogFragment.newInstance()
        currencySelectionDialogFragment.onCurrencySelected =
            { currency: Currency -> updateSelectedCurrency(currency, textView) }
        currencySelectionDialogFragment.show(requireFragmentManager(), "CurrencySelectionDialogFragment")

    }

    private fun updateSelectedCurrency(currency: Currency?, textView: View) {
        val context = requireContext()
        val text = context.getString(R.string.currency_abbreviation, currency?.flag, currency?.code)
        (textView as TextView).setText(text)

        if (textView.id == R.id.text_currency_to_convert_to) {
            currencyToConvertTo = currency
        } else {
            currencyToConvertFrom = currency
        }
    }

    private fun swapCurrency(
        currencyToConvertTo: Currency?,
        currencyToConvertFrom: Currency?,
        currencyToConvertToTextView: TextView,
        currencyToConvertFromTextView: TextView
    ) {
        updateSelectedCurrency(currencyToConvertFrom, currencyToConvertToTextView)
        updateSelectedCurrency(currencyToConvertTo, currencyToConvertFromTextView)
    }
}
