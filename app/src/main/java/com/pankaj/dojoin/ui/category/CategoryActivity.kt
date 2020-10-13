package com.pankaj.dojoin.ui.category

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pankaj.dojoin.R
import com.pankaj.dojoin.databinding.ActivityCategoryBinding
import com.pankaj.dojoin.models.ChildPojo
import com.pankaj.dojoin.models.ParentPojo
import com.pankaj.dojoin.network_response.CategoryResponse
import com.pankaj.dojoin.ui.category.adapter.CustomExpandableListAdapter
import com.pankaj.dojoin.ui.categroy_selection.SelectionActivity
import com.pankaj.dojoin.utils.Utils
import kotlinx.android.synthetic.main.activity_selection.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class CategoryActivity : AppCompatActivity(), KodeinAware {


    var categoryList = ArrayList<ParentPojo>()
    val subCategoryList = HashMap<ParentPojo, List<ChildPojo>>()

    lateinit var mActivity: Activity
    lateinit var mBinding: ActivityCategoryBinding
    lateinit var mViewModel: CategoryViewModel
    val factory: CategoryFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

    }

    /**
     * initialize the data binding
     */
    private fun initBinding() {
        mActivity = this
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        mViewModel = ViewModelProvider(this, factory).get(CategoryViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        supportActionBar?.apply {



        }

        mViewModel.callApiCategory(mActivity)


    }

    /**
     * Set adapter data
     */
    fun setAdapter(categoryResponse: List<CategoryResponse.Result>) {

        if (categoryResponse.isNullOrEmpty()) {
            mViewModel.dataFound.value = false
        } else {

            for (i in categoryResponse.indices) {
                val parent = categoryResponse[i]

                val parentPojo = ParentPojo(parent.id, parent.title)
                categoryList.add(parentPojo)
                val childList = ArrayList<ChildPojo>()
                if (parent.subCategories.isNotEmpty()) {

                    for (j in parent.subCategories.indices) {

                        val child = parent.subCategories[j]
                        childList.add(ChildPojo(child.id, child.title))
                    }

                    subCategoryList.put(parentPojo, childList)
                }


            }



            mBinding.adapter = CustomExpandableListAdapter(this, categoryList, subCategoryList)

            mBinding.expendableList!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

                Utils.startNewActivity(mActivity,SelectionActivity::class.java)



                false
            }


        }

    }

    override val kodein: Kodein by kodein()

}