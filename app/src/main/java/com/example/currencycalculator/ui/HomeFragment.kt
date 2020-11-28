package com.example.currencycalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.currencycalculator.R
import com.example.currencycalculator.data.Status
import com.example.currencycalculator.data.model.Currency
import com.example.currencycalculator.databinding.ActivityMainBinding
import com.example.currencycalculator.databinding.FragmentHomeBinding
import com.example.currencycalculator.ui.dialog.CurrencySelectionDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.graph_sheet_chart.*
import kotlinx.android.synthetic.main.spinner_layout.*
import kotlinx.android.synthetic.main.spinner_layout_2.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var currencyToConvertTo: Currency? = null
    private var currencyToConvertFrom: Currency? = null
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {
            //gives a pre plotted-like view for the chart background
            gradientChart.chartValues = arrayOf(
                5f, 0f, -15f, 5f, 10f,5f, 0f, 10f, -5f, -10f
            )

        }

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

        btn_convert.setOnClickListener {

            var currencyToConvertFromAmount: String = txt_input_currency_to_convert_from.text.toString()

            if (!txt_input_currency_convert_to.text.isNullOrEmpty() ||
                    !txt_input_currency_to_convert_from.text.isNullOrEmpty()) {

                homeViewModel.getLatestRates(currencyToConvertTo?.code!!, currencyToConvertFrom?.code!!)
                homeViewModel.getRatingsLiveData().observe(requireActivity(), Observer {
                    when (it.status.toString()) {
                        Status.LOADING.toString() -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        Status.ERROR.toString() -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                            return@Observer
                        }
                        Status.LOADED.toString() -> {
                            progressBar.visibility = View.GONE
                        }
                        Status.SUCCESS.toString() -> {
                            progressBar.visibility = View.GONE
                            it.data.let { exchange ->
                                txt_input_currency_convert_to.setText("${currencyToConvertFromAmount.toDouble() * exchange!!}")
                            }
                        }
                    }
                })
            }
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
        (textView as TextView).text = text

        if (textView.id == R.id.text_currency_to_convert_to) {
            currencyToConvertTo = currency
            currency_to_text.text = currency?.code

        } else {
            currencyToConvertFrom = currency
            currency_from_text.text = currency?.code

        }
    }

    private fun swapCurrency(
            currencyToConvertTo: Currency?,
            currencyToConvertFrom: Currency?,
            currencyToConvertToTextView: TextView,
            currencyToConvertFromTextView: TextView
    ){
        updateSelectedCurrency(currencyToConvertFrom, currencyToConvertToTextView)
        updateSelectedCurrency(currencyToConvertTo, currencyToConvertFromTextView)
    }
}
