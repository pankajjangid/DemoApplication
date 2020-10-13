package com.pankaj.dojoin.ui.categroy_selection

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pankaj.dojoin.R
import com.pankaj.dojoin.databinding.ActivityCategoryBinding
import com.pankaj.dojoin.databinding.ActivitySelectionBinding
import com.pankaj.dojoin.ui.category.CategoryFactory
import com.pankaj.dojoin.ui.category.CategoryViewModel
import kotlinx.android.synthetic.main.activity_selection.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SelectionActivity : AppCompatActivity(), KodeinAware {

    lateinit var mActivity: Activity
    lateinit var mBinding: ActivitySelectionBinding
    lateinit var mViewModel: SelectionViewModel
    val factory: SelectionFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        setListners()
    }

    private fun setListners() {

        rgPeopleReach.setOnCheckedChangeListener { radioGroup, i ->

            when(radioGroup.checkedRadioButtonId){
                R.id.rbInPerson->{
                    mViewModel.personReachStatus.value =SelectionViewModel.PersonReachStatus.IN_PERSON

                }
                R.id.rbOnline->{
                    mViewModel.personReachStatus.value =SelectionViewModel.PersonReachStatus.ONLINE

                }
            }
        }
        rgAppointment.setOnCheckedChangeListener { radioGroup, i ->

            when(radioGroup.checkedRadioButtonId){
                R.id.rbBookaTime->{
                    mViewModel.appointmentStatus.value =SelectionViewModel.AppointmentStatus.BOOK_A_TIME

                }
                R.id.rbDropIn->{
                    mViewModel.appointmentStatus.value =SelectionViewModel.AppointmentStatus.DROP_IN
                }
            }
        }
    }

    /**
     * initialize the data binding
     */
    private fun initBinding() {
        mActivity = this
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_selection)
        mViewModel = ViewModelProvider(this, factory).get(SelectionViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        supportActionBar?.apply {

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

        }


    }
    override val kodein: Kodein by kodein()

}